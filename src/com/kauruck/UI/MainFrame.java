package com.kauruck.UI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private final MainPanel panel = new MainPanel();

    public MainFrame(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setLocationRelativeTo(null);
        this.setSize(500, 500);

        this.add(panel);
        this.setVisible(true);
    }
}
