package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberLinesFeatureTest {
    private NumberLinesFeature feature = new NumberLinesFeature();


    @Test
    public void testSimpleFile() {
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

