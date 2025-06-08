package de.uni_passau.fim.se2.sa.readability.utils;

import de.uni_passau.fim.se2.sa.readability.features.FeatureMetric;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreprocessTest {

    @Test
    public void testGetSnippetColumnNames() {
        String[] headers = {"Rater", "Snippet1", "Snippet2", "Snippet3", "Snippet200"};
        List<String> snippetNames = Preprocess.getSnippetColumnNames(headers);
        assertEquals(4, snippetNames.size());
        assertEquals("Snippet1", snippetNames.get(0));
        assertEquals("Snippet2", snippetNames.get(1));
        assertEquals("Snippet3", snippetNames.get(2));
        assertEquals("Snippet200", snippetNames.get(3));
    }

    @Test
    public void testParseTruthFileTest() throws IOException {
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
    public void testGetSortedSnippetFiles() throws IOException {
        Path tempDir = Files.createTempDirectory("Snippets");
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

    @Test
    void buildCSVRowReturnsCorrectFormatWithNo() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");
        tempDir.toFile().deleteOnExit();

        Path testFile = tempDir.resolve("test.jsnp");
        Files.writeString(testFile, "public class Test {}");

        List<FeatureMetric> metrics = List.of(
                new LineMetric(),
                new EntropyMetric(),
                new VolumeMetric()
        );
        Map<String, Double> truthScores = Map.of("test.jsnp", 0.6);

        String result = Preprocess.buildCSVRow(testFile, truthScores, metrics);

        String expected = "test.jsnp,15.00,2.34,100.59,N"+ System.lineSeparator();
        assertEquals(expected, result);
    }

    @Test
    void buildCSVRowReturnsCorrectFormatWithYes() throws IOException {
        Path tempDir = Files.createTempDirectory("testDir");
        tempDir.toFile().deleteOnExit();

        Path testFile = tempDir.resolve("test.jsnp");
        Files.writeString(testFile, "public class Test {}");

        List<FeatureMetric> metrics = List.of(
                new LineMetric(),
                new EntropyMetric(),
                new VolumeMetric()
        );


        Map<String, Double> truthScores = Map.of("test.jsnp", 3.7);

        String result = Preprocess.buildCSVRow(testFile, truthScores, metrics);

        String expected = "test.jsnp,15.00,2.34,100.59,Y"+ System.lineSeparator();
        assertEquals(expected, result);
    }

    @Test
    void buildCSVRowThrowsIOException() {
        Path nonExistentFile = Paths.get("nonexistent.csv");
        assertThrows(IOException.class, () ->
                Preprocess.buildCSVRow(nonExistentFile, Map.of(), List.of()));
    }

    static class LineMetric extends FeatureMetric {
        private final double value;

        public LineMetric() {
            this(15.00);
        }

        public LineMetric(double value) {
            this.value = value;
        }

        @Override
        public double computeMetric(String codeSnippet) {
            return value;
        }

        @Override
        public String getIdentifier() {
            return "LineMetric";
        }
    }

    static class EntropyMetric extends FeatureMetric {
        private final double value;

        public EntropyMetric() {
            this(2.34);
        }

        public EntropyMetric(double value) {
            this.value = value;
        }

        @Override
        public double computeMetric(String codeSnippet) {
            return value;
        }

        @Override
        public String getIdentifier() {
            return "EntropyMetric";
        }
    }

    static class VolumeMetric extends FeatureMetric {
        private final double value;

        public VolumeMetric() {
            this(100.59);
        }

        public VolumeMetric(double value) {
            this.value = value;
        }

        @Override
        public double computeMetric(String codeSnippet) {
            return value;
        }

        @Override
        public String getIdentifier() {
            return "VolumeMetric";
        }
    }
}
