package com.example.borch.surfaceviewcarboard;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by borch on 14/06/16.
 */
public class PositionSender {

    public static final int BUFFER_LENGTH = 1024;

    private String ip;
    private int port;
    InetAddress ipAddres;

    private DatagramSocket socket;
    private byte[] sendData;


    public PositionSender(String ip, int port) throws SocketException, UnknownHostException {
        this.ip = ip;
        this.port = port;
        sendData = new byte[BUFFER_LENGTH];
        socket = new DatagramSocket();
        ipAddres = InetAddress.getByName(ip);
    }


    public void send(String message) throws IOException {
        sendData = message.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, ipAddres, port);
        socket.send(sendPacket);
    }


}
