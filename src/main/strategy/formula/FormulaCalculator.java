package main.strategy.formula;

public class FormulaCalculator {
    private FormulaCalculatorStrategy formulaCalculatorStrategy;

    public void setFormulaParserStrategy(FormulaCalculatorStrategy formulaCalculatorStrategy) {
        this.formulaCalculatorStrategy = formulaCalculatorStrategy;
    }

    public double calculate(double... args) {
        return formulaCalculatorStrategy.calculate(args);
    }
}
