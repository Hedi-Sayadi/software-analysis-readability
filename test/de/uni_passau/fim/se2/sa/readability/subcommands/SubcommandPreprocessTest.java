package de.uni_passau.fim.se2.sa.readability.subcommands;

import de.uni_passau.fim.se2.sa.readability.ReadabilityAnalysisMain;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        CommandLine.ParseResult result = cmd.parseArgs(
                "preprocess",
                "-s","resources/snippets",
                "-g","resources/truth_scores.csv",
                "-t","resources/data.csv",
                "LINES",
                "TOKEN_ENTROPY",
                "H_VOLUME");
        assertEquals("preprocess", result.subcommand().commandSpec().name());
    }

    @Test
    void setSourceDirectoryError() {
        SubcommandPreprocess cmd = new SubcommandPreprocess();
        CommandLine cmdLine = new CommandLine(cmd);
        cmd.spec = cmdLine.getCommandSpec();

        File invalidDir = new File("invalidPath");
        Exception e = assertThrows(CommandLine.ParameterException.class,
                () -> cmd.setSourceDirectory(invalidDir));
        assertTrue(e.getMessage().contains("Source directory does not exist."));
    }

    @Test
    void testSetTargetFileThrowsWhenDirectoryDoesNotExist() {
        SubcommandPreprocess cmd = new SubcommandPreprocess();
        cmd.spec = new CommandLine(cmd).getCommandSpec();

        File invalidTargetFile = new File("nonExistingDirectory/output.csv");

        CommandLine.ParameterException exception = assertThrows(
                CommandLine.ParameterException.class,
                () -> cmd.setTargetFile(invalidTargetFile)
        );

        assertTrue(exception.getMessage().contains("Target directory does not exist."));
    }

    @Test
    void testSetTargetFileThrowsWhenFileIsNotCsv() throws IOException {
        SubcommandPreprocess cmd = new SubcommandPreprocess();
        cmd.spec = new CommandLine(cmd).getCommandSpec();

        Path tempDirPath = Files.createTempDirectory("tempDirPrefix");
        File tempDir = tempDirPath.toFile();

        File invalidFile = new File(tempDir, "output.txt");

        CommandLine.ParameterException exception = assertThrows(
                CommandLine.ParameterException.class,
                () -> cmd.setTargetFile(invalidFile)
        );

        assertTrue(exception.getMessage().contains("Target file must end with a .csv suffix"));
    }

    @Test
    void setTruthWithNonExistentFile() {
        SubcommandPreprocess cmd = new SubcommandPreprocess();
        CommandLine cmdLine = new CommandLine(cmd);
        cmd.spec = cmdLine.getCommandSpec();

        File nonExistentFile = new File("NonExistentTruth.csv");

        CommandLine.ParameterException exception = assertThrows(
                CommandLine.ParameterException.class,
                () -> cmd.setTruth(nonExistentFile)
        );

        assertEquals("Truth file does not exist.", exception.getMessage());
    }
}