package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.BodyDeclaration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CyclomaticComplexityVisitorTest {

    @Test
    public void testSwitchEntryVisitorLabeledAndNonLabeledCases() throws ParseException {
        String code = """
            class Test {
                void testMethod() {
                    switch (1) {
                        case 1 -> System.out.println("One");
                        case 2 -> System.out.println("Two");
                        default -> System.out.println("Default");
                    }
                }
            }
        """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);
        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        parsedCode.accept(visitor, null);
        // switch , case 1 and case 2 and default is not counted
        assertEquals(3, visitor.getComplexity());
    }

    @Test
    public void testBinaryExp() throws ParseException {
        String code = """
            public class testBinaryExp {
                public void testMethod(int x, int y) {
                    if (x > 0 && y > 0 || x == y) {
                        System.out.println("Hedi");
                    }
                }
            }
        """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);
        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        parsedCode.accept(visitor, null);
        assertEquals(4, visitor.getComplexity());
    }

    @Test
    public void testWhileAndDoWhile() throws ParseException {
        String code = """
            public class testWhileAndDoWhile {
                void testMethod() {
                    while (true) {
                        // code
                    }
                    do {
                        // code
                    } while (false);
                }
            }
            """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);

        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        parsedCode.accept(visitor, null);
        System.out.println(visitor.getComplexity());

        assertEquals(3, visitor.getComplexity());
    }


    @Test
    public void testCatchClause() throws ParseException {
        String code = """
            public class testCatchClause {
                void testMethod() {
                    try {
                        int x = 1 / 0;
                    } catch (ArithmeticException e) {
                        System.out.println("ArithmeticException");
                    }
                }
            }
            """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);

        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        parsedCode.accept(visitor, null);
        System.out.println(visitor.getComplexity());

        assertEquals(2, visitor.getComplexity());
    }

    @Test
    public void testForEachStmt() throws ParseException {
        String code = """
            public class Test {
                void example() {
                    for (String s : new String[]{"a", "b", "c"}) {
                        System.out.println(s);
                    }
                }
            }
            """;

        BodyDeclaration<?> parsedCode = Parser.parseJavaSnippet(code);

        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        parsedCode.accept(visitor, null);
        System.out.println(visitor.getComplexity());

        assertEquals(2, visitor.getComplexity());
    }

}