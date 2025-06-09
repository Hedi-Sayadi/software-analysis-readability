package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CyclomaticComplexityFeatureTest {
    private final CyclomaticComplexityFeature complexityFeature = new CyclomaticComplexityFeature();


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
        assertEquals(4.0, complexityFeature.computeMetric(code));
    }

    @Test
    void testEmptyMethod() {
        String code = "void foo() { }";
        assertEquals(1.0, complexityFeature.computeMetric(code), "Empty method should have complexity 1");
    }


    @Test
    void testInvalidCode() {
        String code = "void invalid { ; invalid}";
        assertEquals(0.0, complexityFeature.computeMetric(code));
    }

    @Test
    void testGetIdentifier() {
        assertEquals("CyclomaticComplexity", complexityFeature.getIdentifier());
    }


}
