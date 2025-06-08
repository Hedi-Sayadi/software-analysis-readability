package de.uni_passau.fim.se2.sa.readability.utils;

import org.junit.jupiter.api.Test;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void trainAndEvaluateOnValidDataset() throws Exception {
        String csvContent = String.join("\n",
                "File,NumberLines,TokenEntropy,HalsteadVolume,Truth",
                "1.jsnp,10.0,4.1,60.0,Y",
                "2.jsnp,11.0,3.2,55.0,N",
                "3.jsnp,12.0,3.3,58.0,Y",
                "4.jsnp,10.0,4.0,59.0,N",
                "5.jsnp,13.0,3.9,57.0,Y",
                "6.jsnp,11.0,4.4,53.0,N",
                "7.jsnp,12.0,3.1,52.0,Y",
                "8.jsnp,14.0,4.2,61.0,N",
                "9.jsnp,10.0,3.5,62.0,Y",
                "10.jsnp,15.0,3.8,64.0,N"
        );

        Path csvDataset = Files.createTempFile("dataset", ".csv");
        Files.writeString(csvDataset, csvContent);

        Instances dataset = Classify.loadDataset(csvDataset.toFile());

        Evaluation evaluation = Classify.trainAndEvaluate(dataset);

        //help from llm: how to calculate the accuracy
        double accuracy = evaluation.pctCorrect() / 100.0;
        assertTrue(accuracy >= 0 && accuracy <= 1, "Accuracy should be within [0,1]");

    }



}