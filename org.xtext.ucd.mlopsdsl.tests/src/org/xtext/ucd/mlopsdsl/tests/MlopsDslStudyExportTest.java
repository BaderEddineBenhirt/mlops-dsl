package org.xtext.ucd.mlopsdsl.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.xtext.ucd.mlopsdsl.generator.MlopsGenerator;

/** Real Xtext observations, without embedding the external study's expected answers. */
@ExtendWith(InjectionExtension.class)
@InjectWith(MlopsDslInjectorProvider.class)
public class MlopsDslStudyExportTest {
    @Inject private ParseHelper<EObject> parser;
    @Inject private IResourceValidator validator;
    @Inject private Provider<XtextResourceSet> resourceSets;
    @Inject private Provider<InMemoryFileSystemAccess> fileSystems;

    @Test
    public void exportActualStudyObservations() throws Exception {
        String jobs = System.getProperty("mlops.study.jobs");
        String source = System.getProperty("mlops.study.source");
        assumeTrue(jobs != null || source != null, "Opt-in study exporter, not a configuration assertion");
        if (jobs != null) {
            List<String> lines = Files.readAllLines(Path.of(jobs), StandardCharsets.UTF_8);
            assertEquals("id\tsource\tdestination", lines.get(0));
            for (String line : lines.subList(1, lines.size())) {
                if (line.isBlank()) continue;
                String[] row = line.split("\t", -1);
                assertEquals(3, row.length, "Malformed job row");
                export(Path.of(row[1]), Path.of(row[2]));
            }
        } else {
            assertNotNull(System.getProperty("mlops.study.output"));
            export(Path.of(source), Path.of(System.getProperty("mlops.study.output")));
        }
    }

    private void export(Path source, Path destination) throws Exception {
        destination = destination.toAbsolutePath().normalize();
        assertFalse(Files.exists(destination), "Preserve earlier exports: " + destination);
        Files.createDirectories(destination);
        byte[] input = Files.readAllBytes(source);
        EObject model = parser.parse(new String(input, StandardCharsets.UTF_8),
            URI.createURI("memory:/study/input.mlops"), resourceSets.get());
        List<String> diagnostics = new ArrayList<>();
        validator.validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl).stream()
            .filter(i -> i.getSeverity() == Severity.ERROR)
            .forEach(i -> diagnostics.add(i.getMessage()));
        InMemoryFileSystemAccess output = fileSystems.get();
        GeneratorContext context = new GeneratorContext();
        context.setCancelIndicator(CancelIndicator.NullImpl);
        String generation = "ACCEPT", rejection = "";
        try {
            new MlopsGenerator().doGenerate(model.eResource(), output, context);
        } catch (IllegalStateException ex) {
            generation = "REJECT";
            rejection = String.valueOf(ex.getMessage());
        }
        Map<String, String> manifest = new TreeMap<>();
        if ("ACCEPT".equals(generation)) {
            for (Map.Entry<String, CharSequence> entry : output.getTextFiles().entrySet()) {
                String key = entry.getKey().replace('\\', '/');
                int marker = key.indexOf("generated/");
                assertTrue(marker >= 0, "Unexpected generator output key: " + key);
                int slash = key.indexOf('/', marker + "generated/".length());
                assertTrue(slash >= 0, "Missing generated project directory");
                String relative = key.substring(slash + 1);
                Path file = destination.resolve("artifacts").resolve(relative).normalize();
                assertTrue(file.startsWith(destination.resolve("artifacts")));
                assertFalse(Files.exists(file), "Duplicate generated path");
                Files.createDirectories(file.getParent());
                byte[] bytes = entry.getValue().toString().getBytes(StandardCharsets.UTF_8);
                Files.write(file, bytes);
                manifest.put(relative, hash(bytes));
            }
            assertFalse(manifest.isEmpty(), "Accepted model has no generated artifacts");
        } else {
            assertTrue(output.getTextFiles().isEmpty(), "Rejected generation wrote partial output");
        }
        List<String> files = new ArrayList<>(), errors = new ArrayList<>();
        manifest.forEach((name, digest) -> files.add(json(name) + ":" + json(digest)));
        diagnostics.forEach(d -> errors.add(json(d)));
        byte[] generator = MlopsGenerator.class.getResourceAsStream("MlopsGenerator.class").readAllBytes();
        String receipt = "{\"producer\":\"MlopsDslStudyExportTest\",\"recorded_at_utc\":" + json(Instant.now().toString())
            + ",\"source_sha256\":" + json(hash(input)) + ",\"generator_class_sha256\":" + json(hash(generator))
            + ",\"java\":" + json(System.getProperty("java.version")) + ",\"generator\":" + json(generation)
            + ",\"rejection\":" + json(rejection) + ",\"diagnostics\":[" + String.join(",", errors)
            + "],\"artifacts\":{" + String.join(",", files) + "}}\n";
        Files.writeString(destination.resolve("receipt.json"), receipt, StandardCharsets.UTF_8);
        System.out.println("STUDY EXPORT: " + destination + " generator=" + generation);
    }

    private String hash(byte[] value) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value));
    }

    private String json(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            if (c == '"' || c == '\\') result.append('\\').append(c);
            else if (c < 32) result.append(String.format("\\u%04x", (int) c));
            else result.append(c);
        }
        return result.append('"').toString();
    }
}
