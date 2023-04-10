package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class SumCalculator implements FormulaCalculatorStrategy {
    @Override
    public double calculate(double... args) {
        return args[0] + args[1];
    }
}
