package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperandVisitorTest {

    @Test
    void testVisitDoubleLiteralExpr () throws ParseException {
        String code = """
    class TestClass {
        void method() {
            double d = 3.14;
            double e = 1.23;
        }
    }
    """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);
        OperandVisitor visitor = new OperandVisitor();
        parsedCode.accept(visitor, null);

        Map<String, Integer> operands = visitor.getOperandsPerMethod();
        assertEquals(1, operands.get("3.14"));
        assertEquals(1, operands.get("1.23"));
    }



    @Test
    void testVisitBooleanLiteralExpr() throws ParseException {
        String code = """
    class TestClass {
        void method() {
            boolean flag = true;
            boolean noFlag = false;
        }
    }
    """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);
        OperandVisitor visitor = new OperandVisitor();
        parsedCode.accept(visitor, null);

        Map<String, Integer> operands = visitor.getOperandsPerMethod();
        assertEquals(1, operands.get("true"));
        assertEquals(1, operands.get("false"));
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
    void testVisitCharLiteralExpr() throws ParseException {
        String code = """
        class TestClass {
            void method() {
                char c1 = 'x';
                char c2 = 'y';
            }
        }
        """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);
        OperandVisitor visitor = new OperandVisitor();
        parsedCode.accept(visitor, null);

        Map<String, Integer> operands = visitor.getOperandsPerMethod();
        assertEquals(1, operands.get("x"));
        assertEquals(1, operands.get("y"));
    }

// add this comment just to see why i have Everything up-to-date when i push



}