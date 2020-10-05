package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 Команда, которая очищает коллекцию.
 */

public class Command_Clear extends AbstractCommand implements Command{
    private String commandName;

    public Command_Clear(){
    }

    @Override
    public ArrayList<String> execute(){
       return Receiver.clear(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "Clear";
    }

    @Override
    public String toString(){
        return "Clear: очищает коллекцию.";
    }
}
