package com.company.controller;

import com.company.model.GameStates;
import com.company.model.Message;
import com.company.view.panels.game.OnlineGamePanel;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Controller {
    private final OnlineGamePanel game;
    private Connection player;

    public Controller(OnlineGamePanel game) {
        this.game = game;
    }

    public void createGameRoom(int gameName, InetAddress ip) throws IOException {
        GameServer server = new GameServer(gameName, ip);
        server.start();
    }

    public void connectToRoom(int gameName, InetAddress ip) throws IOException {
        player = new Connection(new DatagramSocket(), gameName, ip);
        player.send(new Message(GameStates.CONNECT));
    }

    public void disconnectGameRoom() throws IOException {
        player.send(new Message(GameStates.DISCONNECT));
    }

    public void closePlayer() {
        player.close();
    }

    public void sendMessage(int type, int x, int y) throws IOException {
        player.send(new Message(type, x, y));
    }

    public boolean receiveMessage() throws IOException, ClassNotFoundException {
        Message message = player.receive();
        if (message.getIntMessageType() == GameStates.PLAYER) {
            game.setPlayer2Y(message.getIntY());
        } else if (message.getIntMessageType() == GameStates.BALL) {
            game.setBallVector(message.getIntX(), message.getIntY());
        } else if (message.getIntMessageType() == GameStates.SCORE) {
            game.setScore1(message.getIntX(), message.getIntY());
            game.setDefault();
            game.changeBallSpeedX();
        }  else if (message.getIntMessageType() == GameStates.DISCONNECT) {
            game.endGame();
            OnlineGamePanel.callInformationWindow("Your opponent has left the game. You have won a technical victory!");
        } else if (message.getIntMessageType() == GameStates.READY) {
            return true;
        }
        if (game.checkEndGame() != 0) {
            if (game.checkEndGame() == 2) {
                player.send(new Message(GameStates.DEFEAT));
                OnlineGamePanel.callInformationWindow("You lose!");
            } else {
                player.send(new Message(GameStates.WIN));
                OnlineGamePanel.callInformationWindow("You win!");
            }
            game.endGame();
        }
        return false;
    }
}