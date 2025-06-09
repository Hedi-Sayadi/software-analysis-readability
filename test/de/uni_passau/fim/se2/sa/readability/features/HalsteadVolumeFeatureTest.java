package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HalsteadVolumeFeatureTest {
    private HalsteadVolumeFeature halsteadFeature = new HalsteadVolumeFeature();

    @Test
    void testSimpleFile() throws IOException {
        String filePath = "resources/Simple.jsnp";
        String code = Files.readString(Paths.get(filePath));
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

    //I added the tests below to improve the mutation after seeing the report of mutation :

    @Test
    void testMathematicalFormulaCorrectness() {
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
        assertTrue(bigVolume >smallVolume );
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

        String multipleMethodsCode = """
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
        double bigVolume = halsteadFeature.computeMetric(multipleMethodsCode);

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
