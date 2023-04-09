package main.strategy.formula;

public class FormulaCalculator {
    private FormulaCalculatorStrategy formulaCalculatorStrategy;

    public void setFormulaParserStrategy(FormulaCalculatorStrategy formulaCalculatorStrategy) {
        this.formulaCalculatorStrategy = formulaCalculatorStrategy;
    }

    public void parse(String formula) {
        formulaCalculatorStrategy.parse(formula);
    }
}
