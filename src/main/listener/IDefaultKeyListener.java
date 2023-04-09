package main.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface IDefaultKeyListener extends KeyListener {
    @Override
    default void keyTyped(KeyEvent e) {

    }

    @Override
    default void keyReleased(KeyEvent e) {

    }

    @Override
    default void keyPressed(KeyEvent e) {

    }
}
