package de.uni_passau.fim.se2.sa.readability.utils;

import de.uni_passau.fim.se2.sa.readability.features.FeatureMetric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Preprocess {

    /**
     * A value of 3.6 splits the Scalabrino Dataset into almost evenly balanced binary classes.
     */
    private static final double TRUTH_THRESHOLD = 3.6;

    /**
     * Traverses through each java snippet in the specified source directory and computes the specified list of feature metrics.
     * Each snippet is then saved together with its extracted feature values and the truth score as one row in the csv, resulting
     * in the scheme [File,NumberLines,TokenEntropy,HalsteadVolume,Truth].
     * <p>
     * The File column value corresponds to the respective file name.
     * All feature values are rounded to two decimal places.
     * The truth value corresponds to a String that is set to the value "Y" if the mean rater score of a given snippet is greater or equal
     * than the TRUTH_THRESHOLD. Otherwise, if the mean score is lower than the TRUTH_THRESHOLD the truth value String is set to "N".
     *
     * @param sourceDir      the directory containing java snippet (.jsnp) files.
     * @param truth          the ground truth csv file containing the human readability ratings of the code snippets.                       `
     * @param csv            the builder for the csv.
     * @param featureMetrics the list of specified features via the cli.
     * @throws IOException if the source directory or the truth file does not exist.
     */

    public static void collectCSVBody(Path sourceDir, File truth, StringBuilder csv, List<FeatureMetric> featureMetrics) throws IOException {
        Map<String, Double> truthScores = parseTruthFile(truth);
        List<Path> snippetFiles = getSortedSnippetFiles(sourceDir);

        for (Path snippetFile : snippetFiles) {
            String row = buildCSVRow(snippetFile, truthScores, featureMetrics);
            csv.append(row);
        }
    }


    /**
     * Helper method to parse the truth CSV file and extract mean rater scores for each snippet.
     * The CSV format has raters as rows, snippets as columns, and the last row contains mean scores.
     * Header format: Rater,Snippet1,Snippet2,Snippet3,...,Snippet200
     * Last row format: Mean,4.22,3.56,3.56,...
     *
     * @param truthFile the CSV file containing human readability ratings
     * @return a map from filename (e.g., "1.jsnp") to mean rater score
     * @throws IOException if the file cannot be read
     */
    private static Map<String, Double> parseTruthFile(File truthFile) throws IOException {
        Map<String, Double> truthScores = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(truthFile));
        String headerLine = br.readLine();

        String[] headers = headerLine.split(",");
        List<String> snippetNames = getSnippetColumnNames(headers);

        String line;
        String meanLine=null;
        // Read all the lines to find the "Mean" row
        while ((line = br.readLine()) != null) {
                if (line.startsWith("Mean")) {
                    meanLine = line;
                    break;
                }
            }

            String[] meanValues = meanLine.split(",");

            MapSnippetToMeanScores(meanValues,snippetNames, truthScores);

        return truthScores;
    }

    private static List<Path> getSortedSnippetFiles(Path sourceDir) throws IOException {
        List<Path> snippetFiles = new ArrayList<>();
        //help of llm
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.jsnp")) {
            for (Path file : stream) {
                snippetFiles.add(file);
            }
        }

        //help of llm
        snippetFiles.sort(Comparator.comparingInt(path -> {
            String name = path.getFileName().toString();
            return Integer.parseInt(name.substring(0, name.lastIndexOf(".jsnp")));
        }));

        return snippetFiles;
    }

    private static String buildCSVRow(Path snippetFile, Map<String, Double> truthScores, List<FeatureMetric> featureMetrics) throws IOException {
        String fileName = snippetFile.getFileName().toString();
        String content = Files.readString(snippetFile);

        StringBuilder row = new StringBuilder();
        row.append(fileName);

        for (FeatureMetric metric : featureMetrics) {
            double value = metric.computeMetric(content);
            row.append(String.format( ",%.2f", value));
        }

        String truthValue = "N";
        Double meanScore = truthScores.get(fileName);
        if (meanScore != null && meanScore >= TRUTH_THRESHOLD) {
            truthValue = "Y";
        }

        row.append(",").append(truthValue);
        row.append(System.lineSeparator());

        return row.toString();
    }


    private static void MapSnippetToMeanScores(String[] meanValues,List<String> snippetNames,Map<String, Double> truthScores) {
        for (int i = 1; i < meanValues.length && i <= snippetNames.size(); i++) {
            String snippetName = snippetNames.get(i - 1);
            //help of llm
            double meanScore = Double.parseDouble(meanValues[i].trim());

            // Convert "SnippetNumber" to "Number.jsnp"
            if (snippetName.startsWith("Snippet")) {
                String snippetNumber = snippetName.substring("Snippet".length());
                String fileName = snippetNumber + ".jsnp";
                truthScores.put(fileName, meanScore);
            }
        }
    }

    public static List<String> getSnippetColumnNames(String[] headers) {
        List<String> snippetNames = new ArrayList<>();
        for (int i = 1; i < headers.length; i++) { // Skip first column "Rater"
            snippetNames.add(headers[i].trim());
        }
        return snippetNames;
    }


}

