package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GroupCountingByCreationDateCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;

    public GroupCountingByCreationDateCommand(DragonCollectionManager collectionManager) {
        super("group_counting_by_creation_date");
        this.collectionManager = collectionManager;
    }

    public GroupCountingByCreationDateCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        HashMap<Date, Integer> groups = new HashMap<>();
        List<Dragon> collection = collectionManager.getCollection();
        for (Dragon dragon : collection) {
            Date date = dragon.getCreationDate();
            if (!groups.containsKey(date))
                groups.put(date, 0);
            groups.replace(date, groups.get(date) + 1);
        }
        return ResponseFactory.createResponse(groups.toString());
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                        "group_counting_by_creation_date",
                        " - splits collection to groups with the same creation date, then prints amount of them"
        );
    }
}
