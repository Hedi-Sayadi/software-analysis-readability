package de.uni_passau.fim.se2.sa.readability.features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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

}
