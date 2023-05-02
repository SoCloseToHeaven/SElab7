package com.soclosetoheaven.server;

import com.soclosetoheaven.common.io.BasicIO;


import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class ServerApp {

    private static final Lock loggerLock = new ReentrantLock();
    private static Logger Logger;

    private static volatile boolean running;


    private static final BasicIO io = new BasicIO();

    public static void main(String[] args) {
        String configPath = System.getenv("SERVER_LOGGER_CONFIG");
        if (configPath == null) {
            io.writeErr("Unable to load logger config, stopping program!");
            return;
        }
        try {
            configureLogger(configPath);
            ServerInstance server = new ServerInstance(io);
            running = true;
            server.launch();
        } catch (IOException | SecurityException e) {
            io.writeErr(e.getMessage());
            io.writeErr("Unable to start server!");
        }
    }

    public static boolean getState() {
        return running;
    }

    private static void configureLogger(String configPath) throws IOException{
        try(FileInputStream fileInputStream = new FileInputStream(configPath)) {
            new File("logs").mkdir();
            LogManager.getLogManager().readConfiguration(fileInputStream);
            Logger = Logger.getLogger(ServerApp.class.getName());
        }
    }

    public static void changeState() {
        running = !running;
    }

    public static void log(Level level, String message) {
        loggerLock.lock();
        Logger.log(level, message);
        loggerLock.unlock();
    }
}