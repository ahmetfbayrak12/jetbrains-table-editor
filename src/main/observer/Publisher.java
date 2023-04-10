package main.observer;

import main.model.CellModel;
import main.util.FormulaParser;

import java.util.*;

/*
 * Observer pattern for notifying the cells and rerender table
 *
 * @author ahmetfbayrak
 *
 * */
public class Publisher {
    private static Publisher instance;
    public Map<String, Set<String>> subscribers;
    public Map<String, CellModel> cells;
    private List<IObserver> observers;

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

    public Map<String, Set<String>> getSubscribers() {
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

    public List<IObserver> getObservers() {
        return observers;
    }


    /*
     * Whenever a cell value is changed this method updates the cell
     *
     * @param changedRow: index of changed row
     * @param changedCol: index of changedCol
     * @param sourceComponent: observer object that updates come from
     *
     * */
    public void notifyObservers(int rowIndex, int columnIndex, IObserver sourceComponent) {
        for (IObserver observer : observers) {
            if (rowIndex >= 0 && columnIndex >= 0) {
                for (String subscriber : subscribers.getOrDefault(rowIndex + "" + columnIndex, new HashSet<>())) {
                    observer.update(Integer.parseInt(subscriber.substring(0, 1)), Integer.parseInt(subscriber.substring(1, 2)), sourceComponent);
                }
                observer.update(rowIndex, columnIndex, sourceComponent);
            }
        }
    }

    /*
     * Whenever a cell value is changed this method updates the referenced cells
     *
     * @param changedRow: index of changed row
     * @param changedCol: index of changedCol
     *
     * */
    public void notifySubsribers(int changedRow, int changedCol) {
        Set<String> subs = getSubscribers().getOrDefault(changedRow + "" + changedCol, new HashSet<>());
        if (!subs.isEmpty()) {
            for (String sub : subs) {
                FormulaParser formulaParser = new FormulaParser();
                String formula = getCells().get(sub).getFormula();
                Double result = formulaParser.parse(formula, Integer.parseInt(sub.substring(0, 1)), Integer.parseInt(sub.substring(1, 2)));
                getCells().get(sub).setShownValue(result);
            }
        }
    }
}
