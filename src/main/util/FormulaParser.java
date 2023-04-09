package main.util;

import main.constant.ErrorMessageConstants;
import main.model.CellModel;
import main.observer.Publisher;

import javax.swing.*;
import java.util.*;

import static main.constant.ErrorMessageConstants.DIVISION_BY_ZERO_ERROR_MESSAGE;

public class FormulaParser {

    public Double parse(String formula, int selectedRow, int selectedColumn) {
        List<String> tokens = tokenize(formula);
        System.out.println(tokens);
        return evaluate(tokens, selectedRow, selectedColumn);
    }

    private List<String> tokenize(String formula) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(c);
            } else {
                if (sb.length() > 0) {
                    String token = sb.toString();
                    if (token.equalsIgnoreCase("pow")) {
                        tokens.add(token);
                    } else if (isCellReference(token)) {
                        tokens.add(token);
                    } else {
                        tokens.add(token.toLowerCase());
                    }
                    sb.setLength(0);
                }
                if (Character.isWhitespace(c)) {
                    continue;
                } else if (Character.isDigit(c) || c == '.') {
                    sb.append(c);
                    while (i + 1 < formula.length() && (Character.isDigit(formula.charAt(i + 1)) || formula.charAt(i + 1) == '.')) {
                        sb.append(formula.charAt(i + 1));
                        i++;
                    }
                    tokens.add(sb.toString());
                    sb.setLength(0);
                } else {
                    tokens.add(String.valueOf(c));
                }
            }
        }
        if (sb.length() > 0) {
            String token = sb.toString();
            if (token.equalsIgnoreCase("pow")) {
                tokens.add(token);
            } else if (isCellReference(token)) {
                tokens.add(token);
            } else {
                tokens.add(token.toLowerCase());
            }
        }
        return tokens;
    }

    // Evaluates an Excel formula represented as a list of tokens
    private Double evaluate(List<String> tokens, int selectedRow, int selectedColumn) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        boolean cellReferenceStarted = false;
        StringBuilder cellReferenceValue = new StringBuilder();

        for (String token : tokens) {
            if (isNumeric(token)) {
                if (cellReferenceStarted) {
                    cellReferenceValue.append(token);
                } else {
                    values.push(Double.parseDouble(token));
                }
            } else {
                if (cellReferenceStarted) {
                    CellModel referenceCell = Publisher.getInstance().getCells().values().stream()
                            .filter(cell -> (cell.getColumnName() + (cell.getRowIndex() + 1)).equalsIgnoreCase(cellReferenceValue.toString()))
                            .findFirst().orElse(null);
                    try {
                        values.push((referenceCell != null && referenceCell.getShownValue() != null) ? referenceCell.getShownValue() : 0d);
                        Set<String> currentSubsribers = Publisher.getInstance().getSubscribers().getOrDefault(referenceCell.getUniqueKey(), new HashSet<>());
                        currentSubsribers.add(selectedRow + "" + selectedColumn);
                        Publisher.getInstance().getSubscribers().put(referenceCell.getUniqueKey(), currentSubsribers);
                        cellReferenceStarted = false;
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, ErrorMessageConstants.INVALID_REFERENCE_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                }
                if (Character.isLetter(token.charAt(0))) {
                    cellReferenceValue.append(token);
                    cellReferenceStarted = true;
                } else if (token.equals("(")) {
                    operators.push('(');
                } else if (token.equals(")")) {
                    while (operators.peek() != '(') {
                        double result = applyOperator(operators.pop(), values.pop(), values.pop());
                        values.push(result);
                    }
                    operators.pop();
                } else if (isOperator(token)) {
                    while (!operators.isEmpty() && isOperator(String.valueOf(operators.peek())) && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                        double result = applyOperator(operators.pop(), values.pop(), values.pop());
                        values.push(result);
                    }
                    operators.push(token.charAt(0));
                } else if (token.matches("[a-zA-Z]+\\(([0-9]+,)*[0-9]+\\)")) {
                    String[] args = token.substring(token.indexOf("(") + 1, token.indexOf(")")).split(",");
                    double[] argValues = new double[args.length];
                    for (int i = 0; i < args.length; i++) {
                        argValues[i] = Double.parseDouble(args[i]);
                    }
                    System.out.println(token);
                    System.out.println(token.substring(0, token.indexOf("(")));
                    System.out.println(argValues);
                /*
                Function function = getFunction(token.substring(0, token.indexOf("(")));
                double result = function.evaluate(argValues);
                values.push(result);
                */

                }
            }

        }

        while (!operators.isEmpty()) {
            try {
                double result = applyOperator(operators.pop(), values.pop(), values.pop());
                values.push(result);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return values.pop();
    }

    // Applies an operator to two operands
    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new IllegalArgumentException(DIVISION_BY_ZERO_ERROR_MESSAGE);
                }
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    // Returns the precedence of an operator
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String str) {
        switch (str) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                return true;
            default:
                return false;
        }
    }

    private boolean isCellReference(String str) {
        if (str.length() < 2 || str.length() > 3) {
            return false;
        }
        char col = Character.toUpperCase(str.charAt(0));
        String row = str.substring(1);
        return col >= 'A' && col <= 'Z' && row.matches("\\d+");
    }
}

