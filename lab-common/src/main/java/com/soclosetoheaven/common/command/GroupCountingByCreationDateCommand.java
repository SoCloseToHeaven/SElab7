package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.businesslogic.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GroupCountingByCreationDateCommand extends AbstractCommand{

    private final FileCollectionManager cm;

    public GroupCountingByCreationDateCommand(FileCollectionManager cm) {
        super("group_counting_by_creation_date");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        HashMap<Date, Integer> groups = new HashMap<>();
        ArrayList<Dragon> collection = cm.getCollection();
        for (Dragon dragon : collection) {
            Date date = dragon.getDate();
            if (!groups.containsKey(date))
                groups.put(date, 0);
            groups.replace(date, groups.get(date) + 1);
        }
        return ResponseFactory.createResponse(groups.toString());
    }

    @Override
    public Request toRequest(String[] args) {
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
