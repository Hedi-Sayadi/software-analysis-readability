package de.uni_passau.fim.se2.sa.readability.features;

import com.github.javaparser.*;
import com.github.javaparser.ast.body.BodyDeclaration;
import de.uni_passau.fim.se2.sa.readability.utils.Parser;

import java.util.*;

public class TokenEntropyFeature extends FeatureMetric {

    /**
     * Computes the entropy metric based on the tokens of the given code snippet.
     * Since we are interested in the readability of code as perceived by a human, tokens also include whitespaces and the like.
     *
     * @return token entropy of the given code snippet.
     */
    @Override
    public double computeMetric(String codeSnippet) {
        BodyDeclaration<?> parsedCodeSnippet;
        try {
            parsedCodeSnippet = Parser.parseJavaSnippet(codeSnippet);
        } catch (ParseException e) {
            return 0.0;
        }
        Optional<TokenRange> tokenRange = parsedCodeSnippet.getTokenRange();

        Map<String, Integer> tokenCounts = new HashMap<>();
        int totalTokens = countTokens(tokenRange.get(), tokenCounts);

        double entropy = calculateEntropy(tokenCounts, totalTokens);

        return entropy;
    }

    public static int countTokens(TokenRange tokenRange, Map<String, Integer> tokenCounts) {
        int totalTokens = 0;
        for (JavaToken token : tokenRange) {
            String text = token.getText();
            if (tokenCounts.containsKey(text)) {
                int currentCount = tokenCounts.get(text);
                tokenCounts.put(text, currentCount + 1);
            } else {
                tokenCounts.put(text, 1);
            }
            totalTokens++;
        }
        return totalTokens;
    }

    public static double calculateEntropy(Map<String, Integer> tokenCounts, int totalTokens) {
        double entropy = 0.0;
        for (int count : tokenCounts.values()) {
            double probability = (double) count / totalTokens;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        return entropy;
    }


    @Override
    public String getIdentifier() {
        return "TokenEntropy";
    }
}
