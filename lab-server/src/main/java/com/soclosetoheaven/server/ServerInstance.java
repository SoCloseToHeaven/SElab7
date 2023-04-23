package com.soclosetoheaven.server;


import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.commandmanagers.ServerCommandManager;

import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.connections.UDPServerConnection;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;


public class ServerInstance{

    private UDPServerConnection connection;
    private ServerCommandManager commandManager;

    private FileCollectionManager fcm;

    private final BasicIO io;

    private final String filePath;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Map<SocketAddress, ClientHandler> clients = new HashMap<>();
    public ServerInstance(String filePath, BasicIO io) throws SocketException {
        connection = new UDPServerConnection(34684);
        this.filePath = filePath;
        this.io = io;
    }

    public void run() {
        try {
            fcm = new FileCollectionManager(filePath);
            commandManager = ServerCommandManager.defaultManager(fcm);
            Runtime.getRuntime().addShutdownHook(new Thread(fcm::save));
        } catch (IOException e) {
            ServerApp.LOGGER.severe("%s - server shutdown".formatted(e.getMessage()));
            System.exit(-1);
        }
        ServerApp.LOGGER.info(fcm.toString());
        while (ServerApp.getState()) {
            try {
                Pair<SocketAddress, byte[]> pair = connection.waitAndGetData();
                SocketAddress client = pair.getLeft();
                byte[] packet = pair.getRight();
                clients.putIfAbsent(client, new ClientHandler(client));
                ClientHandler handler = clients.get(client).put(packet);
                ServerApp.LOGGER.log(Level.INFO, "GOT PACKAGE FROM CLIENT - %s - SIZE: %s".formatted(
                    client.toString(),
                    packet.length
                    )
                );
                if (packet[connection.MAX_PACKET_SIZE] == 0) {
                    ServerApp.LOGGER.log(Level.INFO, "HANDLING REQUEST FROM CLIENT: " + client.toString());
                    executor.execute(handler::handle);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler {

        private SocketAddress client;

        private ArrayList<byte[]> buffers = new ArrayList<>();

        ClientHandler(SocketAddress client) {
            this.client = client;
        }


        void handle() {
            try {
                byte[] data = connection.transformPackagesToData(buffers);
                buffers.clear();
                Request request = SerializationUtils.deserialize(data);
                Response response;
                synchronized (commandManager) {
                    response = commandManager.manage(request);
                }
                connection.sendData(new ImmutablePair<>(
                                client,
                                response
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                buffers.clear();
            }
        }

        ClientHandler put(byte[] buf) {
            buffers.add(buf);
            return this;
        }
    }
}
