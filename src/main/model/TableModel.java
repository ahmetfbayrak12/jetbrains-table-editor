package main.model;

import main.constant.ErrorMessageConstants;
import main.observer.IObserver;
import main.observer.Publisher;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel implements IObserver {
    private final String[] columnNames;

    public TableModel(String[] columnNames) {
        this.columnNames = columnNames;
        Publisher.getInstance().addObserver(this);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return Publisher.getInstance().getCells().size() / this.getColumnCount();
    }

    public Object getValueAt(int row, int col) {
        try {
            return Publisher.getInstance().getCells().get(row + "" + col).getShownValue();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        try {
            String input = value.toString();
            Publisher pub = Publisher.getInstance();
            pub.getCells().get(row + "" + col).setShownValue(input.isEmpty() ? null : Double.parseDouble(input));
            pub.getCells().get(row + "" + col).setFormula(null);
            pub.notifySubsribers(row, col);
            pub.notifyObservers(row, col, this);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ErrorMessageConstants.INVALID_INPUT_ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int col) {
        return Object.class;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
