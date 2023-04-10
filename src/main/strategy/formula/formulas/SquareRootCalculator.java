package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class SquareRootCalculator implements FormulaCalculatorStrategy {
    @Override
    public double calculate(double... args) {
        return Math.sqrt(args[0]);
    }
}
