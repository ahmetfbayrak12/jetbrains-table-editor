package main.component.frame;

import main.Constant.Constants;
import main.component.textfield.FormulaBar;
import main.component.table.CustomEditor;
import main.component.table.CustomTableCellRenderer;
import main.component.table.TableComponent;
import main.model.CellModel;
import main.model.TableModel;
import main.observer.Publisher;

import javax.swing.*;
import java.awt.*;

public class FrameMain extends JFrame {

    public FrameMain() {
        // Create the main window
        super("JetBrains Table Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        // create observable
        Publisher pub = Publisher.getInstance();

        // create columns
        String[] columnNames = new String[]{"A", "B", "C"};

        // create cells
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int columnIndex = 0; columnIndex < 3; columnIndex++) {
                CellModel newCell = new CellModel.Builder().setColumnIndex(columnIndex).setRowIndex(rowIndex).build();
                pub.getCells().put(rowIndex + "" + columnIndex, newCell);
                pub.addObserver(newCell);
            }
        }

        // Create table JetBrainsTableEditor.model to define data structure and content for the table
        TableModel tableModel = new TableModel(columnNames);

        // Create cell renderer to customize the appearance (change font, color and other visual attributes)
        // of the cells based on their data type or other criteria
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();

        // Create editor
        CustomEditor editor = new CustomEditor();

        // Create table
        TableComponent table = new TableComponent(tableModel, renderer);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create the formula bar
        FormulaBar formulaBar = new FormulaBar();
        formulaBar.putClientProperty(Constants.TABLE_COMPONENT_KEYWORD, table);
        formulaBar.putClientProperty(Constants.TABLE_MODEL_KEYWORD, tableModel);
        add(formulaBar, BorderLayout.NORTH);
        pub.addObserver(formulaBar);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Show the main window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
