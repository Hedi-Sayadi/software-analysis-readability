package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

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
    void testSimpleFile() {
        String code = """
        public void testMethod(){
            int x = 10;
            int y = 20;
            int z = x + y;
            x = x + y;
            y = x > 10 ? 10 : 20;

            List<Integer> list;
            if (list instanceof ArrayList) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(i);
                }
            }
            
        }
        """;
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
