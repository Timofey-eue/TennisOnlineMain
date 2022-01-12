package com.company.controller;

import com.company.model.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection {
    private final DatagramSocket playerSocket;
    private final int serverPort;
    private final InetAddress serverIpAddress;

    public Connection(DatagramSocket playerSocket, int gameName, InetAddress ipAddress) {
        this.playerSocket = playerSocket;
        this.serverPort = gameName;
        this.serverIpAddress = ipAddress;
    }

    public void send(Message message) throws IOException {
        byte[] sendData = message.getFullMessage();
        playerSocket.send(new DatagramPacket(sendData, sendData.length, serverIpAddress, serverPort));
    }

    public Message receive() throws IOException {
        DatagramPacket receivePacket = new DatagramPacket(new byte[12], 12);
        playerSocket.receive(receivePacket);
        return new Message(receivePacket.getData());
    }

    public void close() {
        playerSocket.close();
    }
}