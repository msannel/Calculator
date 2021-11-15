package com.company;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @Test
    void calculate_Minus() throws Exception {
        assertEquals(2, new Calculator().calculate("4-2"), 0.000000001);
    }

    @Test
    void calculate_UnaryMinusAllSituation() throws Exception {
        assertEquals(2, new Calculator().calculate("-(-2)"), 0.000000001);
    }

    @Test
    void calculate_Priority() throws Exception {
        assertEquals(4.5, new Calculator().calculate("4-2+2*2-3/(-70-9*4*(2-4))"), 0.000000001);
    }

    @Test
    void calculate_Plus() throws Exception {
        assertEquals(12, new Calculator().calculate("2+4+6"), 0.000000001);
    }

    @Test
    void calculate_PlusWithUnaryPlus() throws Exception {
        assertEquals(8, new Calculator().calculate("2+(+6)"), 0.000000001);
    }

    @Test
    void calculate_Multiply() throws Exception {
        assertEquals(60, new Calculator().calculate("2*3*10"), 0.01);
    }

    @Test
    void calculate_MultiplyByZero() throws Exception {
        assertEquals(0, new Calculator().calculate("0*3*9"), 0.01);
    }

    @Test
    void calculate_Division() throws Exception {
        assertEquals(0.666, new Calculator().calculate("2/3"), 0.01);
    }

    @Test
    void calculate_DivisionByZero() {

        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("1/0");
        });

        String expectedMessage = "Cannot divide by zero";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculate_MoreThanOneDot() {
        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("2+2.1..3-1");
        });

        String expectedMessage = "More than 1 dot in one number";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculate_OpeningBracketMissing() {
        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("3-(2+23");
        });

        String expectedMessage = "The ')' is missing";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculate_ClosedBracketMissing() {
        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("3-2)+23");
        });

        String expectedMessage = "The '(' is missing";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculate_MoreThanOneOperatorInRow() {
        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("3-2-+23");
        });

        String expectedMessage = "More than one operator in row";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculate_OperatorMissing() {
        Exception ex = assertThrows(Exception.class, () -> {
            new Calculator().calculate("3(2+23)");
        });

        String expectedMessage = "The operator is missing";
        String actualMessage = ex.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
