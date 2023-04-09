package main.model;

public class CellModel {
    private String uniqueKey;
    private int rowIndex;
    private int columnIndex;
    private Double shownValue;
    private String formula;
    private String columnName;

    private CellModel() {
        // private constructor for builder pattern
    }

    public static class Builder {
        private Double shownValue = null;
        private String formula = "";
        private int rowIndex;
        private int columnIndex;
        private String uniqueKey;
        private String columnName;

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

        public Builder setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public CellModel build() {
            if (uniqueKey == null) {
                uniqueKey = rowIndex + "" + columnIndex;
            }
            CellModel cellModel = new CellModel();
            cellModel.setFormula(this.formula);
            cellModel.setShownValue(this.shownValue);
            cellModel.setColumnIndex(this.columnIndex);
            cellModel.setRowIndex(this.rowIndex);
            cellModel.setUniqueKey(this.uniqueKey);
            cellModel.setColumnName(this.columnName);
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

    private void setColumnName(String columnName) {
        this.columnName = columnName;
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

    public String getColumnName() {
        return columnName;
    }
}
