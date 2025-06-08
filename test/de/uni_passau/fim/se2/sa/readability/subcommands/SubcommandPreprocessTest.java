package de.uni_passau.fim.se2.sa.readability.subcommands;

import de.uni_passau.fim.se2.sa.readability.ReadabilityAnalysisMain;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubcommandPreprocessTest {


    @Test
    void testPreprocessCommandRunsSuccessfully() throws IOException {
        File tempDir = Files.createTempDirectory("snippets").toFile();
        File snippet = new File(tempDir, "snippet1.jsnp");
        Files.writeString(snippet.toPath(), "public class Test {}");

        File truthFile = File.createTempFile("truth", ".csv");
        Files.writeString(truthFile.toPath(), "Rater,snippet1\nMean,5.0");

        File targetFile = new File(Files.createTempDirectory("target").toFile(), "result.csv");

        List<String> args = List.of(
                "-s", tempDir.getAbsolutePath(),
                "-g", truthFile.getAbsolutePath(),
                "-t", targetFile.getAbsolutePath(),
                "LINES"
        );

        SubcommandPreprocess command = new SubcommandPreprocess();
        CommandLine cmd = new CommandLine(command);
        int exitCode = cmd.execute(args.toArray(new String[0]));

        assertEquals(0, exitCode);
        assertTrue(targetFile.exists(), "Target file was not created");

        String output = Files.readString(targetFile.toPath());
        System.out.println("Output  " + output);

        assertTrue(output.contains("snippet1.jsnp"), "Output does not contain expected file name");
        assertTrue(output.contains("NumberLines"), "Header missing NumberLines metric");
    }


    @Test
    public void testPreprocessCommand() {
        CommandLine cmd = new CommandLine(new ReadabilityAnalysisMain());
        CommandLine.ParseResult result = cmd.parseArgs("preprocess",
                "-s","resources/snippets",
                "-g","resources/truth_scores.csv",
                "-t","resources/data.csv",
                "LINES",
                "TOKEN_ENTROPY",
                "H_VOLUME");
        assertEquals("preprocess", result.subcommand().commandSpec().name());
    }


}