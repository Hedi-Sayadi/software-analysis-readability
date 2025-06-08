package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberLinesFeatureTest {
    private NumberLinesFeature feature = new NumberLinesFeature();


    @Test
    public void testSimpleFile() throws IOException {
        String filePath = "resources/Simple.jsnp";
        String code = Files.readString(Paths.get(filePath));
        double result = feature.computeMetric(code);
        assertEquals(15.0, result);
    }

    @Test
    public void testEmptyCode() {
        String code = "";
        double result = feature.computeMetric(code);
        assertEquals(0.0, result);
    }

}

