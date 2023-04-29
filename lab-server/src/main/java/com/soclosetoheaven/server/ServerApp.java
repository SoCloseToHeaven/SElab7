package com.soclosetoheaven.server;

import com.soclosetoheaven.common.io.BasicIO;


import java.io.*;
import java.net.SocketException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class ServerApp {


    public  static  Logger LOGGER;

    private static boolean running;


    private static final BasicIO io = new BasicIO();
    private ServerApp() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws SocketException {
        configureLogger();
        ServerInstance server = new ServerInstance(io);
        running = true;
        server.run();
    }

    public static boolean getState() {
        return running;
    }

    private static void configureLogger() {
        String configPath = System.getenv("SERVER_LOGGER_CONFIG");
        if (configPath == null) {
            io.writeErr("Unable to load logger's configuration");
            System.exit(-1);
        }
        try(FileInputStream fileInputStream = new FileInputStream(configPath)) {
            new File("logs").mkdir();
            LogManager.getLogManager().readConfiguration(fileInputStream);
            LOGGER = Logger.getLogger(ServerApp.class.getName());
        }
        catch (IOException e){
            io.writeErr(e.getMessage());
            io.writeErr("Unable to load logger's configuration");
            System.exit(-1);
        } catch (SecurityException e) {
            io.writeErr("Unable to create logs directory.");
            System.exit(-1);
        }
    }

    public static void changeState() {
        running = !running;
    }
}