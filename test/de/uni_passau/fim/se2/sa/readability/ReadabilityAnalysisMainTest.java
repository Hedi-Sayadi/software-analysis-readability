package de.uni_passau.fim.se2.sa.readability;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadabilityAnalysisMainTest {

    @Test
    void testCallMethod() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));
        ReadabilityAnalysisMain main = new ReadabilityAnalysisMain();
        Integer result = main.call();

        assertEquals(0, result);
        assertTrue(out.toString().contains("Subcommand needed: 'preprocess' or 'classify'"));
        System.setOut(originalOut);

    }
}