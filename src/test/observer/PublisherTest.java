package test.observer;

import main.model.CellModel;
import main.observer.IObserver;
import main.observer.Publisher;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class PublisherTest {
    private Publisher publisher;

    @Before
    public void setup() {
        publisher = Publisher.getInstance();
    }

    @Test
    public void testAddObserver() {
        IObserver observer = new IObserver() {
            @Override
            public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
                // Do nothing
            }
        };
        publisher.addObserver(observer);
        assertTrue(publisher.getObservers().contains(observer));
    }

    @Test
    public void testRemoveObserver() {
        IObserver observer = new IObserver() {
            @Override
            public void update(int rowIndex, int columnIndex, IObserver sourceComponent) {
                // Do nothing
            }
        };
        publisher.addObserver(observer);
        publisher.removeObserver(observer);
        assertFalse(publisher.getObservers().contains(observer));
    }

    @Test
    public void testGetSubscribers() {
        // Create a subscriber for a cell
        Set<String> subscribers = new HashSet<>();
        subscribers.add("11");
        publisher.subscribers.put("00", subscribers);

        Map<String, Set<String>> expectedSubscribers = new HashMap<>();
        expectedSubscribers.put("00", subscribers);

        assertEquals(expectedSubscribers, publisher.getSubscribers());
    }

    @Test
    public void testGetCells() {
        publisher.getCells().clear();
        // Create a cell
        CellModel cell = new CellModel.Builder()
                .setColumnIndex(0)
                .setRowIndex(0)
                .setShownValue(10.0)
                .build();
        publisher.cells.put("00", cell);

        Map<String, CellModel> expectedCells = new HashMap<>();
        expectedCells.put("00", cell);

        assertEquals(expectedCells, publisher.getCells());
    }
}
