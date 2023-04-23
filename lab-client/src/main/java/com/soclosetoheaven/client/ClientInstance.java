package com.soclosetoheaven.client;

import com.soclosetoheaven.common.commandmanagers.ClientCommandManager;
import com.soclosetoheaven.common.exceptions.ExecutingScriptException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.UnknownCommandException;
import com.soclosetoheaven.common.io.BasicIO;

import com.soclosetoheaven.common.net.connections.UDPClientConnection;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.TerminalColors;
import org.apache.commons.lang3.SerializationException;

import java.io.IOException;
import java.net.PortUnreachableException;

/**
 * class used for interaction of user with console
 */
public class ClientInstance {
    private final BasicIO io;

    private UDPClientConnection connection;

    private final ClientCommandManager commandManager;


    /**
     * Prefix used before user's input
     */
    private static final String INPUT_PREFIX = TerminalColors.setColor("> ", TerminalColors.CYAN);




    public ClientInstance() {
        this.io = new BasicIO();
        this.commandManager = ClientCommandManager.defaultManager(io);
    }


    public void run() {
        try {
            connection = new UDPClientConnection("localhost", 34684);
        } catch (IOException e) {
            io.writeErr(e.getMessage());
            io.writeErr("No option to continue work of client application!");
            System.exit(-29);
        }
        String input;
        while ((input = io.read(INPUT_PREFIX)) != null) {
            try {
                Request request = commandManager.manage(input);
                if (request == null)
                    continue;
                connection.sendData(request);
                Response response = connection.waitAndGetData();
                io.writeln(
                        TerminalColors.setColor(response.toString(), TerminalColors.BLUE)
                );
            } catch (PortUnreachableException e){
                io.writeErr("UNABLE TO CONNECT TO SERVER!");
            } catch (IOException |
                     UnknownCommandException |
                     InvalidCommandArgumentException |
                     ExecutingScriptException |
                     SerializationException e) {
                io.writeErr(e.getMessage());
            }
        }
    }
}
