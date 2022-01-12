package com.company.view.panels.menu;

import com.company.view.panels.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends BasePanel {

    private final JButton btnNewGame;
    private final JButton btnJoin;
    private final JButton btnSingle;
    private final JButton btnExit;

    public MenuPanel() {
        super();
        setLayout(null);
        btnNewGame = new JButton("NewGame");
        btnJoin = new JButton("Join");
        btnSingle = new JButton("Single Play");
        btnExit = new JButton("Exit");
        componentsInit();
        buttonsInit();
    }

    private void componentsInit() {
        JLabel title = new JLabel("Tennis Online");
        add(title);
        title.setFont(new Font("Century Gothic", Font.BOLD, 30));
        title.setBounds(215, 30, 400, 40);
        btnNewGame.setBounds(235, 100, 150, 50);
        btnJoin.setBounds(235, 160, 150, 50);
        btnSingle.setBounds(235, 220, 150, 50);
        btnExit.setBounds(235, 280, 150, 50);
        btnNewGame.setFont(btnFont);
        btnJoin.setFont(btnFont);
        btnSingle.setFont(btnFont);
        btnExit.setFont(btnFont);
        btnNewGame.setBackground(btnColor);
        btnJoin.setBackground(btnColor);
        btnSingle.setBackground(btnColor);
        btnExit.setBackground(btnColor);
        add(btnNewGame);
        add(btnJoin);
        add(btnSingle);
        add(btnExit);
    }
    private void buttonsInit() {
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                NewGamePanel ngp = new NewGamePanel();
                window.add(ngp);
                ngp.addFrame(window);
                window.pack();
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                JoinPanel jp = new JoinPanel();
                window.add(jp);
                jp.addFrame(window);
                window.pack();
            }
        });
        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                SinglePlayPanel spp = new SinglePlayPanel();
                window.add(spp);
                spp.addFrame(window);
                window.pack();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
