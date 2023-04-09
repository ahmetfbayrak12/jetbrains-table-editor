package main.strategy.formula.formulas;

import main.strategy.formula.FormulaCalculatorStrategy;

public class PowerOfCalculator implements FormulaCalculatorStrategy {
    @Override
    public void parse(String formula) {
        System.out.println("POWER OF FORMULA");
    }
}
