package de.uni_passau.fim.se2.sa.readability.subcommands;

import de.uni_passau.fim.se2.sa.readability.ReadabilityAnalysisMain;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubcommandPreprocessTest {
    @Test
    public void testPreprocessCommand() {
        CommandLine cmd = new CommandLine(new ReadabilityAnalysisMain());
        CommandLine.ParseResult result = cmd.parseArgs("preprocess","-s","resources/snippets","-g","resources/truth_scores.csv","-t","resources/data.csv","LINES","TOKEN_ENTROPY","H_VOLUME");
        assertEquals("preprocess", result.subcommand().commandSpec().name());
    }
}
