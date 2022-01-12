package com.company.model;

import java.net.InetAddress;

public class PlayerConnectionData {
    private final InetAddress ipAddress;
    private final int port;

    public PlayerConnectionData(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public InetAddress getIpAddress() { return ipAddress;}
    public int getPort() { return port;}
}
