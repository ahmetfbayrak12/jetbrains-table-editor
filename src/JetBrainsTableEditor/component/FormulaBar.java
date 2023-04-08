package JetBrainsTableEditor.component;

import JetBrainsTableEditor.IObserver;
import JetBrainsTableEditor.Publisher;

import javax.swing.*;
import java.awt.event.*;

public class FormulaBar extends JTextField implements IObserver, KeyListener, ActionListener {
    private int selectedRow = -1;
    private int selectedColumn = -1;

    public FormulaBar() {
        addKeyListener(this);
        addActionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (selectedRow >= 0 && selectedColumn >= 0) {
                StringBuilder currentTextFieldValue = new StringBuilder(getText());
                if (e.getKeyChar() == 8 && currentTextFieldValue.length() != 0) {
                    char lastChar = currentTextFieldValue.charAt(currentTextFieldValue.length() - 1);
                    if (lastChar > 20) {
                        currentTextFieldValue.deleteCharAt(currentTextFieldValue.length() - 1);
                    }
                } else if (e.getKeyChar() > 20) {
                    currentTextFieldValue.append(e.getKeyChar());
                }
                try {
                    Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setShownValue(Double.parseDouble(currentTextFieldValue.toString()));
                    Publisher.getInstance().notifyObservers(selectedRow, selectedColumn, this);
                } catch (NumberFormatException ex) {
                    Publisher.getInstance().getCells().get(selectedRow + "" + selectedColumn).setShownValue(null);
                    Publisher.getInstance().notifyObservers(selectedRow, selectedColumn, this);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private Double evaluateFormula(String formula) {
        // Parse the formula and evaluate it
        // Use the Shunting Yard algorithm or a similar algorithm to
        // convert infix notation to postfix notation
        // Update the variables map with any variable values in the formula
        // Evaluate the postfix notation using a stack
        // Return the result as a Double
        return 5.0;
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
        if (!(sourceComponent instanceof FormulaBar)) {
            selectedRow = rowIndex;
            selectedColumn = columnIndex;

            if (rowIndex >= 0 && columnIndex >= 0) {
                try {
                    setText(Publisher.getInstance().getCells().get(rowIndex + "" + columnIndex).getShownValue().toString());
                } catch (NullPointerException ex) {
                    setText("");
                }
            }
        }
    }
}
