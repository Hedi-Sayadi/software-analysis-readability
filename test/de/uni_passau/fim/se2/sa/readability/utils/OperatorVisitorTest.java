package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ast.body.BodyDeclaration;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperatorVisitorTest {
    @Test
    public void testOperatorCounting() throws Exception {
        String snippet = """
            void testMethod() {
                int a = 5;
                int b = 10;
                int c = a + b;
                boolean d = a > b ? true : false;
                if (a instanceof Integer) {
                    a++;
                }
            }
            """;
        BodyDeclaration<?> parsedSnippet = Parser.parseJavaSnippet(snippet);
        OperatorVisitor visitor = new OperatorVisitor();
        parsedSnippet.accept(visitor, null);

        Map<OperatorVisitor.OperatorType, Integer> operatorCounts = visitor.getOperatorsPerMethod();

        assertEquals(4, operatorCounts.get(OperatorVisitor.OperatorType.ASSIGNMENT));
        assertEquals(2, operatorCounts.get(OperatorVisitor.OperatorType.BINARY));
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.CONDITIONAL));
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.TYPE_COMPARISON));
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.UNARY));
    }

//to kill the mutation shown in mutation report
    @Test
    public void testNestedBinaryExpressions() throws Exception {
        String snippet = """
        void testMethod() {
            int result1 = (a + b) * (c - d);           // 3: +, -, *
            int result2 = a + b * c + d;               // 3 : *, +, +
            boolean complex = (x > y) && (z < w);      // 3 : >, <, &&
        }
        """;

        BodyDeclaration<?> parsedSnippet = Parser.parseJavaSnippet(snippet);
        OperatorVisitor visitor = new OperatorVisitor();
        parsedSnippet.accept(visitor, null);
        Map<OperatorVisitor.OperatorType, Integer> operatorCounts = visitor.getOperatorsPerMethod();
        assertEquals(9, operatorCounts.get(OperatorVisitor.OperatorType.BINARY));
    }


    @Test
    public void testOnlyPositiveCountsExist() throws Exception {
        String snippet = """
            void testMethod() {
                int a = 5;
                int b = 10;
                int c = a + b;
                boolean d = a > b ? true : false;
                if (a instanceof Integer) {
                    a++;
                }
            }
            """;
        BodyDeclaration<?> parsedSnippet = Parser.parseJavaSnippet(snippet);
        OperatorVisitor visitor = new OperatorVisitor();
        parsedSnippet.accept(visitor, null);

        Map<OperatorVisitor.OperatorType, Integer> operatorCounts = visitor.getOperatorsPerMethod();

        for (Map.Entry<OperatorVisitor.OperatorType, Integer> entry : operatorCounts.entrySet()) {
            assertTrue(entry.getValue() > 0, "Operator with zero count should not be included: " + entry.getKey());
        }
    }




}

