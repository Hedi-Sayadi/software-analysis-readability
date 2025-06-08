package de.uni_passau.fim.se2.sa.readability.subcommands;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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




}