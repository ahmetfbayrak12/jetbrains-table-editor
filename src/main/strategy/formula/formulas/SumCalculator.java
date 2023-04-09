package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class SumCalculator implements FormulaCalculatorStrategy {
    @Override
    public void parse(String formula) {
        System.out.println("SUM FORMULA STRATEGY");
    }
}
