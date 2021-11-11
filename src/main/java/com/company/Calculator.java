package com.company;
import java.util.Stack;

/**
 * A calculator class that can calculate the value of expressions
 */
public class Calculator
{
    /**
     * Stores the expression that will need to be calculated
     */
    private String expression;

    Calculator() { expression = ""; }
    Calculator(String str) { expression = str; }

    /**
     * The shell of the main method _calculate, writes the given expression to a variable and calls the main method to count the expression
     * @param _expression Accepts an expression that needs to be calculated
     * @return Returns the calculated value of the expression
     * @throws Exception Exceptions can be raised when the user has entered an expression incorrectly
     */
    public double calculate(String _expression) throws Exception
    {
        expression = _expression;
        return _calculate();
    }

    /**
     * Calculates the expression that is stored in the 'expression' variable
     * @return Returns the calculated value of the expression
     * @throws Exception If the user has entered an incorrect expression, it will throw an exception specifying the error
     */
    private double _calculate() throws Exception {

        if (!checkTwoOperatorsInRow(expression)) throw new Exception("More than one operator in row");

        //if (isOperator(expression.charAt(expression.length() - 1))) throw new Exception("Last symbol is operator");
        // OR

        if (isOperator(expression.charAt(expression.length() - 1))) expression = expression.substring(0, expression.length() - 1);

        //if (!checkToInvalidSymbols(expression)) throw new Exception("Invalid symbol in string");
        // OR

        if (!checkToInvalidSymbols(expression))
        {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < expression.length(); i++)
            {
                if ( !( (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.' || isOperator(expression.charAt(i)) || expression.charAt(i) == '(' || expression.charAt(i) == ')'))
                    continue;
                str.append(expression.charAt(i));
            }

        }

        expression = fixUnaryOperators(expression);
        char[] tokens = expression.toCharArray();
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                boolean dot = false;
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    if (tokens[i] == '.' && !dot) {
                        sbuf.append(tokens[i++]);
                        dot = true;
                    } else if (tokens[i] != '.') sbuf.append(tokens[i++]);
                    else
                        throw new Exception("More than 1 dot in one number");
                }
                numbers.push(Double.parseDouble(sbuf.toString()));
                i--;
            } else if (tokens[i] == '(')
                operators.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                    if (operators.isEmpty()) throw new Exception("The '(' is missing");
                }
                operators.pop();
            } else if (isOperator(tokens[i])) {
                while (!operators.empty() && hasPrecedence(tokens[i], operators.peek()))
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                operators.push(tokens[i]);
            }
        }

        while (!operators.empty()) {
            if (numbers.size() < 2)
                throw new Exception("The ')' is missing");
            numbers.push(applyOperation(operators.pop(),
                    numbers.pop(),
                    numbers.pop()));

        }
        if (numbers.size() > 1) throw new Exception("The operator is missing");
        return numbers.pop();
    }

    /**
     * Determines whether the second operator takes precedence over the first
     * @param op1 The first operator
     * @param op2 The second operator
     * @return Returns the answer to the question whether the second operator takes precedence over the first
     */
    private boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    /**
     * The method determines whether a variable of type char is an operator
     * @param c A variable of type char, which we will check to see if it is an operator
     * @return Returns true or false whether the variable is an operator
     */
    private boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '/' || c == '*';
    }

    /**
     * Before unary - and + puts 0, so that the expression is considered correct
     * @param str A string containing expressions in which to add 0 before unary - and +
     * @return returns the corrected string
     */
    private static String fixUnaryOperators(String str) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (symbol == '-' || symbol == '+') {
                if (i == 0)
                    newStr.append('0');
                else if (str.charAt(i - 1) == '(')
                    newStr.append('0');
            }
            newStr.append(symbol);
        }
        return newStr.toString();
    }

    /**
     * Performs an operation between two numbers
     * @param operator The operator that determines which operation we will perform
     * @param second_element The second element that will be on the right when performing the operation
     * @param first_element The first element that will be on the left when performing the operation
     * @return Returns the result of performing an operation on two numbers
     */
    private double applyOperation(char operator, double second_element, double first_element)
    {
        switch (operator)
        {
            case '+':
                return fixDouble(first_element + second_element);
            case '-':
                return fixDouble(first_element - second_element);
            case '*':
                return fixDouble(first_element * second_element);
            case '/':
                if (second_element == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return fixDouble(first_element / second_element);
        }
        return 0;
    }

    /**
     * Searches for unsuitable characters (not brackets, not operators, not dots and not numbers)
     * @param str The string in which we will search for incorrect characters
     * @return Returns whether it is true or not that there are incorrect characters in the string
     */
    private boolean checkToInvalidSymbols(String str)
    {
        char[] ch = str.toCharArray();
        boolean boo = true;
        for (int i = 0; i < ch.length; i++)
        {
            if ( !( (ch[i] >= '0' && ch[i] <= '9') || ch[i] == '.' || isOperator(ch[i]) || ch[i] == '(' || ch[i] == ')'))
                boo = false;
        }
        return boo;
    }

    /**
     * Searches for more than one operator in a row
     * @param str The variable in which the search takes place
     * @return Returns whether it is true or false that there is more than one operator in a row
     */
    private boolean checkTwoOperatorsInRow(String str)
    {
        char[] ch = str.toCharArray();
        boolean boo = true;
        for (int i = 0; i < ch.length; i++)
        {
            if (isOperator(ch[i]) && isOperator(ch[i+1])) boo = false;
        }
        return boo;
    }

    /**
     * The function corrects arithmetic defects in java, for example 2.0 - 1.1 = 0.89999999999999991 as standard, and after applying this method to the result, it becomes 0.9
     * @param value The variable that we will fix
     * @return Returns the corrected value
     */
    private double fixDouble(double value) {
        var power = Math.pow(10, 14);
        return Math.round(value * power) / power;
    }

    /**
     * Converts an expression to a hash code by concatenating ascii characters
     * @return Returns the calculated hash code
     */
    @Override
    public int hashCode() {
        int prime = 31;
        StringBuilder result = new StringBuilder();
        result.append("0");
        for (int i = 0; i < expression.length(); i++) {
            result.append((int)expression.charAt(i));
        }
        return Integer.parseInt(result.toString());
    }

    /**
     * Converts class information to a string: the 'expression' field and the result of calculating the expression
     * @return Returns the resulting string
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        try {
            str.append("Expression: " + expression + "\nResult of expression: " + calculate(expression));
        } catch (Exception e) {
            str.append("Expression: " + expression + "\nError of calculation of expression!");
        }
        return str.toString();
    }
}
