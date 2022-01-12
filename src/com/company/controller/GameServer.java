package com.company.controller;

import com.company.model.GameStates;
import com.company.model.Message;
import com.company.model.PlayerConnectionData;
import com.company.view.panels.game.OnlineGamePanel;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameServer extends Thread {
    private final DatagramSocket serverSocket;
    private PlayerConnectionData player1, player2;
    private final BufferedWriter logWriter;

    public GameServer(int gameName, InetAddress ip) throws IOException {
        serverSocket = new DatagramSocket(gameName, ip);
        logWriter = new BufferedWriter(new FileWriter("log.txt"));
        logWriter.write("Server start\n");
    }

    @Override
    public void run() { getConnection();}

    public void getConnection() {
        try {
            int playerCount = 0;
            while (playerCount != 2) {
                byte[] receiveData = new byte[12];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                playerCount++;
                if (playerCount == 1) {
                    player1 = new PlayerConnectionData(receivePacket.getAddress(), receivePacket.getPort());
                    logWriter.write("player1 connected: " + receivePacket.getPort() + "\n");
                } else {
                    player2 = new PlayerConnectionData(receivePacket.getAddress(), receivePacket.getPort());
                    logWriter.write("player2 connected: " + receivePacket.getPort()  + "\n");
                }
            }
            new Communication().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Communication extends Thread {
        private volatile boolean stopCycle = false;

        @Override
        public void run() {
            try {
                while (!stopCycle) {
                    byte[] receiveData = new byte[12];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    Message message = new Message(receivePacket.getData());
                    if (message.getIntMessageType() == GameStates.DISCONNECT ||
                            message.getIntMessageType() == GameStates.DEFEAT ||
                            message.getIntMessageType() == GameStates.WIN) {
                        sendMessageEnemy(message, receivePacket.getPort());
                        stopCycle = true;
                        serverSocket.close();
                        logWriter.write("Server shutdown");
                        logWriter.close();
                    } else sendMessageEnemy(message, receivePacket.getPort());
                }
            } catch (Exception e) {
                OnlineGamePanel.callInformationWindow("Error while exchanging packages. Communication lost!");
            }
        }

        private void sendMessageEnemy(Message message, int port) throws IOException {
            if (port == player1.getPort()) {
                serverSocket.send(new DatagramPacket(message.getFullMessage(),
                        12, player2.getIpAddress(), player2.getPort()));
                logWriter.write("Message for (" + player2.getPort() + ") player2: " + message  + "\n");
            }
            else {
                serverSocket.send(new DatagramPacket(message.getFullMessage(),
                        12, player1.getIpAddress(), player1.getPort()));
                logWriter.write("Message for (" + player1.getPort() + ") player1: " + message  + "\n");
            }
        }
    }
}