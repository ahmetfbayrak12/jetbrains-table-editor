package JetBrainsTableEditor.observer;

public interface IObserver {
    void update(int rowIndex, int columnIndex, IObserver sourceComponent);
}