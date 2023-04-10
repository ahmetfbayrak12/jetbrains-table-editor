package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

import static main.constant.ErrorMessageConstants.DIVISION_BY_ZERO_ERROR_MESSAGE;

public class DivideCalculator implements FormulaCalculatorStrategy {
    @Override
    public double calculate(double... args) {
        if (args[1] == 0) {
            throw new IllegalArgumentException(DIVISION_BY_ZERO_ERROR_MESSAGE);
        }
        return args[0] / args[1];
    }
}
