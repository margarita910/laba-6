package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 Команда, выводящая все элементы коллекции
 */

public class Command_Show extends AbstractCommand implements Command{
    private String commandName;
    public Command_Show(){
    }

    @Override
    public ArrayList<String> execute(){
        return Receiver.show(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "Show";
    }

    @Override
    public String toString(){
        return "Show: выводит все элементы коллекции";
    }
}