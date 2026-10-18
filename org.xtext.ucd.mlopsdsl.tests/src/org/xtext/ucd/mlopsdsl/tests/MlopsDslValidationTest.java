package org.xtext.ucd.mlopsdsl.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.Provider;
import mlops.Edge;
import mlops.InfraPlan;
import mlops.MetaModelSpec;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.xtext.ucd.mlopsdsl.generator.MlopsGenerator;

/** Real parser/editor validation and direct generator checks; no mocked model. */
@ExtendWith(InjectionExtension.class)
@InjectWith(MlopsDslInjectorProvider.class)
public class MlopsDslValidationTest {
    @Inject private ParseHelper<EObject> parser;
    @Inject private IResourceValidator validator;
    @Inject private Provider<XtextResourceSet> resourceSets;
    @Inject private Provider<InMemoryFileSystemAccess> fileSystems;

    @Test public void validAzure() throws Exception { verify("C00"); }
    @Test public void validPrivateWithoutTls() throws Exception { verify("C01"); }
    @Test public void validMinApiPort() throws Exception { verify("C02"); }
    @Test public void validMaxApiPort() throws Exception { verify("C03"); }
    @Test public void invalidSplit() throws Exception { verify("S01"); }
    @Test public void missingGpu() throws Exception { verify("S02"); }
    @Test public void publicWithoutTls() throws Exception { verify("S03"); }
    @Test public void missingApiPort() throws Exception { verify("S04"); }
    @Test public void missingCloudRegion() throws Exception { verify("S05"); }
    @Test public void missingPublicDomain() throws Exception { verify("S06"); }
    @Test public void invalidNodeCounts() throws Exception { verify("S07"); }
    @Test public void invalidDecisionWeights() throws Exception { verify("S08"); }
    @Test public void contradictoryCriterionBounds() throws Exception { verify("S09"); }
    @Test public void brokenWorkflowReference() throws Exception { verify("S10"); }
    @Test public void emptyEntryCommand() throws Exception { verify("S11"); }
    @Test public void zeroSplitRatio() throws Exception { verify("S12"); }

    @Test
    public void localReferencesAreIndependentOfResourceLoadOrder() throws Exception {
        for (boolean invalidFirst : new boolean[] { true, false }) {
            ResourceSet resourceSet = resourceSets.get();
            EObject first = parse(invalidFirst ? "S01" : "C00", resourceSet);
            EObject second = parse(invalidFirst ? "C00" : "S01", resourceSet);
            EObject valid = invalidFirst ? second : first;
            EObject invalid = invalidFirst ? first : second;
            MetaModelSpec model = EcoreUtil2.getAllContentsOfType(valid, MetaModelSpec.class).get(0);
            assertSame(valid.eResource(), model.getProject().getData().eResource());
            assertSame(valid.eResource(), model.getPlan().eResource());
            assertSame(valid.eResource(), model.getInfra().eResource());
            assertTrue(errors(valid).isEmpty(), "Valid scenario borrowed another file's declarations");
            assertTrue(errors(invalid).stream().anyMatch(i ->
                i.getMessage().contains("Dataset split ratios must sum to 1.0")));
        }
    }

    @Test
    public void explicitFalseSurvivesParsing() throws Exception {
        EObject root = parse("C01", resourceSets.get());
        InfraPlan infra = EcoreUtil2.getAllContentsOfType(root, InfraPlan.class).get(0);
        assertEquals(Boolean.FALSE, infra.getNetwork().getTls());
        EObject observability = (EObject) value(infra, "observability");
        assertEquals(Boolean.FALSE, value(observability, "tracesEnabled"));
        assertEquals(Boolean.FALSE, value(infra, "spotAllowed"));
        assertEquals(Boolean.FALSE, value(infra, "piiData"));
    }

    @Test
    public void generatedIngressPreservesTlsAndIngressClass() throws Exception {
        InMemoryFileSystemAccess output = generate("C00");
        String ingress = generatedText(output, "runtime/k8s/ingress.yaml");
        System.out.println("C00 GENERATED INGRESS:\n" + ingress);
        System.out.println("GENERATOR CLASS: " + MlopsGenerator.class.getResource("MlopsGenerator.class"));
        assertTrue(ingress.contains("  ingressClassName: \"nginx\""),
            "Missing configured Ingress class. Actual generated Ingress:\n" + ingress);
        assertTrue(ingress.contains("  tls:\n    - hosts:\n        - \"vision.azure.company.com\"\n"),
            "Missing configured TLS host. Actual generated Ingress:\n" + ingress);
        assertTrue(ingress.contains("      secretName: validation-c00-serving-tls"),
            "Missing TLS Secret reference. Actual generated Ingress:\n" + ingress);
        assertTrue(generatedText(output, "validation-c00/README.md").contains("validation-c00-serving-tls"),
            "Generated README must document the certificate Secret prerequisite");
    }

    @Test
    public void privateIngressDoesNotEnableTlsWhenFalse() throws Exception {
        String ingress = generatedText(generate("C01"), "runtime/k8s/ingress.yaml");
        assertFalse(ingress.contains("  tls:"));
        assertFalse(ingress.contains("secretName:"));
    }

    private InMemoryFileSystemAccess generate(String id) throws Exception {
        EObject root = parse(id, resourceSets.get());
        assertTrue(errors(root).isEmpty(), id + " must validate before checking generated content");
        InMemoryFileSystemAccess output = fileSystems.get();
        GeneratorContext context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        new MlopsGenerator().doGenerate(root.eResource(), output, context);
        return output;
    }

    private String generatedText(InMemoryFileSystemAccess output, String suffix) {
        List<CharSequence> matches = output.getTextFiles().entrySet().stream()
            .filter(entry -> entry.getKey().endsWith("/" + suffix))
            .map(entry -> entry.getValue()).collect(Collectors.toList());
        assertEquals(1, matches.size(), "Expected one generated file ending with " + suffix);
        // File-system postprocessors may use the editor/platform line separator.
        return matches.get(0).toString().replace("\r\n", "\n").replace('\r', '\n');
    }

    private void verify(String id) throws Exception {
        String[] scenario = scenario(id);
        EObject root = parse(id, resourceSets.get());
        List<Issue> issues = errors(root);
        boolean valid = "ACCEPT".equals(scenario[2]);
        if (!"linking".equals(scenario[3])) {
            assertTrue(root.eResource().getErrors().isEmpty(),
                id + " must parse/link before its semantic rule is assessed: "
                + root.eResource().getErrors());
        }
        if (valid) {
            assertTrue(issues.isEmpty(), id + " false alarm: " + issues);
        } else {
            assertFalse(issues.isEmpty(), id + " was incorrectly accepted");
            assertTrue(issues.stream().anyMatch(i -> i.getMessage().contains(scenario[4])),
                id + " did not report the intended fault: " + issues);
            issues.forEach(issue -> System.out.println("DIAGNOSTIC\t" + id
                + "\t" + issue.getCode() + "\t" + issue.getUriToProblem()
                + "\t" + issue.getMessage()));
            assertTrue(issues.stream().allMatch(i -> isExpectedIssue(i, scenario, root)),
                id + " also reports unrelated errors: " + issues);
        }

        InMemoryFileSystemAccess output = fileSystems.get();
        GeneratorContext context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        MlopsGenerator generator = new MlopsGenerator();
        if (valid) {
            assertDoesNotThrow(() -> generator.doGenerate(root.eResource(), output, context), id);
            assertFalse(output.getTextFiles().isEmpty(), id + " generated no artifacts");
        } else {
            assertThrows(IllegalStateException.class,
                () -> generator.doGenerate(root.eResource(), output, context), id);
            assertTrue(output.getTextFiles().isEmpty(), id + " emitted artifacts before rejecting");
        }
        System.out.println("VALIDATION\t" + id + "\t" + scenario[2]
            + "\t" + scenario[3] + "\tassertions_passed");
    }


    private boolean isExpectedIssue(Issue issue, String[] scenario, EObject root) {
        if (issue.getMessage().contains(scenario[4])) {
            return true;
        }
        if (!"S10".equals(scenario[0])) {
            return false;
        }
        // Xtext linking and EMF proxy validation can diagnose the same Edge.to.
        // Accept the additional proxy diagnostic only at that exact broken edge.
        String message = issue.getMessage().toLowerCase(java.util.Locale.ROOT);
        if (!message.contains("proxy") || !message.contains("resolv")
                || !message.contains("'to'")) {
            return false;
        }
        List<Edge> edges = EcoreUtil2.getAllContentsOfType(root, Edge.class);
        if (edges.isEmpty()) {
            return false;
        }
        Edge intendedEdge = edges.get(0); // S10 changes only the first Edge.to.
        Object target = intendedEdge.eGet(
            intendedEdge.eClass().getEStructuralFeature("to"), false);
        return target instanceof EObject && ((EObject) target).eIsProxy()
            && EcoreUtil.getURI(intendedEdge).equals(issue.getUriToProblem());
    }

    private EObject parse(String id, ResourceSet resourceSet) throws Exception {
        String filename = scenario(id)[1];
        return parser.parse(read("validation/" + filename),
            URI.createURI("memory:/validation/" + filename), resourceSet);
    }

    private List<Issue> errors(EObject root) {
        return validator.validate(root.eResource(), CheckMode.ALL, CancelIndicator.NullImpl)
            .stream().filter(i -> i.getSeverity() == Severity.ERROR).collect(Collectors.toList());
    }

    private Object value(EObject object, String feature) {
        return object.eGet(object.eClass().getEStructuralFeature(feature));
    }

    private String[] scenario(String id) throws Exception {
        return read("validation/scenarios.tsv").lines()
            .skip(1).map(line -> line.split("\t", -1))
            .filter(row -> row[0].equals(id)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown scenario: " + id));
    }

    private String read(String path) throws Exception {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            assertNotNull(input, "Missing test resource: " + path);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
