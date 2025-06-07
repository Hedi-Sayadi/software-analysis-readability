package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberLinesFeatureTest {

    @Test
    public void testSimpleFile() throws IOException {
        NumberLinesFeature feature = new NumberLinesFeature();

        String filePath = "resources/Simple.jsnp";
        String code = Files.readString(Paths.get(filePath));

        double result = feature.computeMetric(code);

        assertEquals(15.0, result);
    }

    @Test
    public void testEmptyCode() {
        NumberLinesFeature feature = new NumberLinesFeature();
        assertEquals(0.0, feature.computeMetric(""));
    }

}

