package main.strategy.formula;

/*
 * Strategy pattern for different formulas
 *
 * @author ahmetfbayrak
 *
 * */
public class FormulaCalculator {
    private FormulaCalculatorStrategy formulaCalculatorStrategy;

    public void setFormulaParserStrategy(FormulaCalculatorStrategy formulaCalculatorStrategy) {
        this.formulaCalculatorStrategy = formulaCalculatorStrategy;
    }

    public double calculate(double... args) {
        return formulaCalculatorStrategy.calculate(args);
    }
}
