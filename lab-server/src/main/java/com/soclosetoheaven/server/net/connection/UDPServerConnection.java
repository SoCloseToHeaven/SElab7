package com.soclosetoheaven.server.net.connection;

import com.soclosetoheaven.common.net.connections.SimpleConnection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.net.*;


public class UDPServerConnection implements SimpleConnection<Pair<SocketAddress, byte[]>, Pair<SocketAddress, byte[]>> {
    private final DatagramSocket socket;

    public UDPServerConnection(int port) throws SocketException{
        this.socket = new DatagramSocket(port);
    }

    @Override
    public Pair<SocketAddress, byte[]> waitAndGetData() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
        socket.receive(packet);
        return new ImmutablePair<>(packet.getSocketAddress(), buffer);
    }

    @Override
    public void sendData(Pair<SocketAddress, byte[]> pair) throws IOException {
        SocketAddress client = pair.getLeft();
        byte[] data = pair.getRight();
        byte[][] packages = transformDataToPackages(data);
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

    @Deprecated
    @Override
    public void connect(String adr, int port) throws IOException {
        socket.connect(new InetSocketAddress(adr, port));
    }

    @Deprecated
    @Override
    public void disconnect() {
        socket.disconnect();
    }

}
