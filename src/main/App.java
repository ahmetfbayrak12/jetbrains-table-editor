package main;

import main.component.frame.FrameMain;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrameMain::new);
    }
}
