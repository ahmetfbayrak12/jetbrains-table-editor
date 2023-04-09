package main.component.table;

import main.model.CellModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CustomEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel;
    private final JTextField textField;
    private CellModel model;

    public CustomEditor()
    {
        panel = new JPanel(new BorderLayout());
        textField = new JTextField();

        panel.add(textField, BorderLayout.CENTER);

        textField.addActionListener(e -> {
            System.out.println(textField.getText());
            fireEditingStopped();
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.model = (CellModel) value;
        textField.setText(this.model.getShownValue().toString());

        table.getModel().setValueAt(this.model.getShownValue().toString(), row, column);

        return panel;
     }

     @Override
     public Object getCellEditorValue() {
        return model.getShownValue();
     }
}
