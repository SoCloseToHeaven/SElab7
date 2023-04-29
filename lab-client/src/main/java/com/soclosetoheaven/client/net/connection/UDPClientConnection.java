package com.soclosetoheaven.client.net.connection;

import com.soclosetoheaven.common.net.connections.SimpleConnection;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class UDPClientConnection implements SimpleConnection<Response, Request> {

    private final DatagramChannel channel;

    private final SocketAddress address;

    private final ByteBuffer buffer;


    public UDPClientConnection(String addr, int port) throws IOException {
        this.address = new InetSocketAddress(addr, port);
        channel = DatagramChannel.open();
        connect(addr, port);
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
        channel.configureBlocking(false); // non-blocking mode
    }



    @Override
    public Response waitAndGetData() throws IOException {
        ArrayList<byte[]> buffers = new ArrayList<>();
        byte[] bufferArray;
        do {
            bufferArray = getData();
            buffers.add(bufferArray);
        } while (bufferArray[MAX_PACKET_SIZE] != 0);
        byte[] data = transformPackagesToData(buffers);
        return SerializationUtils.deserialize(data);
    }

    public byte[] getData() throws IOException { // method is needed to provide non-blocking channel mode
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        SocketAddress address = null;
        long timeoutChecker = System.currentTimeMillis() + CONNECTION_TIMEOUT;
        // var is needed to check connection for timeout
        while (address == null && (timeoutChecker - System.currentTimeMillis()) >= 0) {
            // желательно тут чёт написать клиенту, может быть точечки вывести
            address = channel.receive(buffer);
        }
        if (timeoutChecker < 0)
            throw new IOException("Server connection timeout!");
        return buffer.array();
    }

    @Override
    public void sendData(Request request) throws IOException{
        byte[][] packets = transformDataToPackages(request);
        for (byte[] bytePacket : packets) {
            buffer.clear();
            buffer.put(bytePacket);
            buffer.flip();
            channel.send(buffer, address);
        }
    }

    @Override
    public void connect(String adr, int port) throws IOException {
        this.channel.connect(new InetSocketAddress(adr,port));
    }

    @Override
    public void disconnect() throws IOException{
        this.channel.disconnect();
    }
}
