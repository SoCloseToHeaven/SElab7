package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.businesslogic.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.factorн.RequestFactory;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.*;

public class AddCommand extends AbstractCommand{

    private final FileCollectionManager cm;

    private final BasicIO io;

    public AddCommand(FileCollectionManager cm, BasicIO io) {
        super("add");
        this.cm = cm;
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        try {
            Dragon dragon = ((RequestBodyWithDragon) requestBody).getDragon();
            cm.add(dragon);
            return ResponseFactory.createResponse(
                    "%s - %s".formatted(dragon, "added in collection")
            );
        } catch (ClassCastException e) {
            return ResponseFactory.createResponseWithException(
                    new InvalidRequestException(e.getMessage())
            );
        }
    }

    @Override
    public Request toRequest(String[] args) {
        return RequestFactory.createRequestWithDragon(getName(), null, io);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "add {element}",
                " - adds new collection element, fill the fields with values line by line"
        );
    }
}
