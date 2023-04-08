package test.model;

import main.model.CellModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellModelTest {

    @Test
    public void testBuilder() {
        CellModel cellModel = new CellModel.Builder()
                .setRowIndex(1)
                .setColumnIndex(2)
                .setShownValue(1.9)
                .setFormula("A1 + 2")
                .build();
        assertEquals(1, cellModel.getRowIndex());
        assertEquals(2, cellModel.getColumnIndex());
        assertEquals(1.9, cellModel.getShownValue(), 0.0);
        assertEquals("A1 + 2", cellModel.getFormula());
        assertEquals("12", cellModel.getUniqueKey());
    }
}
