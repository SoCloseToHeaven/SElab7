package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class SortCommand extends AbstractCommand{
    private final DragonCollectionManager collectionManager;
    public SortCommand(DragonCollectionManager collectionManager) {
        super("sort");
        this.collectionManager = collectionManager;
    }

    public SortCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        collectionManager.sort();
        return ResponseFactory.createResponse("Collection was sorted in default order");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "sort",
                " - sorts the collection in ascending order by ID"
        );
    }
}
