package de.uni_passau.fim.se2.sa.readability.utils;

import org.junit.jupiter.api.Test;
import weka.core.Instances;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ClassifyTest {
    @Test
    void loadDatasetWithCSVFile() throws  IOException {
        String csvContent = String.join("\n",
                "File,NumberLines,TokenEntropy,HalsteadVolume,Truth",
                "196.jsnp,11.00,4.23,63.00,N",
                "199.jsnp,11.00,3.28,9.00,Y"
        );

        Path csvDataset = Files.createTempFile("dataset", ".csv");
        Files.writeString(csvDataset, csvContent);

        Instances dataset = Classify.loadDataset(csvDataset.toFile());
        assertEquals(2, dataset.numInstances(), "Should have 2 instances");
        assertEquals("Y", dataset.instance(1).stringValue(4));
    }

}