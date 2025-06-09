package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ast.expr.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperandVisitorTest {
    @Test
    void testVisitDoubleLiteralExpr() {
        OperandVisitor visitor = new OperandVisitor();
        DoubleLiteralExpr doubleExpr = new DoubleLiteralExpr("3.14");

        visitor.visit(doubleExpr, null);

        assertTrue(visitor.getOperandsPerMethod().containsKey("3.14"));
        assertEquals(1, visitor.getOperandsPerMethod().get("3.14"));
    }

    @Test
    void testVisitLongLiteralExpr() {
        OperandVisitor visitor = new OperandVisitor();
        LongLiteralExpr longExpr = new LongLiteralExpr("123456789L");

        visitor.visit(longExpr, null);

        assertTrue(visitor.getOperandsPerMethod().containsKey("123456789L"));
        assertEquals(1, visitor.getOperandsPerMethod().get("123456789L"));
    }

    @Test
    void testVisitStringLiteralExprTrue() {
        OperandVisitor visitor = new OperandVisitor();
        StringLiteralExpr expr = new StringLiteralExpr("TRUE");

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("true"));
    }

    @Test
    void testVisitStringLiteralExprFalse() {
        OperandVisitor visitor = new OperandVisitor();
        StringLiteralExpr expr = new StringLiteralExpr("false");

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("false"));
    }

    @Test
    void testVisitStringLiteralExprNull() {
        OperandVisitor visitor = new OperandVisitor();
        StringLiteralExpr expr = new StringLiteralExpr("NULL");

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("null"));
    }

    @Test
    void testVisitStringLiteralExprOther() {
        OperandVisitor visitor = new OperandVisitor();
        StringLiteralExpr expr = new StringLiteralExpr("Hedi");

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("Hedi"));
    }

    @Test
    void testVisitNullLiteralExpr() {
        OperandVisitor visitor = new OperandVisitor();
        NullLiteralExpr expr = new NullLiteralExpr();

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("null"));
    }

    @Test
    void testVisitBooleanLiteralTrue() {
        OperandVisitor visitor = new OperandVisitor();
        BooleanLiteralExpr expr = new BooleanLiteralExpr(true);

        visitor.visit(expr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("true"));
    }

    @Test
    void testVisitCharLiteralExpr() {
        OperandVisitor visitor = new OperandVisitor();
        CharLiteralExpr charExpr = new CharLiteralExpr('a');

        visitor.visit(charExpr, null);

        Map<String, Integer> result = visitor.getOperandsPerMethod();
        assertEquals(1, result.get("a"));
    }



}