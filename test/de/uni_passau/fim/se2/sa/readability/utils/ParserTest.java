package de.uni_passau.fim.se2.sa.readability.utils;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.BodyDeclaration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void ParseJavaSnippetValidSnippetTest() {
        String validSnippet = "void foo() { int x = 0; }";
        try {
            BodyDeclaration<?> result = Parser.parseJavaSnippet(validSnippet);
            assertNotNull(result);
        } catch (ParseException e) {
            fail("Should parse the valid code");
        }
    }

    @Test
    void ParseJavaSnippetInValidSnippetTest() {
        String invalidSnippet = "void foo( { int x = 0 ";
        assertThrows(ParseException.class, () -> {
            Parser.parseJavaSnippet(invalidSnippet);
        });
    }


}
