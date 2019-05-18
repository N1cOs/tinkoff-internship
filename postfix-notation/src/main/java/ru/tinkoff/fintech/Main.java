package ru.tinkoff.fintech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        PostfixNotation postfixNotation = new PostfixNotation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    System.out.println("Введите выражение, которое нужно вычислить");
                    String inputString = reader.readLine();
                    inputString = inputString.replace('÷', '/');
                    inputString = inputString.replace('×', '*');
                    inputString = inputString.replace('−', '-');
                    Integer result = postfixNotation.calculate(inputString.split("\\s"));
                    System.out.println("Результат: " + result);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
