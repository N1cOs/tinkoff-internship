import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.tinkoff.fintech.PostfixNotation;

import java.util.regex.Pattern;

public class TestPostfixNotation {

    private static Pattern splitPattern;

    @BeforeAll
    static void compilePattern() {
        splitPattern = Pattern.compile("\\s");
    }

    @Test
    void emptyExpression() {
        PostfixNotation postfixNotation = new PostfixNotation();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> postfixNotation.calculate(new String[]{}));
    }

    @Test
    void allOperators() {
        PostfixNotation postfixNotation = new PostfixNotation();
        String testCase = "+ - * / + - - - - +";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> postfixNotation.calculate(splitPattern.split(testCase)));
    }

    @Test
    void allNumbers() {
        PostfixNotation postfixNotation = new PostfixNotation();
        String testCase = "12 5 -2 3 9 0 1 2";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> postfixNotation.calculate(splitPattern.split(testCase)));
    }

    @Test
    void divisionByZero() {
        PostfixNotation postfixNotation = new PostfixNotation();
        String testCase = "4 0 /";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> postfixNotation.calculate(splitPattern.split(testCase)));
    }

    @Test
    void simpleIntegerCase() {
        PostfixNotation postfixNotation = new PostfixNotation();
        String testCase = "3 4 - 5 +";
        Assertions.assertEquals(4, postfixNotation.calculate(splitPattern.split(testCase)));
    }

    @Test
    void complexIntegerCase() {
        PostfixNotation postfixNotation = new PostfixNotation();
        String testCase = "15 7 1 1 + - / 3 * 2 1 1 + + -";
        Assertions.assertEquals(5, postfixNotation.calculate(splitPattern.split(testCase)));
    }
}
