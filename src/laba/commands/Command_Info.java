package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

public class Command_Info extends AbstractCommand implements Command{
    private String commandName;

    public Command_Info(){
    }

    @Override
    public ArrayList<String> execute(){
        return Receiver.info(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "Info";
    }

    @Override
    public String toString(){
        return "Info: выводит информацию о коллекции.";
    }
}
