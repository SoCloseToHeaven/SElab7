package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class ClearCommand extends AbstractCommand{

    private FileCollectionManager cm;
    public ClearCommand(FileCollectionManager cm) {
        super("clear");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
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
