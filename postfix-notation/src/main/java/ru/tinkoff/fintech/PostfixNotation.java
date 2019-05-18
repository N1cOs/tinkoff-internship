package ru.tinkoff.fintech;

import java.util.EmptyStackException;
import java.util.Stack;

public class PostfixNotation {

    public Integer calculate(String[] expression) {
        Stack<Integer> stack = new Stack<>();
        try {
            for (String s : expression) {
                if (isOperand(s)) {
                    Integer firstOperand = stack.pop();
                    Integer secondOperand = stack.pop();
                    if (s.equals("+")) {
                        stack.push(firstOperand + secondOperand);
                    } else if (s.equals("-")) {
                        stack.push(secondOperand - firstOperand);
                    } else if (s.equals("*")) {
                        stack.push(firstOperand * secondOperand);
                    } else if (firstOperand != 0) {
                        stack.push(secondOperand / firstOperand);
                    } else {
                        throw new IllegalArgumentException("Деление на 0");
                    }
                } else {
                    stack.push(Integer.parseInt(s));
                }
            }
        } catch (EmptyStackException | NumberFormatException e) {
            throw new IllegalArgumentException("Выражение некорректно");
        }

        if (stack.size() == 1) {
            return stack.pop();
        }
        throw new IllegalArgumentException("Выражение некорректно");
    }

    private boolean isOperand(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
}
