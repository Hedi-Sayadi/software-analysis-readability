package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.Map;

public class OperandVisitor extends VoidVisitorAdapter<Void> {

    /**
     * Maps operand names to the number of their occurrences in the given code snippet.
     */
    private final Map<String, Integer> operandsPerMethod;

    public OperandVisitor() {
        operandsPerMethod = new HashMap<>();
    }

    public Map<String, Integer> getOperandsPerMethod() {
        return operandsPerMethod;
    }

    private void increment(String operand) {
        if (operandsPerMethod.containsKey(operand)) {
            operandsPerMethod.put(operand, operandsPerMethod.get(operand) + 1);
        } else {
            operandsPerMethod.put(operand, 1);
        }
    }

    @Override
    public void visit(NameExpr n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(BooleanLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(String.valueOf(n.getValue()));
        super.visit(n, arg);
    }

    @Override
    public void visit(CharLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(n.getValue());
        super.visit(n, arg);
    }

    @Override
    public void visit(DoubleLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(n.getValue());
        super.visit(n, arg);
    }

    @Override
    public void visit(IntegerLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(n.getValue());
        super.visit(n, arg);
    }

    @Override
    public void visit(LongLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(n.getValue());
        super.visit(n, arg);
    }

    @Override
    public void visit(StringLiteralExpr n, Void arg) {
        System.out.println(n.getValue());
        increment(n.getValue());
        super.visit(n, arg);
    }

    @Override
    public void visit(NullLiteralExpr n, Void arg) {
        System.out.println("null");
        increment("null");
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }

//println
    @Override
    public void visit(MethodCallExpr n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }
    //variable names
    @Override
    public void visit(VariableDeclarator n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceType n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }

    // out
    @Override
    public void visit(FieldAccessExpr n, Void arg) {
        System.out.println(n.getNameAsString());
        increment(n.getNameAsString());
        super.visit(n, arg);
    }

}
