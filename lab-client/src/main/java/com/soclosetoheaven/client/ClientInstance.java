package com.soclosetoheaven.client;

import com.soclosetoheaven.common.commandmanagers.ClientCommandManager;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.exceptions.UnauthorizedException;
import com.soclosetoheaven.common.io.BasicIO;

import com.soclosetoheaven.client.net.connection.UDPClientConnection;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
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

    private AuthCredentials authCredentials;


    /**
     * Prefix used before user's input
     */
    private static final String INPUT_PREFIX = TerminalColors.setColor("> ", TerminalColors.CYAN);


    private final int port;



    public ClientInstance(int port) {
        this.io = new BasicIO();
        this.commandManager = ClientCommandManager.defaultManager(io);
        this.port = port;
    }


    public void launch() {
        try {
            connection = new UDPClientConnection("localhost", port);
        } catch (IOException e) {
            io.writeErr(e.getMessage());
            io.writeErr("No option to continue work of client application!");
            return;
        }
        io.writeln(TerminalColors.setColor(
                "Welcome to client-app, please, login or register, or see possible command by typing 'help', or exit program typing 'exit'",
                TerminalColors.GREEN)
        );
        String input;
        while ((input = io.read(INPUT_PREFIX)) != null) {
            try {
                Request request = commandManager.manage(input);
                if (request == null)
                    continue;
                if (request.getAuthCredentials() == null)
                    request.setAuthCredentials(this.authCredentials);
                else
                    this.authCredentials = request.getAuthCredentials();
                connection.sendData(request);
                Response response = connection.waitAndGetData();
                io.writeln(
                        TerminalColors.setColor(response.toString(), TerminalColors.BLUE)
                );
            } catch (UnauthorizedException e) {
                this.authCredentials = null;
                io.writeErr("Auth credentials cleared");
            } catch (PortUnreachableException e){
                io.writeErr("UNABLE TO CONNECT TO SERVER!");
            } catch (IOException |
                     ManagingException |
                     SerializationException e) {
                io.writeErr(e.getMessage());
            }
        }
    }
}
