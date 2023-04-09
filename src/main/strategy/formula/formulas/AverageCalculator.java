package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class AverageCalculator implements FormulaCalculatorStrategy {
    @Override
    public void parse(String formula) {
        System.out.println("AVERAGE CALCULATOR");
    }
}
