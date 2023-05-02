package com.soclosetoheaven.common.net.connections;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public interface SimpleConnection<getT, sendT> {
    int CONNECTION_TIMEOUT = 5_000;
    int BUFFER_SIZE = 8192;
    int MAX_PACKET_SIZE = BUFFER_SIZE - 1;

    int LAST_PACKET_TOKEN = 0;

    getT waitAndGetData() throws IOException;

    void sendData(sendT t) throws IOException;

    default byte[][] transformDataToPackages(Serializable obj) {
        byte[] data = SerializationUtils.serialize(obj);
        return transformDataToPackages(data);
    }

    default byte[][] transformDataToPackages(byte[] data) {
        byte[][] packets = new byte[(int) Math.ceil(data.length / (double) BUFFER_SIZE)][BUFFER_SIZE];

        int currentPosition = 0;
        for (int i = 0; i < packets.length; ++i) {
            packets[i] = Arrays.copyOfRange(data, currentPosition, currentPosition + BUFFER_SIZE);
            currentPosition += BUFFER_SIZE;
        }
        return packets;
    }

    default byte[] transformPackagesToData(List<byte[]> packages) {
        byte[] data = new byte[packages.size() * BUFFER_SIZE];
        int currentPosition = 0;
        for (byte[] i : packages) {
            for (byte j : i) {
                data[currentPosition] = j;
                currentPosition++;
            }
        }
        return data;
    }

    void connect(String adr, int port) throws IOException;

    void disconnect() throws IOException;
}
