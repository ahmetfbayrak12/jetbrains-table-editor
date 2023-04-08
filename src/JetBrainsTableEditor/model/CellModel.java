package JetBrainsTableEditor.model;

import JetBrainsTableEditor.observer.IObserver;

public class CellModel implements IObserver {
    private String uniqueKey;
    private int rowIndex;
    private int columnIndex;
    private Double shownValue;
    private String formula;

    private CellModel() {
        // private constructor for builder pattern
    }

    public static class Builder {
        private Double shownValue = null;
        private String formula = "";
        private int rowIndex;
        private int columnIndex;
        private String uniqueKey = rowIndex + "" + columnIndex;

        public Builder setUniqueKey(String uniqueKey) {
            this.uniqueKey = uniqueKey;
            return this;
        }

        public Builder setShownValue(Double value) {
            this.shownValue = value;
            return this;
        }

        public Builder setFormula(String formula) {
            this.formula = formula;
            return this;
        }

        public Builder setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
            return this;
        }

        public Builder setColumnIndex(int columnIndex) {
            this.columnIndex = columnIndex;
            return this;
        }

        public CellModel build() {
            CellModel cellModel = new CellModel();
            cellModel.setFormula(this.formula);
            cellModel.setShownValue(this.shownValue);
            cellModel.setColumnIndex(this.columnIndex);
            cellModel.setRowIndex(this.rowIndex);
            cellModel.setUniqueKey(this.uniqueKey);
            return cellModel;
        }
    }

    public void setShownValue(Double shownValue) {
        this.shownValue = shownValue;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    private void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    private void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    private void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Double getShownValue() {
        return shownValue;
    }

    public String getFormula() {
        return formula;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {

    }
}
