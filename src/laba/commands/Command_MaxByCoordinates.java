package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 * Команда, которая выводит элемент коллекции, значение поля coordinates которого является максимальным.
 */

public class Command_MaxByCoordinates extends AbstractCommand implements Command {
    private String commandName;

    public Command_MaxByCoordinates(){
    }

    @Override
    public ArrayList<String> execute(){
        return Receiver.maxByCoordinates(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "MaxByCoordinates";
    }

    @Override
    public String toString(){
        return "MaxByCoordinates: выводит элемент коллекции, значение поля coordinates которого является максимальным.";
    }
}
