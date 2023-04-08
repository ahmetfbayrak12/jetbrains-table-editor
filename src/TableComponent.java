import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.*;

public class TableComponent extends JTable implements IObserver, MouseListener, KeyListener {
    public TableComponent(TableModel model, DefaultTableCellRenderer renderer) {
        super(model);
        this.setFillsViewportHeight(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // pass to the Object.class since I want to apply all cells
        // but why it does not work when I pass CellModel.class?
        //table.setDefaultRenderer(CellModel.class, renderer);
        //table.setDefaultEditor(CellModel.class, editor);
        this.setDefaultRenderer(Object.class, renderer);
        addMouseListener(this);
        addKeyListener(this);
    }

    @Override
    public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
        System.out.println("TABLE UPDATE PARAMETERS");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Publisher.getInstance().notifyObservers(getSelectedRow(), getSelectedColumn(), this);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
