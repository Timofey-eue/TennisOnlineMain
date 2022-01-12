package com.company.view.panels.menu;

import com.company.controller.Controller;
import com.company.view.panels.BasePanel;
import com.company.view.panels.game.OnlineGamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinPanel extends BasePanel {
    private final JButton btnPlay;
    private final JButton btnBack;
    private final JTextField nameEnterArea;

    public JoinPanel() {
        super();
        setLayout(null);
        btnPlay = new JButton("Play");
        btnBack = new JButton("Back");
        nameEnterArea = new JTextField(20);
        componentsInit();
        buttonsInit();
    }
    private void componentsInit() {
        JLabel text = new JLabel("Enter port and ip-address:");
        add(text);
        text.setFont(textFont);
        text.setBounds(147, 30, 500, 30);
        add(nameEnterArea);
        nameEnterArea.setBounds(150, 90, 300, 30);
        nameEnterArea.setFocusable(true);
        btnPlay.setBounds(235, 200, 150, 50);
        btnBack.setBounds(235, 260, 150, 50);
        btnPlay.setFont(btnFont);
        btnBack.setFont(btnFont);
        btnPlay.setBackground(btnColor);
        btnBack.setBackground(btnColor);
        add(btnPlay);
        add(btnBack);
    }
    private void buttonsInit() {
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = nameEnterArea.getText();
                int port = Integer.parseInt(address.substring(0, 4));
                address = address.substring(5);
                if (port != 0) {
                    window.getContentPane().removeAll();
                    OnlineGamePanel game = new OnlineGamePanel(port, address);
                    Controller controller = new Controller(game);
                    game.setController(controller);
                    window.add(game);
                    window.revalidate();
                    window.pack();
                    game.addFrame(window);
                    game.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "You entered incorrect data.", "Attention!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                MenuPanel menu = new MenuPanel();
                window.add(menu);
                window.pack();
                menu.addFrame(window);
            }
        });
    }
}
