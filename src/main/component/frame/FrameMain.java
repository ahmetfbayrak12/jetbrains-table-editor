package main.component.frame;

import main.component.table.CustomTableCellRenderer;
import main.component.table.TableComponent;
import main.component.textfield.FormulaBar;
import main.constant.InformationMessageConstants;
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
        for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            for (int rowIndex = 0; rowIndex < columnNames.length; rowIndex++) {
                CellModel newCell = new CellModel.Builder().setColumnIndex(columnIndex).setRowIndex(rowIndex).setColumnName(columnNames[columnIndex]).build();
                pub.getCells().put(rowIndex + "" + columnIndex, newCell);
            }
        }

        // Create table model to define data structure and content for the table
        TableModel tableModel = new TableModel(columnNames);

        // Create cell renderer to customize the appearance (change font, color and other visual
        // attributes) of the cells based on their data type or other criteria
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();

        // Create table
        TableComponent table = new TableComponent(tableModel, renderer);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create the formula bar and label
        JLabel label = new JLabel("Formula Bar:");
        FormulaBar formulaBar = new FormulaBar();
        pub.addObserver(formulaBar);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(formulaBar, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Help");
        JMenuItem infoItem = new JMenuItem("About");
        fileMenu.add(infoItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        infoItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, InformationMessageConstants.INFO_USAGE_FORMULA_MESSAGE);
        });

        // Show the main window
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
