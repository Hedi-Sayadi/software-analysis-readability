package de.uni_passau.fim.se2.sa.readability.subcommands;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SubcommandClassifyTest {


    @Test
    void testInvalidFileThrowsParameterException() {
        File nonExistentFile = new File("nonExistent.csv");
        SubcommandClassify cmd = new SubcommandClassify();
        CommandLine cmdLine = new CommandLine(cmd);
        int exitCode = cmdLine.execute("-d", nonExistentFile.getAbsolutePath());
        assertThrows(CommandLine.ParameterException.class,
                () -> cmd.setDataFile(nonExistentFile));
        assertNotEquals(0, exitCode);
    }

    @Test
    void testInvalidFileNonZeroExitCode() {
        File nonExistentFile = new File("nonExistent.csv");
        SubcommandClassify cmd = new SubcommandClassify();
        CommandLine cmdLine = new CommandLine(cmd);
        int exitCode = cmdLine.execute("-d", nonExistentFile.getAbsolutePath());
        assertNotEquals(0, exitCode);
    }


    @Test
    void testCallOnInvalidData() {
        SubcommandClassify cmd = new SubcommandClassify();
        CommandLine cmdLine = new CommandLine(cmd);
        cmd.spec = cmdLine.getCommandSpec();

        try {
            Path tempFilePath = Files.createTempFile("testInvalid", ".csv");
            Files.writeString(tempFilePath, "invalid,data,that,fails");

            cmd.setDataFile(tempFilePath.toFile());

            int result = cmd.call();
            assertEquals(1, result);

        } catch (IOException e) {
            fail("Test file setup failed: " + e.getMessage());
        }
    }



    //help from an llm
    @Test
    void callWithRealDataset() throws Exception {
        String csvContent = """
            File,NumberLines,TokenEntropy,HalsteadVolume,Truth
            1.jsnp,16.00,2.03,12.00,Y
            2.jsnp,30.00,2.95,221.01,N
            3.jsnp,33.00,3.15,179.67,N
            4.jsnp,24.00,1.67,49.50,Y
            5.jsnp,27.00,2.19,85.11,Y
            6.jsnp,18.00,3.42,53.77,Y
            7.jsnp,36.00,3.52,248.80,N
            8.jsnp,33.00,2.61,191.16,N
            9.jsnp,32.00,2.98,126.71,Y
            10.jsnp,25.00,3.52,215.22,N
            11.jsnp,14.00,2.52,50.19,Y
            12.jsnp,19.00,2.91,76.00,N
            13.jsnp,22.00,2.65,222.97,N
            14.jsnp,28.00,2.31,298.68,N
            15.jsnp,39.00,2.19,285.40,N
            16.jsnp,16.00,4.04,114.69,Y
            17.jsnp,40.00,4.04,208.00,Y
            18.jsnp,28.00,3.98,286.62,Y
            19.jsnp,13.00,3.30,0.00,Y
            20.jsnp,28.00,4.24,216.33,Y
            """;
        Path tempFile = Files.createTempFile("testData", ".csv");
        File csvFile = tempFile.toFile();
        Files.writeString(csvFile.toPath(), csvContent);

        SubcommandClassify cmd = new SubcommandClassify();
        CommandLine commandLine = new CommandLine(cmd);
        commandLine.parseArgs("-d", csvFile.getAbsolutePath());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result = cmd.call();
        String output = outContent.toString();

        System.setOut(System.out);

        assertEquals(0, result, "Should return success code");

        assertTrue(output.contains("Accuracy"));
        assertTrue(output.contains("Area Under ROC"));
        assertTrue(output.contains("F-Score"));

    }


}