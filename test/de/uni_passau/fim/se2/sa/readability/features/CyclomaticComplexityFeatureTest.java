package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CyclomaticComplexityFeatureTest {
    private final CyclomaticComplexityFeature complexityFeature = new CyclomaticComplexityFeature();


    @Test
    void testSimpleFile() throws IOException {
        String filePath = "resources/Simple.jsnp";
        String code = Files.readString(Paths.get(filePath));
        assertEquals(4.0, complexityFeature.computeMetric(code));
    }

    @Test
    void testEmptyMethod() {
        String code = "void foo() { }";
        assertEquals(1.0, complexityFeature.computeMetric(code), "Empty method should have complexity 1");
    }

}
