package org.xtext.ucd.mlopsdsl.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
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

/** Actual Xtext/GCP generator observations. Separate from the archived Azure suite. */
@ExtendWith(InjectionExtension.class)
@InjectWith(MlopsDslInjectorProvider.class)
public class MlopsDslGcpEvaluationTest {
    @Inject private ParseHelper<EObject> parser;
    @Inject private IResourceValidator validator;
    @Inject private Provider<XtextResourceSet> resourceSets;
    @Inject private Provider<InMemoryFileSystemAccess> fileSystems;

    @Test
    public void gcpCaseExportsActualObservations() throws Exception {
        Path destination = Path.of(System.getProperty("mlops.gcp.output",
            "target/gcp-observations-" + System.currentTimeMillis())).toAbsolutePath();
        Files.createDirectories(destination.getParent());
        Files.createDirectory(destination);
        String original = read("validation/gcp/c10_fraud_gcp.mlops");
        List<String> rows = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        for (String line : read("validation/gcp/cases.tsv").lines().skip(1).collect(Collectors.toList())) {
            if (line.isBlank()) continue;
            String[] c = line.split("\t", -1);
            assertEquals(6, c.length, "Invalid GCP case matrix row");
            String source = changed(original, c);
            Path caseRoot = destination.resolve(c[0]);
            Files.createDirectories(caseRoot);
            Files.writeString(caseRoot.resolve("source.mlops"), source, StandardCharsets.UTF_8);
            EObject model = parse(source, c[0]);
            List<Issue> issues = errors(model);
            boolean fault = "fault".equals(c[1]);
            boolean target = issues.stream().anyMatch(i -> i.getMessage().contains(c[3]));
            boolean unrelated = issues.stream().anyMatch(i -> !i.getMessage().contains(c[3]));
            String outcome = fault ? (target && !unrelated ? "DETECTED" : issues.isEmpty() ? "MISSED" : "BLOCKED")
                : issues.isEmpty() ? "PASS" : "FALSE_ALARM";
            InMemoryFileSystemAccess output = fileSystems.get();
            String generation = "ACCEPT", message = "";
            try {
                generate(model, output);
            } catch (IllegalStateException ex) {
                generation = "REJECT";
                message = ex.getMessage();
            }
            if (!model.eResource().getErrors().isEmpty()
                    || !(fault ? "DETECTED" : "PASS").equals(outcome)
                    || fault == "ACCEPT".equals(generation)
                    || (fault && !output.getTextFiles().isEmpty())) failures.add(c[0]);
            List<String> manifest = new ArrayList<>();
            if ("ACCEPT".equals(generation)) {
                for (Map.Entry<String, CharSequence> entry : output.getTextFiles().entrySet()) {
                    String key = entry.getKey().replace('\\', '/');
                    String marker = "generated/fraud-gcp/";
                    int index = key.indexOf(marker);
                    assertTrue(index >= 0, "Unexpected generated path: " + key);
                    String relative = key.substring(index + marker.length());
                    Path file = caseRoot.resolve("generated").resolve(relative).normalize();
                    assertTrue(file.startsWith(caseRoot.resolve("generated")));
                    Files.createDirectories(file.getParent());
                    byte[] bytes = entry.getValue().toString().getBytes(StandardCharsets.UTF_8);
                    Files.write(file, bytes);
                    manifest.add("{\"path\":" + json(relative) + ",\"sha256\":" + json(hash(bytes)) + "}");
                }
                if (manifest.isEmpty()) failures.add(c[0] + ":empty-export");
            }
            String diagnostics = issues.stream().map(i -> "{\"code\":" + json(i.getCode())
                + ",\"message\":" + json(i.getMessage()) + "}").collect(Collectors.joining(","));
            rows.add("{\"case_id\":" + json(c[0]) + ",\"category\":" + json(c[1])
                + ",\"source_sha256\":" + json(hash(source.getBytes(StandardCharsets.UTF_8)))
                + ",\"outcome\":" + json(outcome) + ",\"generator\":" + json(generation)
                + ",\"generator_message\":" + json(message) + ",\"diagnostics\":[" + diagnostics
                + "],\"generated_manifest\":[" + String.join(",", manifest) + "]}");
            System.out.println("GCP\t" + c[0] + "\t" + outcome + "\tgenerator=" + generation);
        }
        Files.writeString(destination.resolve("dsl-observations.json"),
            "{\"recorded_at_utc\":" + json(Instant.now().toString())
            + ",\"java\":" + json(System.getProperty("java.version"))
            + ",\"base_sha256\":" + json(hash(original.getBytes(StandardCharsets.UTF_8)))
            + ",\"matrix_sha256\":" + json(hash(read("validation/gcp/cases.tsv").getBytes(StandardCharsets.UTF_8)))
            + ",\"generator_class_sha256\":" + json(hash(MlopsGenerator.class.getResourceAsStream("MlopsGenerator.class").readAllBytes()))
            + ",\"cases\":[" + String.join(",", rows) + "]}\n", StandardCharsets.UTF_8);
        System.out.println("GCP OBSERVATIONS: " + destination);
        System.out.println("GENERATOR CLASS: " + MlopsGenerator.class.getResource("MlopsGenerator.class"));
        assertTrue(failures.isEmpty(), "GCP failures, raw observations preserved: " + failures);
    }

    @Test
    public void gcpComputeAndNetworkPreserveIntent() throws Exception {
        EObject root = parse(read("validation/gcp/c10_fraud_gcp.mlops"), "GCP-renderer");
        assertTrue(errors(root).isEmpty(), () -> errors(root).toString());
        InMemoryFileSystemAccess output = fileSystems.get();
        generate(root, output);
        String network = text(output, "modules/network/main.tf");
        assertTrue(network.contains("[\"10.72.1.0/24\", \"10.72.2.0/24\"][count.index]"));
        assertTrue(network.contains("google_compute_subnetwork.private[*].id"));
        String compute = text(output, "modules/compute/main.tf");
        assertTrue(compute.contains("resource \"google_container_cluster\" \"main\""));
        assertTrue(compute.contains("resource \"google_container_node_pool\" \"cpu\""));
        assertTrue(compute.contains("initial_node_count = 3"));
        assertTrue(compute.contains("min_node_count = 2"));
        assertTrue(compute.contains("max_node_count = 5"));
        assertTrue(compute.contains("machine_type = \"e2-standard-2\""));
        assertTrue(text(output, "modules/observability/main.tf").contains("variable \"account_id\""));
    }

    private String changed(String original, String[] c) {
        original = original.replace("\r\n", "\n");
        if (c[4].isEmpty()) return original;
        String[] old = c[4].split("\\|\\|", -1), replacement = c[5].split("\\|\\|", -1);
        assertEquals(old.length, replacement.length);
        String result = original;
        for (int i = 0; i < old.length; i++) {
            String target = old[i].replace("\\n", "\n");
            assertTrue(result.contains(target), "Missing mutation target for " + c[0]);
            result = result.replace(target, replacement[i].replace("\\n", "\n"));
        }
        return result;
    }

    private EObject parse(String source, String id) throws Exception {
        return parser.parse(source, URI.createURI("memory:/gcp/" + id + ".mlops"), resourceSets.get());
    }

    private List<Issue> errors(EObject root) {
        return validator.validate(root.eResource(), CheckMode.ALL, CancelIndicator.NullImpl)
            .stream().filter(i -> i.getSeverity() == Severity.ERROR).collect(Collectors.toList());
    }

    private void generate(EObject root, InMemoryFileSystemAccess output) {
        GeneratorContext context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        new MlopsGenerator().doGenerate(root.eResource(), output, context);
    }

    private String text(InMemoryFileSystemAccess output, String suffix) {
        return output.getTextFiles().entrySet().stream().filter(e -> e.getKey().endsWith("/" + suffix))
            .map(e -> e.getValue().toString().replace("\r\n", "\n")).findFirst().orElseThrow();
    }

    private String hash(byte[] bytes) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }

    private String json(String text) {
        if (text == null) return "null";
        StringBuilder result = new StringBuilder("\"");
        for (char c : text.toCharArray()) {
            if (c == '"' || c == '\\') result.append('\\').append(c);
            else if (c < 32) result.append(String.format("\\u%04x", (int) c));
            else result.append(c);
        }
        return result.append('"').toString();
    }

    private String read(String path) throws Exception {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            assertNotNull(input, "Missing test resource: " + path);
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
