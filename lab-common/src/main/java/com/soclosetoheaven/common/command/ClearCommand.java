package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class ClearCommand extends AbstractCommand{

    private final DragonCollectionManager cm;
    private final UserManager um;
    public ClearCommand(DragonCollectionManager cm, UserManager um) {
        super("clear");
        this.cm = cm;
        this.um = um;
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidAccessException{
        if (!um.getUserByAuthCredentials(requestBody.getAuthCredentials()).isAdmin())
            throw new InvalidAccessException();
        cm.clear();
        return ResponseFactory.createResponse("Collection was successfully cleared");
    }

    @Override
    public Request toRequest(String[] args) {
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
