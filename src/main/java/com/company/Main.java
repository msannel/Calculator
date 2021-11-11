package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String s = "";
        boolean boo = true;
        Scanner scan = new Scanner(System.in);
        while (boo) {

            System.out.println("Напишите математическое выражение (для завершения работы напишите 0): ");
            s = scan.nextLine();
            if (s.equals("0"))
                boo = false;
            else
                System.out.println(s + " = " + new Calculator().calculate(s) + "\n");
        }
        System.out.println("До свидания!");
    }
}
