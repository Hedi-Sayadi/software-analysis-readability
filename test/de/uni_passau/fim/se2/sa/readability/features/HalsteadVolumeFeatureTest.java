package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HalsteadVolumeFeatureTest {
    private HalsteadVolumeFeature halsteadFeature = new HalsteadVolumeFeature();

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
        assertTrue(halsteadFeature.computeMetric(code)>0);
    }

    @Test
    void testEmptyCode() {
        String code = "";
        double volume = halsteadFeature.computeMetric(code);
        assertEquals(0, volume, "Volume should be zero for empty code");

    }

    @Test
    void testGetIdentifier() {
        assertEquals("HalsteadVolume", halsteadFeature.getIdentifier());
    }

    //I added the tests below to improve the mutation coverage after seeing the report: mutation.xml :

    @Test
    void testSmallandBigCode() {
        String smallCode = """
            public class Test {
                int x = 1;
            }
            """;

        String bigCode = """
            public class Test {
                int x = 1;
                int y = 2;
                int z = x + y;
                int w = x * y;
                int v = x - y;
                int u = x / y;
            }
            """;

        double smallVolume = halsteadFeature.computeMetric(smallCode);
        double bigVolume = halsteadFeature.computeMetric(bigCode);
        assertTrue(bigVolume > smallVolume );
        assertTrue(smallVolume > 0 && bigVolume < 100);
        assertTrue(bigVolume > 20 && bigVolume < 500);
    }

    @Test
    void testMultipleBody() {
        String singleMethodCode = """
            public class Calculator {
                public int add(int a, int b) {
                    return a + b;
                }
            }
            """;

        String multiMethodsCode = """
            public class Calculator {
                public int add(int a, int b) {
                    return a + b;
                }
                
                public int multiply(int x, int y) {
                    int result = x * y;
                    return result;
                }
            }
            """;

        double smallVolume = halsteadFeature.computeMetric(singleMethodCode);
        double bigVolume = halsteadFeature.computeMetric(multiMethodsCode);

        assertTrue(bigVolume > smallVolume);
    }

    @Test
    public void testHalsteadVolumeWithKnownInput() {
        HalsteadVolumeFeature feature = new HalsteadVolumeFeature();

        String code = """
     {
        int a = b + c;
    }
    """;
        double result = feature.computeMetric(code);
        double expectedVolume = 11.61; //Calculated manually: 5 * log2(5)

        assertEquals(expectedVolume, result, 0.01); //I added the 0.01 to check the two numbers are close enough : i asked an llm about this
    }

}
