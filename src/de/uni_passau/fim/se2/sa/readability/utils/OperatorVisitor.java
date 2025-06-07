package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.Map;

public class OperatorVisitor extends VoidVisitorAdapter<Void> {

    public enum OperatorType {
        ASSIGNMENT,         // x=y
        BINARY,             // x+y
        UNARY,              // -x, ++x
        CONDITIONAL,        // ?
        TYPE_COMPARISON,    // instanceof
    }

    /**
     * Maps operator types to the number of their occurrences in the given code snippet.
     */
    private final Map<OperatorType, Integer> operatorsPerMethod;

    public OperatorVisitor() {
        operatorsPerMethod = new HashMap<>();
        for (OperatorType type : OperatorType.values()) {
            operatorsPerMethod.put(type, 0);
        }
    }

    public Map<OperatorType, Integer> getOperatorsPerMethod() {
        Map<OperatorType, Integer> result = new HashMap<>();
        for (Map.Entry<OperatorType, Integer> entry : operatorsPerMethod.entrySet()) {
            if (entry.getValue() > 0) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
    @Override
    public void visit(AssignExpr n, Void arg) {
        OperatorType type = OperatorType.ASSIGNMENT;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }

    @Override
    public void visit(BinaryExpr n, Void arg) {
        OperatorType type = OperatorType.BINARY;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        OperatorType type = OperatorType.ASSIGNMENT;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }
    @Override
    public void visit(UnaryExpr n, Void arg) {
        OperatorType type = OperatorType.UNARY;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }
    @Override
    public void visit(ConditionalExpr n, Void arg) {
        OperatorType type = OperatorType.CONDITIONAL;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }

    @Override
    public void visit(InstanceOfExpr n, Void arg) {
        OperatorType type = OperatorType.TYPE_COMPARISON;
        operatorsPerMethod.put(type, operatorsPerMethod.get(type) + 1);
        super.visit(n, arg);
    }

}
