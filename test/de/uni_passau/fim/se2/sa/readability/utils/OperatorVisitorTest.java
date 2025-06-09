package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ast.body.BodyDeclaration;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(4, operatorCounts.get(OperatorVisitor.OperatorType.ASSIGNMENT));      // a=5, b=10
        assertEquals(2, operatorCounts.get(OperatorVisitor.OperatorType.BINARY));          // a + b
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.CONDITIONAL));     // ? :
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.TYPE_COMPARISON)); // instanceof
        assertEquals(1, operatorCounts.get(OperatorVisitor.OperatorType.UNARY));           // a++
    }
    }

