package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenEntropyFeatureTest {
    private final TokenEntropyFeature entropyFeature = new TokenEntropyFeature();
    @Test
    void testEmptyCode() {
        String code = "";
        double result = entropyFeature.computeMetric(code);
        assertEquals(0.0, result, "Entropy should be 0 for empty code");
    }

    @Test
    void testSimpleFile() throws IOException {
        String filePath = "resources/Simple.jsnp";
        String code = Files.readString(Paths.get(filePath));
        double result = entropyFeature.computeMetric(code);
        assertTrue(result > 0, "Entropy should be greater than 0");
    }
    @Test
    void testInvalidCode() {
        String code = "public { not valid }";
        double result = entropyFeature.computeMetric(code);
        assertEquals(0.0, result, "Invalid code should result in 0 entropy");
    }

    @Test
    void testGetIdentifier() {
        assertEquals("TokenEntropy", entropyFeature.getIdentifier());
    }

    @Test
    public void testCalculateEntropyWithKnownValues() {
        TokenEntropyFeature feature = new TokenEntropyFeature();

        Map<String, Integer> tokenCounts = new HashMap<>();
        tokenCounts.put("if", 2);
        tokenCounts.put("else", 2);
        tokenCounts.put("for", 4);

        int totalTokens = 8;

        double expectedEntropy = 1.5; //Calculated manually with the formula given in artemis

        double actualEntropy = feature.calculateEntropy(tokenCounts, totalTokens);
        assertEquals(expectedEntropy, actualEntropy);
    }



}
