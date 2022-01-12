package com.company.model;

import java.nio.ByteBuffer;

public class Message {
    private final byte[] x;
    private final byte[] y;
    private final byte[] messageType;

    public Message(int messageType) {
        this.messageType = ByteBuffer.allocate(4).putInt(messageType).array();
        x = new byte[4];
        y = new byte[4];
    }

    public Message(int messageType, int x, int y) {
        this.x = ByteBuffer.allocate(4).putInt(x).array();
        this.y = ByteBuffer.allocate(4).putInt(y).array();
        this.messageType = ByteBuffer.allocate(4).putInt(messageType).array();
    }

    public Message(byte[] bytes) {
        x = new byte[4];
        y = new byte[4];
        messageType = new byte[4];
        for(int i = 0; i < 4; i++) {
            x[i] = bytes[i];
            y[i] = bytes[i + 4];
            messageType[i] = bytes[i + 8];
        }
    }

    private int getInt(byte[] val) {
        return ((val[0] & 0xFF) << 24) + ((val[1] & 0xFF) << 16) + ((val[2] & 0xFF) << 8) + ((val[3] & 0xFF));
    }

    public int getIntX() {
        return getInt(x);
    }

    public byte[] getByteX() {
        return x;
    }

    public int getIntY() {
        return getInt(y);
    }
    public byte[] getByteY() {
        return y;
    }

    public byte[] getMessageType() {
        return messageType;
    }
    public int getIntMessageType() {
        return getInt(messageType);
    }

    public byte[] getFullMessage() {
        byte[] message = new byte[12];
        for(int i = 0; i < 4; i++) {
            message[i] = x[i];
            message[i+4] = y[i];
            message[i+8] = messageType[i];
        }
        return message;
    }

    @Override
    public String toString() {
        return getIntMessageType() + " " + getIntX() + " " + getIntY();
    }
}
