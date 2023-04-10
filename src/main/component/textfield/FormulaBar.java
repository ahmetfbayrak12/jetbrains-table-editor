package main.component.textfield;

import main.constant.ErrorMessageConstants;
import main.listener.IDefaultKeyListener;
import main.model.CellModel;
import main.observer.IObserver;
import main.observer.Publisher;
import main.util.FormulaParser;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class FormulaBar extends JTextField implements IObserver, IDefaultKeyListener {
    private int selectedRow = -1;
    private int selectedColumn = -1;

    public FormulaBar() {
        addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedRow >= 0 && selectedColumn >= 0) {
            StringBuilder currentTextFieldValue = new StringBuilder(getText());
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE && !currentTextFieldValue.isEmpty()) {
                currentTextFieldValue.deleteCharAt(currentTextFieldValue.length() - 1);
            } else if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9) {
                currentTextFieldValue.insert(getCaretPosition(), e.getKeyChar());
            } else if (e.getKeyChar() == KeyEvent.VK_ENTER && !currentTextFieldValue.isEmpty()) {
                currentTextFieldValue = new StringBuilder(parseFormula(getText()));
            }
            updateAndNotify(currentTextFieldValue);
            Publisher.getInstance().notifySubsribers(selectedRow, selectedColumn);
        }
    }

    private void updateAndNotify(StringBuilder currentTextFieldValue) {
        try {
            if (currentTextFieldValue.charAt(0) == '=') {
                Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setFormula(currentTextFieldValue.toString());
            } else {
                Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setShownValue(Double.valueOf(currentTextFieldValue.toString()));
            }
            Publisher.getInstance().notifyObservers(selectedRow, selectedColumn, this);
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex ){
            Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setShownValue(null);
            Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setFormula(null);
            Publisher.getInstance().notifyObservers(selectedRow, selectedColumn, this);
        }

    }

    private String parseFormula(String formula) {
        if (formula.charAt(0) == '=') {
            FormulaParser formulaParser = new FormulaParser();
            String calculatedValue = formulaParser.parse(formula, selectedRow, selectedColumn) + "";
            Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setFormula("null".equals(calculatedValue) ? null : formula);
            return calculatedValue;
        } else if (formula.matches(".*[^0-9].*")) {
            JOptionPane.showMessageDialog(null, ErrorMessageConstants.FORMULA_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setFormula(null);
        return formula;
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
        if (!(sourceComponent instanceof FormulaBar)) {
            selectedRow = rowIndex;
            selectedColumn = columnIndex;

            if (rowIndex >= 0 && columnIndex >= 0) {
                try {
                    CellModel updatedCellModel = Publisher.getInstance().getCells().get(rowIndex + "" + columnIndex);
                    boolean doesFormulaExist = updatedCellModel.getFormula() != null && !(updatedCellModel.getFormula().isEmpty());
                    setText(doesFormulaExist ? updatedCellModel.getFormula() : updatedCellModel.getShownValue().toString());
                } catch (NullPointerException ex) {
                    setText("");
                }
            }
        }
    }
}
