package main.component.table;

import main.listener.IDefaultKeyListener;
import main.listener.IDefaultMouseListener;
import main.observer.IObserver;
import main.observer.Publisher;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TableComponent extends JTable implements IObserver, IDefaultMouseListener, IDefaultKeyListener {
    public TableComponent(TableModel model, DefaultTableCellRenderer renderer) {
        super(model);
        this.setFillsViewportHeight(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setDefaultRenderer(Object.class, renderer);
        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int rowIndex = getSelectedRow();
        int columnIndex = getSelectedColumn();
        ((CustomTableCellRenderer) this.getDefaultRenderer(Object.class)).setClickedCell(rowIndex, columnIndex);
        Publisher.getInstance().notifyObservers(rowIndex, columnIndex, TableComponent.this);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Publisher.getInstance().notifyObservers(getSelectedRow(), getSelectedColumn(), this);
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
        System.out.println("TABLE UPDATE PARAMETERS");
    }
}
