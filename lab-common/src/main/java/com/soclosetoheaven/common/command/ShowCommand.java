package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.List;

public class ShowCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;
    public ShowCommand(DragonCollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    public ShowCommand() {
        this(null);
    }


    @Override
    public Response execute(RequestBody requestBody) {
        List<Dragon> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            return new Response("No elements found in collection");
        }
        StringBuilder builder = new StringBuilder();
        collection.forEach(element -> builder.append(element.toString()).append("\n"));
        return ResponseFactory.createResponse(builder.toString().trim());
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "show",
                " - displays all elements of collection with their data"
        );
    }
}
