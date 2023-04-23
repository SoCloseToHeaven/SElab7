package com.soclosetoheaven.client;


/**
 * Client main-class
 */
public class ClientApp {

    /**
     * private class-constructor
     */
    private ClientApp() {
        throw new UnsupportedOperationException("This is utility class!");
    }

    /**
     * main method, runs ConsoleClient
     * @param args command-string arguments
     */
    public static void main(String[] args){
        ClientInstance client = new ClientInstance();
        client.run();
    }
}
