package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class PowerOfCalculator implements FormulaCalculatorStrategy {
    @Override
    public double calculate(double... args) {
        return Math.pow(args[0], args[1]);
    }
}
