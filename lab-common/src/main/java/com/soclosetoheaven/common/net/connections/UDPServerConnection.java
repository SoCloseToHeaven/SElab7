package com.soclosetoheaven.common.net.connections;

import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.net.*;


public class UDPServerConnection implements SimpleConnection<Pair<SocketAddress, byte[]>, Pair<SocketAddress, Response>> {
    private final DatagramSocket socket;

    private byte[] buffer = new byte[BUFFER_SIZE];
    public UDPServerConnection(int port) throws SocketException{
        this.socket = new DatagramSocket(port);
    }

    @Override
    public Pair<SocketAddress, byte[]> waitAndGetData() throws IOException {
        buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
        socket.receive(packet);
        return new ImmutablePair<>(packet.getSocketAddress(), buffer);
    }

    @Override
    public void sendData(Pair<SocketAddress, Response> pair) throws IOException {
        SocketAddress client = pair.getLeft();
        Response response = pair.getRight();
        byte[][] packages = transformDataToPackages(response);
        synchronized (this) {
            for (byte[] pack : packages) {
                socket.send(new DatagramPacket(
                        pack,
                        BUFFER_SIZE,
                        client
                        )
                );
            }
        }
    }

    @Override
    public byte[][] transformDataToPackages(Serializable obj) {
        synchronized (UDPServerConnection.class) {
            return SimpleConnection.super.transformDataToPackages(obj);
        }
    }

    @Override
    public void connect(String adr, int port) throws IOException {

    }

    @Override
    public void disconnect() throws IOException {

    }

}
