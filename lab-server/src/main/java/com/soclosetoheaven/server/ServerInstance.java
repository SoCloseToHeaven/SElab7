package com.soclosetoheaven.server;


import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.server.dao.JDBCDragonDAO;
import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.server.collectionmanager.SynchronizedSQLCollectionManager;
import com.soclosetoheaven.common.commandmanagers.ServerCommandManager;

import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.server.dao.JDBCUserDAO;
import com.soclosetoheaven.server.dao.SQLDragonDAO;
import com.soclosetoheaven.server.dao.SQLUserDAO;
import com.soclosetoheaven.server.net.auth.SynchronizedSQLUserManager;
import com.soclosetoheaven.server.net.connection.UDPServerConnection;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;


public class ServerInstance{

    private final UDPServerConnection connection;

    private DragonCollectionManager cm;

    private UserManager um;

    private final BasicIO io;

    private final Lock loggerLock = new ReentrantLock();


    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Map<SocketAddress, ClientHandler> clients = new HashMap<>();

    public ServerInstance(BasicIO io) throws SocketException {
        connection = new UDPServerConnection(34684);
        //this.filePath = filePath;
        this.io = io;
    }

    public void run() {
        try {
            SQLDragonDAO dragonDAO = new JDBCDragonDAO(initializeSQLConnection());
            SQLUserDAO userDAO = new JDBCUserDAO(initializeSQLConnection());
            this.um = new SynchronizedSQLUserManager(userDAO); // NOT NULL!!!!
            cm = new SynchronizedSQLCollectionManager(dragonDAO);
        } catch (Exception e) {
            ServerApp.LOGGER.severe("%s - server shutdown".formatted(e.getMessage()));
            System.exit(-1);
        }
        ServerApp.LOGGER.info(cm.toString());
        while (ServerApp.getState()) {
            try {
                Pair<SocketAddress, byte[]> pair = connection.waitAndGetData();
                SocketAddress client = pair.getLeft();
                byte[] packet = pair.getRight();
                clients.putIfAbsent(client, new ClientHandler(client));
                ClientHandler handler = clients.get(client).put(packet);
                // в отдельной синхронизации нет смысла, тк метод receive у datagramsocket'a работает в блокирующем режиме
                io.writeln("GOT PACKAGE FROM CLIENT - %s - SIZE: %s".formatted(
                    client.toString(),
                    packet.length
                    )
                ); // не хочу логировать каждый пакет
                if (packet[connection.MAX_PACKET_SIZE] == 0) {
                    loggerLock.lock();
                    ServerApp.LOGGER.log(Level.INFO, "HANDLING REQUEST FROM CLIENT: " + client);
                    loggerLock.unlock();
                    executor.execute(handler::handle);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler {

        private final SocketAddress client;

        private final ArrayList<byte[]> buffers = new ArrayList<>();

        ClientHandler(SocketAddress client) {
            this.client = client;
        }


        void handle() {
            try {
                byte[] data;
                synchronized (buffers) {
                    data = connection.transformPackagesToData(buffers);
                    buffers.clear();
                }
                Request request;
                synchronized (SerializationUtils.class) {
                    request = SerializationUtils.deserialize(data);
                }
                Response response;

                ServerCommandManager taskCommandManager;
                if (um.checkIfAuthorized(request.getAuthCredentials()))
                    taskCommandManager = ServerCommandManager.defaultManager(cm, um);
                else
                    taskCommandManager = ServerCommandManager.authManager(um);
                try {
                    response = taskCommandManager.manage(request);
                } catch (InvalidRequestException e) {
                    response = ResponseFactory.createResponseWithException(e);
                }
                connection.sendData(new ImmutablePair<>(
                                client,
                                response
                        )
                );
            } catch (IOException | SerializationException e) {
                loggerLock.lock();
                ServerApp.LOGGER.severe(e.getMessage());
                loggerLock.unlock();
            }
        }

        synchronized ClientHandler put(byte[] buf) {
            buffers.add(buf);
            return this;
        }
    }
    private static Connection initializeSQLConnection() {
        try (BufferedReader stream = new BufferedReader(new FileReader(System.getenv("DB_CONFIG")))) {
            String driver = stream.readLine();
            String user = stream.readLine();
            String password = stream.readLine();
            return DriverManager.getConnection(driver, user, password);
        } catch (Exception e) {
            ServerApp.LOGGER.severe("%s - %s".formatted(e.getMessage(), "stopping server"));
            System.exit(-1);
        }
        return null;
    }
}
