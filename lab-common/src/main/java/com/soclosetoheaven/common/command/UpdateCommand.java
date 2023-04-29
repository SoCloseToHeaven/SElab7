package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.RequestFactory;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.*;


public class UpdateCommand extends AbstractCommand{

    private final DragonCollectionManager cm;

    private final BasicIO io;

    private final UserManager um;

    public UpdateCommand(DragonCollectionManager cm, BasicIO io, UserManager um) {
        super("update");
        this.cm = cm;
        this.io = io;
        this.um = um;
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (
                !(requestBody instanceof RequestBodyWithDragon) ||
                args.length < 1 ||
                !args[0].chars().allMatch(Character::isDigit)
        )
           throw new InvalidRequestException("Unable to update element");

        int id = Integer.parseInt(args[0]);

        Dragon dragon = cm.getByID(id);
        if (dragon == null)
            throw new InvalidRequestException("No dragon with such ID");
        User user = um.getUserByAuthCredentials(requestBody.getAuthCredentials());
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (!cm.update(((RequestBodyWithDragon) requestBody).getDragon(), id))
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponse("Successfully updated!");
    }

    @Override
    public Request toRequest(String[] args) {
        if (args.length > 0 && args[0].chars().allMatch(Character::isDigit))
            return RequestFactory.createRequestWithDragon(getName(), args, io);
        throw new InvalidCommandArgumentException();
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "update {ID} {element}",
                " - updates data(in the same way as in {add} command) with the same ID"
        );
    }
}
