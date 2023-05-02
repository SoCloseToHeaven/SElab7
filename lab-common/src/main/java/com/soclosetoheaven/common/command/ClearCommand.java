package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class ClearCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;
    private final UserManager userManager;
    public ClearCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super("clear");
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public ClearCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidAccessException{
        if (!userManager.getUserByAuthCredentials(requestBody.getAuthCredentials()).isAdmin())
            throw new InvalidAccessException();
        if (collectionManager.clear())
            return ResponseFactory
                    .createResponse("Collection was successfully cleared");
        return ResponseFactory
                .createResponseWithException(new ManagingException("An error occurred while clearing collection"));
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "clear",
                " - removes all elements out of collection"
        );
    }
}
