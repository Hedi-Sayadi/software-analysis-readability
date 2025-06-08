package de.uni_passau.fim.se2.sa.readability.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreprocessTest {


    @Test
    public void getSnippetColumnNamesTest() {
        String[] headers = {"Rater", "Snippet1", "Snippet2", "Snippet3", "Snippet200"};
        List<String> snippetNames = Preprocess.getSnippetColumnNames(headers);
        assertEquals(4, snippetNames.size());
        assertEquals("Snippet1", snippetNames.get(0));
        assertEquals("Snippet2", snippetNames.get(1));
        assertEquals("Snippet3", snippetNames.get(2));
        assertEquals("Snippet200", snippetNames.get(3));
    }

    @Test
    public void parseTruthFileTest() throws IOException {
        //help of llm :
        File tempTruthFile = File.createTempFile("truthTemp",".csv");
        try (PrintWriter writer = new PrintWriter(tempTruthFile)) {
            writer.println("Rater,Snippet1,Snippet2,Snippet3");
            writer.println("Hedi,4.1,3.5,3.7");
            writer.println("Sayadi,4.3,3.7,3.5");
            writer.println("Mean,4.2,3.6,3.2");
        }

        Map<String, Double> result = Preprocess.parseTruthFile(tempTruthFile);
        assertEquals(3, result.size());
        assertEquals(4.2, result.get("1.jsnp"));
        assertEquals(3.6, result.get("2.jsnp"));
        assertEquals(3.2, result.get("3.jsnp"));
    }

    @Test
    public void getSortedSnippetFilesTest() throws IOException {
        Path tempDir = Files.createTempDirectory("Snippets-test");
        Files.createFile(tempDir.resolve("3.jsnp"));
        Files.createFile(tempDir.resolve("1.jsnp"));
        Files.createFile(tempDir.resolve("2.jsnp"));

        List<Path> snippetFiles = Preprocess.getSortedSnippetFiles(tempDir);

        assertEquals(3, snippetFiles.size());
        assertEquals("1.jsnp",snippetFiles.get(0).getFileName().toString());
        assertEquals("2.jsnp",snippetFiles.get(1).getFileName().toString());
        assertEquals("3.jsnp",snippetFiles.get(2).getFileName().toString());

    }

    @Test
    void testMapSnippetToMeanScores() throws Exception {
        String[] meanValues = {"Mean", "0.5", "0.8", "1.0"};
        List<String> snippetNames = List.of("Snippet1", "Snippet2", "Snippet3");
        Map<String, Double> truthScores = new HashMap<>();

        Preprocess.MapSnippetToMeanScores(meanValues,snippetNames,truthScores);

        assertEquals(3, truthScores.size());
        assertEquals(0.5, truthScores.get("1.jsnp"));
        assertEquals(0.8, truthScores.get("2.jsnp"));
        assertEquals(1.0, truthScores.get("3.jsnp"));
    }



    }



