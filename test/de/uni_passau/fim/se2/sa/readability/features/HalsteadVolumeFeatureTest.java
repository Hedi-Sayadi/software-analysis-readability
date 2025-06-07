package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class HalsteadVolumeFeatureTest {
    private HalsteadVolumeFeature halsteadFeature;
    @BeforeEach
    void setUp() {
        halsteadFeature = new HalsteadVolumeFeature();
    }


    @Test
    void testSimpleFile1() throws IOException {
        String filePath = "resources/snippets/23.jsnp";
        String code = Files.readString(Paths.get(filePath));
        assertEquals(503,halsteadFeature.computeMetric(code));
    }

    @Test
    void testEmptyCode() {
        String code = "";
        double volume = halsteadFeature.computeMetric(code);
        assertEquals(0, volume, "Volume should be zero for empty code");
    }

}
