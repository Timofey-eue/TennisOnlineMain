package com.company.view.panels;

import com.company.view.panels.menu.MenuPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(String s) {
        super(s);
        MenuPanel menu = new MenuPanel();
        add(menu);
        menu.addFrame(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
