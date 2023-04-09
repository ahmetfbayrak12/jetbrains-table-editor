package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class FourOperationsCalculator implements FormulaCalculatorStrategy {
    @Override
    public void parse(String formula) {
        System.out.println("FOUR OPERATIONS STRATEGY");
    }
}
