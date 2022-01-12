package com.company.view.panels.menu;

import com.company.controller.Controller;
import com.company.view.panels.BasePanel;
import com.company.view.panels.game.OnlineGamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGamePanel extends BasePanel {

    private final JButton btnCreate;
    private final JButton btnBack;
    private final JTextField nameEnterArea;
    private final JTextField scoreEnterArea;

    public NewGamePanel() {
        super();
        setLayout(null);
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
        nameEnterArea = new JTextField(20);
        scoreEnterArea = new JTextField(3);
        componentsInit();
        buttonsInit();
    }
    private void componentsInit() {
        JLabel text1 = new JLabel("Enter port and ip-address:");
        add(text1);
        text1.setFont(textFont);
        text1.setBounds(147, 30, 500, 30);
        add(nameEnterArea);
        nameEnterArea.setBounds(150, 80, 300, 30);
        nameEnterArea.setFocusable(true);

        JLabel text2 = new JLabel("Number of wins:");
        add(text2);
        text2.setFont(textFont);
        text2.setBounds(160, 120, 300, 30);
        add(scoreEnterArea);
        scoreEnterArea.setBounds(380, 125, 50, 30);
        scoreEnterArea.setFocusable(true);

        btnCreate.setBounds(235, 200, 150, 50);
        btnBack.setBounds(235, 260, 150, 50);
        btnCreate.setFont(btnFont);
        btnBack.setFont(btnFont);
        btnCreate.setBackground(btnColor);
        btnBack.setBackground(btnColor);
        add(btnCreate);
        add(btnBack);
    }
    private void buttonsInit() {
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = nameEnterArea.getText();
                int port = Integer.parseInt(address.substring(0, 4));
                address = address.substring(5);
                int score = Integer.parseInt(scoreEnterArea.getText().trim());
                if (port != 0 && score != 0) {
                    window.getContentPane().removeAll();
                    OnlineGamePanel game = new OnlineGamePanel(port, address, score);
                    Controller controller = new Controller(game);
                    game.setController(controller);
                    window.add(game);
                    window.revalidate();
                    window.pack();
                    game.addFrame(window);
                    game.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "You entered incorrect data.",
                            "Attention!", JOptionPane.ERROR_MESSAGE);
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
