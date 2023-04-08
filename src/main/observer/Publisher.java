package main.observer;

import main.model.CellModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    private static Publisher instance;
    public Map<String, List<String>> subscribers;
    public Map<String, CellModel> cells;
    private List<IObserver> observers = new ArrayList<>();

    private Publisher() {
        subscribers = new HashMap<>();
        cells = new HashMap<>();
        observers = new ArrayList<>();
    }

    public static synchronized Publisher getInstance() {
        if (instance == null) {
            instance = new Publisher();
        }
        return instance;
    }

    public Map<String, List<String>> getSubscribers() {
        return subscribers;
    }

    public Map<String, CellModel> getCells() {
        return cells;
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int rowIndex, int columnIndex, IObserver sourceComponent) {
        for (IObserver observer : observers) {
            if (rowIndex >= 0 && columnIndex >= 0) {
                observer.update(rowIndex, columnIndex, sourceComponent);
            }
        }
    }

}
