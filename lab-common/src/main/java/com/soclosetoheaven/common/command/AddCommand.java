package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.RequestFactory;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.*;

public class AddCommand extends AbstractCommand{

    private final DragonCollectionManager cm;

    private final BasicIO io;
    private final UserManager um;

    public AddCommand(DragonCollectionManager cm, BasicIO io, UserManager um) {
        super("add");
        this.cm = cm;
        this.io = io;
        this.um = um;
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        if (!(requestBody instanceof RequestBodyWithDragon))
            throw new InvalidRequestException();
        Dragon dragon = ((RequestBodyWithDragon) requestBody).getDragon();
        dragon.setCreatorId(um.getUserByAuthCredentials(requestBody.getAuthCredentials()).getID());
        if (!cm.add(dragon))
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponse("Successfully added");
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
