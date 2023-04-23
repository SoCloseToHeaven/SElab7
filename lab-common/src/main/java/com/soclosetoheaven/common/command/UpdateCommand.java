package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.businesslogic.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.factorн.RequestFactory;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.*;

import java.util.NoSuchElementException;

public class UpdateCommand extends AbstractCommand{

    private final FileCollectionManager cm;

    private final BasicIO io;

    public UpdateCommand(FileCollectionManager cm, BasicIO io) {
        super("update");
        this.cm = cm;
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        long id = Long.parseLong(requestBody.getArgs()[0]);
        try {
            cm.removeByID(id);
            Dragon element = ((RequestBodyWithDragon) requestBody).getDragon();
            element.setId(id);
            cm.add(element);
            return new Response("Replaced!");
        } catch (NoSuchElementException |
                 ClassCastException |
                 InvalidFieldValueException e) {
            return ResponseFactory.createResponseWithException(
                    new InvalidRequestException(e.getMessage())
            );
        }
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
