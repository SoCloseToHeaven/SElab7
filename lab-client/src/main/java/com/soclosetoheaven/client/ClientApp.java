package com.soclosetoheaven.client;


/**
 * Client main-class
 */
public class ClientApp {

    private final static int FIRST_ARG = 0;

    private final static int PORT_LOWER_LIMIT = 1024;

    private final static int PORT_UPPER_LIMIT = 65535;
    public static void main(String[] args){
        if (System.console() == null)
            return;
        int port;
        if (args.length < 1 || !args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return;
        port = Integer.parseInt(args[FIRST_ARG]);
        if (port < PORT_LOWER_LIMIT || port > PORT_UPPER_LIMIT)
            return;
        ClientInstance client = new ClientInstance(port);
        client.launch();
    }
}
