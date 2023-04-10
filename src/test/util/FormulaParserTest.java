/*package test.util;

import main.model.CellModel;
import main.observer.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FormulaParserTest {

    @Mock
    private Publisher publisher;

    @Before
    public void setUp() {
        Map<String, CellModel> cells = new HashMap<>();
        String[] columnNames = new String[]{"A", "B", "C"};

        for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            for (int rowIndex = 0; rowIndex < columnNames.length; rowIndex++) {
                CellModel newCell = new CellModel.Builder().setColumnIndex(columnIndex).setRowIndex(rowIndex).setColumnName(columnNames[columnIndex]).build();
                cells.put(rowIndex + "" + columnIndex, newCell);
            }
        }

        when(publisher.getCells()).thenReturn(cells);
    }

    // I was going to add unit tests but I could not find time
}*/