package com.company.view.panels.menu;

import com.company.view.panels.BasePanel;
import com.company.view.panels.game.GameBotPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayPanel extends BasePanel {

    private final JButton btnPlay;
    private final JButton btnBack;
    private final JRadioButton easy;
    private final JRadioButton normal;
    private final JRadioButton insane;
    private int difficulty;

    public SinglePlayPanel() {
        super();
        setLayout(null);
        btnPlay = new JButton("Play");
        btnBack = new JButton("Back");
        easy = new JRadioButton("Easy");
        normal = new JRadioButton("Normal");
        insane = new JRadioButton("Insane");
        ButtonGroup choice = new ButtonGroup();
        choice.add(easy);
        choice.add(normal);
        choice.add(insane);
        componentsInit();
        buttonsInit();
    }
    private void componentsInit() {
        JLabel text = new JLabel("Chose difficulty:");
        add(text);
        text.setFont(textFont);
        text.setBounds(205, 50, 500, 30);
        easy.setBounds(160, 120, 100, 50);
        normal.setBounds(260, 120, 100, 50);
        insane.setBounds(380, 120, 100, 50);
        btnPlay.setBounds(235, 200, 150, 50);
        btnBack.setBounds(235, 260, 150, 50);
        easy.setFont(btnFont);
        normal.setFont(btnFont);
        insane.setFont(btnFont);
        btnPlay.setFont(btnFont);
        btnBack.setFont(btnFont);
        easy.setBackground(bgColor);
        normal.setBackground(bgColor);
        insane.setBackground(bgColor);
        btnPlay.setBackground(btnColor);
        btnBack.setBackground(btnColor);
        add(easy);
        add(normal);
        add(insane);
        add(btnPlay);
        add(btnBack);
    }
    private void buttonsInit() {
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 2;
            }
        });
        insane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 3;
            }
        });
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (difficulty != 0) {
                    window.getContentPane().removeAll();
                    GameBotPanel gp = new GameBotPanel(difficulty);
                    window.add(gp);
                    gp.addFrame(window);
                    window.revalidate();
                    window.pack();
                    gp.requestFocus();
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
