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

    private final DragonCollectionManager collectionManager;

    private final BasicIO io;
    private final UserManager userManager;

    public AddCommand(DragonCollectionManager collectionManager, BasicIO io, UserManager userManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.io = io;
        this.userManager = userManager;
    }

    public AddCommand(BasicIO io) {
        this(null, io, null);
    }

    public AddCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        this(collectionManager,null, userManager);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        if (!(requestBody instanceof RequestBodyWithDragon))
            throw new InvalidRequestException();
        Dragon dragon = ((RequestBodyWithDragon) requestBody).getDragon();
        dragon.setCreatorId(userManager.getUserByAuthCredentials(requestBody.getAuthCredentials()).getID());
        if (!collectionManager.add(dragon))
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
