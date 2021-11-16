package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String expression = "";
        boolean boo = true;
        Scanner scan = new Scanner(System.in);
        while (boo) {
            System.out.println("Напишите математическое выражение (для завершения работы напишите 0): ");
            expression = scan.nextLine();
            if (expression.equals("0"))
                boo = false;
            else
                System.out.println(expression + " = " + new Calculator().calculate(expression) + "\n");
        }
        System.out.println("До свидания!");
    }

}
