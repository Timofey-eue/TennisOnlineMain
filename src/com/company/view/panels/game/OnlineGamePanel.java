package com.company.view.panels.game;

import com.company.controller.Controller;
import com.company.model.GameModel;
import com.company.model.GameStates;
import com.company.view.panels.BasePanel;
import com.company.view.panels.menu.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class OnlineGamePanel extends BasePanel implements ActionListener, KeyListener {

    private BufferedImage court;
    private GameModel model;
    private Timer FPS;
    private int gameName;
    private InetAddress ip;
    private Controller controller;
    private final boolean hostPlayer;
    private StartGame startGame;

    public OnlineGamePanel(int port, String ip, int score) {
        init(port, ip);
        model.setMaxScore(score);
        hostPlayer = false;
    }

    public OnlineGamePanel(int port, String ip) {
        init(port, ip);
        hostPlayer = true;
    }

    private void init(int port, String ip) {
        model = new GameModel();
        addKeyListener(this);
        FPS = new Timer(10, this);
        setFocusable(true);
        gameName = port;
        try {
            this.ip = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        startGame = new StartGame();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class StartGame extends Thread {
        private boolean running = false;

        @Override
        public void run() {
            running = true;
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    if (!hostPlayer) {
                        controller.createGameRoom(gameName, ip);
                        controller.connectToRoom(gameName, ip);
                        callInformationWindow("Waiting an opponent...");
                        if (controller.receiveMessage()) {
                            callInformationWindow("The second player connected! Starting the game.");
                            sendScore(model.getScore1(), model.getMaxScore());
                        } else {
                            callInformationWindow("The second player is not connected. Enter Esc to disconnect.");
                        }
                    } else {
                        controller.connectToRoom(gameName, ip);
                        callInformationWindow("Connecting to the room...");
                        sendReady();
                        controller.receiveMessage();
                    }
                    new ReceiveThread().start();
                    model.changeBallSpeedX();
                    FPS.start();
                } catch (Exception e) {
                    running = false;
                    Thread.currentThread().interrupt();
                }
            }
            running = false;
        }

        public boolean isRunning() {
            return running;
        }
    }

    private void disconnectGameRoom() {
        try {
            controller.disconnectGameRoom();
            callInformationWindow("You have disconnected from the room. Game over.");
        } catch (Exception e) {
            callInformationWindow("Error while disconnecting from the room.");
        }
    }

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            while (checkEndGame() == 0) {
                try {
                    controller.receiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        }
    }

    public int checkEndGame() {
        return model.checkEndGame();
    }

    public void endGame() {
        if (FPS.isRunning())
            FPS.stop();
        disconnectGameRoom();
        controller.closePlayer();
        window.getContentPane().removeAll();
        MenuPanel menu = new MenuPanel();
        window.add(menu);
        window.pack();
        menu.addFrame(window);
    }

    public void setDefault() {
        model.setBallX(GameModel.defaultBallX);
        model.setBallY(GameModel.defaultBallY);
        model.setBallSpeedX(GameModel.defaultBallSpeedX);
        model.setPlayer1Y(GameModel.defaultY1);
        model.setPlayer2Y(GameModel.defaultY2);
    }

    @Override
    public void paint(Graphics g) {

        try {
            court = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/bg.png")));
        } catch (IOException e) { e.printStackTrace();}
        g.drawImage(court, 0, 0, WIDTH, HEIGHT, this);

        g.setColor(new Color(0, 0, 255));
        g.fillRect(model.getPlayer1X(), model.getPlayer1Y(), GameModel.playerWidth, GameModel.playerHeight);
        g.setColor(new Color(255, 0, 0));
        g.fillRect(model.getPlayer2X(), model.getPlayer2Y(), GameModel.playerWidth, GameModel.playerHeight);

        g.setColor(new Color(0, 0, 0));
        g.fillOval(model.getBallX() - 2, model.getBallY() - 2, GameModel.ballSize, GameModel.ballSize);
        g.setColor(new Color(255, 250, 205));
        g.fillOval(model.getBallX(), model.getBallY(), GameModel.ballSize - 4, GameModel.ballSize - 4);

        Font f = new Font("Arial", Font.BOLD, 25);
        g.setFont(f);
        g.drawString(String.valueOf(model.getScore1()), 260, 30);
        g.drawString(String.valueOf(model.getScore2()), 325, 30);
        Font f1 = new Font("Arial", Font.BOLD, 15);
        g.setFont(f1);
        g.drawString("Max Score: " + model.getMaxScore(), 425, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        model.moveBallX();
        model.moveBallY();

        if (model.checkBallRebound()) {
            model.changeBallSpeedY();
        }

        if (model.checkBallCollision()) {
            model.changeBallSpeedX();
            sendBall(WIDTH - model.getBallX(), model.getBallY());
        }

        if (model.checkBallIsOut()) {
            model.incrScore2();
            sendScore(model.getScore2(), model.getMaxScore());
            setDefault();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN && model.getPlayer1Y() <= 292) {
            model.incrPlayer1Y();
            sendMove(model.getPlayer2X(), model.getPlayer1Y());
        } else if (e.getKeyCode() == KeyEvent.VK_UP && model.getPlayer1Y() > 5) {
            model.decrPlayer1Y();
            sendMove(model.getPlayer2X(), model.getPlayer1Y());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !startGame.isRunning()) {
            startGame.start();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (startGame.isRunning())
                startGame.interrupt();
            endGame();
        }
    }

    public static void callInformationWindow(String message) {
        JOptionPane.showMessageDialog(null, message, "Attention!", JOptionPane.ERROR_MESSAGE);
    }

    public void sendMove(int x, int y) {
        try {
            controller.sendMessage(GameStates.PLAYER, x, y);
        } catch (Exception e) { e.printStackTrace();}
    }

    public void sendBall(int x, int y) {
        try {
            controller.sendMessage(GameStates.BALL, x, y);
        } catch (Exception e) { e.printStackTrace();}
    }

    public void sendScore(int score, int maxScore) {
        try {
            controller.sendMessage(GameStates.SCORE, score, maxScore);
        } catch (Exception e) { e.printStackTrace();}
    }

    public void sendReady() {
        try {
            controller.sendMessage(GameStates.READY,  model.getScore1(), model.getMaxScore());
        } catch (Exception e) { e.printStackTrace();}
    }

    public void setPlayer2Y(int y) {
        model.setPlayer2Y(y);
    }

    public void setBallVector(int x, int y) {
        model.setBallX(x);
        model.setBallY(y);
        model.changeBallSpeedX();
    }

    public void setScore1(int score, int maxScore) {
        model.setScore1(score);
        if (model.getMaxScore() == 0)
            model.setMaxScore(maxScore);
    }

    public void changeBallSpeedX() {
        model.changeBallSpeedX();
    }
}