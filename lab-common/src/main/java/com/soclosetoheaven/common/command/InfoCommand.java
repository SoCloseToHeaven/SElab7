package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class InfoCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;
    public InfoCommand(DragonCollectionManager collectionManager) {
        super("info");
        this.collectionManager = collectionManager;
    }

    public InfoCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        return new Response(collectionManager.toString());
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "info",
                " - displays information about the collection"
        );
    }
}
