package main.util;

import main.constant.ErrorMessageConstants;
import main.constant.FormulaKeyConstants;
import main.model.CellModel;
import main.observer.Publisher;
import main.strategy.formula.FormulaCalculator;
import main.strategy.formula.formulas.AverageCalculator;
import main.strategy.formula.formulas.DivideCalculator;
import main.strategy.formula.formulas.PowerOfCalculator;
import main.strategy.formula.formulas.SquareRootCalculator;

import javax.swing.*;
import java.util.*;

/*
 * Helper class for parsing and calculating the formula
 *
 * @author ahmetfbayrak
 *
 * */
public class FormulaParser {

    public Double parse(String formula, int selectedRow, int selectedColumn) {
        List<String> tokens = tokenize(formula);
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
                    if (isCellReference(token)) {
                        tokens.add(token);
                    } else {
                        tokens.add(token.toUpperCase());
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
            if (isCellReference(token)) {
                tokens.add(token);
            } else {
                tokens.add(token.toUpperCase());
            }
        }
        return tokens;
    }

    // Evaluates an Excel formula represented as a list of tokens
    private Double evaluate(List<String> tokens, int selectedRow, int selectedColumn) {
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();
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
                        boolean isSelfRef = referenceCell != null && (selectedRow + "" + selectedColumn).equals(referenceCell.getUniqueKey());
                        if (isSelfRef) {
                            JOptionPane.showMessageDialog(null, ErrorMessageConstants.SELF_REFERENCE_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                        //TODO refernececell null geliyo =a1+a2 gibi durumda
                        boolean isLoop = Publisher.getInstance().getSubscribers().getOrDefault(selectedRow + "" + selectedColumn, new HashSet<>()).contains(referenceCell.getUniqueKey());
                        if (isLoop) {
                            JOptionPane.showMessageDialog(null, ErrorMessageConstants.LOOP_REFERENCE_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        } else {
                            values.push(referenceCell.getShownValue() != null ? referenceCell.getShownValue() : 0d);
                            Set<String> currentSubsribers = Publisher.getInstance().getSubscribers().getOrDefault(referenceCell.getUniqueKey(), new HashSet<>());
                            currentSubsribers.add(selectedRow + "" + selectedColumn);
                            Publisher.getInstance().getSubscribers().put(referenceCell.getUniqueKey(), currentSubsribers);
                            cellReferenceStarted = false;
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, ErrorMessageConstants.INVALID_REFERENCE_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                }
                if (token.length() > 1) {
                    if (isOperatorValid(token)) {
                        operators.push(token);
                    } else {
                        JOptionPane.showMessageDialog(null, ErrorMessageConstants.INVALID_OPERATOR_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                } else if (Character.isLetter(token.charAt(0))) {
                    cellReferenceValue.append(token);
                    cellReferenceStarted = true;
                } else if (token.equals("(")) {
                    operators.push("(");
                } else if (token.equals(")")) {
                    while (!operators.peek().equalsIgnoreCase("(")) {
                        double result = applyOperator(operators.pop(), values);
                        values.push(result);
                    }
                    operators.pop();
                } else if (isOperatorValid(token)) {
                    while (!operators.isEmpty() && isOperatorValid(String.valueOf(operators.peek())) && precedence(operators.peek()) >= precedence(token)) {
                        double result = applyOperator(operators.pop(), values);
                        values.push(result);
                    }
                    operators.push(token);
                } else if (token.matches("[a-zA-Z]+\\(([0-9]+,)*[0-9]+\\)")) {
                    String[] args = token.substring(token.indexOf("(") + 1, token.indexOf(")")).split(",");
                    double[] argValues = new double[args.length];
                    for (int i = 0; i < args.length; i++) {
                        argValues[i] = Double.parseDouble(args[i]);
                    }
                }
            }

        }

        while (!operators.isEmpty()) {
            try {
                double result = applyOperator(operators.pop(), values);
                values.push(result);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return values.pop();
    }

    // Applies an operator to two operands
    private static double applyOperator(String operator, Stack<Double> values) {
        FormulaCalculator formulaCalculator = new FormulaCalculator();
        double a = 0, b = 0;
        if (!values.isEmpty()) {
            a = values.pop();
        }
        if (!values.isEmpty()) {
            b = values.pop();
        }
        switch (operator.toUpperCase()) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                formulaCalculator.setFormulaParserStrategy(new DivideCalculator());
                return formulaCalculator.calculate(b, a);
            case "^":
            case FormulaKeyConstants.POW_KEYWORD:
                formulaCalculator.setFormulaParserStrategy(new PowerOfCalculator());
                return formulaCalculator.calculate(b, a);
            case FormulaKeyConstants.SQRT_KEYWORD:
                formulaCalculator.setFormulaParserStrategy(new SquareRootCalculator());
                return formulaCalculator.calculate(a);
            case FormulaKeyConstants.AVERAGE_KEYWORD:
                formulaCalculator.setFormulaParserStrategy(new AverageCalculator());
                return formulaCalculator.calculate(a, b);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    // Returns the precedence of an operator
    private static int precedence(String operator) {
        switch (operator.toUpperCase()) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
            case FormulaKeyConstants.POW_KEYWORD:
            case FormulaKeyConstants.SQRT_KEYWORD:
            case FormulaKeyConstants.AVERAGE_KEYWORD:
                return 3;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperatorValid(String str) {
        switch (str.toUpperCase()) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case FormulaKeyConstants.POW_KEYWORD:
            case FormulaKeyConstants.SQRT_KEYWORD:
            case FormulaKeyConstants.AVERAGE_KEYWORD:
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

