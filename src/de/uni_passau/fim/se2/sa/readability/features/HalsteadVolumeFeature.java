package de.uni_passau.fim.se2.sa.readability.features;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.BodyDeclaration;
import de.uni_passau.fim.se2.sa.readability.utils.OperandVisitor;
import de.uni_passau.fim.se2.sa.readability.utils.OperatorVisitor;
import de.uni_passau.fim.se2.sa.readability.utils.Parser;

import java.util.Map;

public class HalsteadVolumeFeature extends FeatureMetric {

    /**
     * Computes the Halstead Volume metric based on the given code snippet.
     *
     * @return Halstead Volume of the given code snippet.
     */
    @Override
    public double computeMetric(String codeSnippet) {
        BodyDeclaration<?> parsedCodeSnippet;
        try {
            parsedCodeSnippet = Parser.parseJavaSnippet(codeSnippet);
        } catch (ParseException e) {
            return 0.0;
        }
        if (codeSnippet == null) {
            return 0.0;
        }
        // Count operators
        OperatorVisitor operatorVisitor = new OperatorVisitor();
        parsedCodeSnippet.accept(operatorVisitor, null);
        Map<OperatorVisitor.OperatorType, Integer> operatorCounts = operatorVisitor.getOperatorsPerMethod();

        // Sum total operators (N1) and unique operators (n1)
        int totalOperators = 0;
        for (Integer count : operatorCounts.values()) {
            totalOperators += count;
        }
        int uniqueOperators = operatorCounts.size();

        // Count operands
        OperandVisitor operandVisitor = new OperandVisitor();
        parsedCodeSnippet.accept(operandVisitor, null);
        Map<String, Integer> operandCounts = operandVisitor.getOperandsPerMethod();

        // Sum total operands (N2) and unique operands (n2)
        int totalOperands = 0;
        for (Integer count : operandCounts.values()) {
            totalOperands += count;
        }
        int uniqueOperands = operandCounts.keySet().size();

        int programLength = totalOperators + totalOperands;    // N =    N1 + N2
        int vocabulary = uniqueOperators + uniqueOperands;     // n = n1 + n2

        double volume = programLength * (Math.log(vocabulary) / Math.log(2));

        return volume;
    }

    @Override
    public String getIdentifier() {
        return "HalsteadVolume";
    }
}
