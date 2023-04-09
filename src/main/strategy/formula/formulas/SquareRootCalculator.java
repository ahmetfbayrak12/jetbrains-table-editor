package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class SquareRootCalculator implements FormulaCalculatorStrategy {
    @Override
    public void parse(String formula) {
        System.out.println("SQUARE ROOT CALCULATOR");
    }
}
