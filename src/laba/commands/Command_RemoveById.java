package laba.commands;

import laba.com.company.CommandExecutionException;
import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 Команда, которая удаляет элемент по его Id.
 */

public class Command_RemoveById extends AbstractCommand implements Command{
    private String commandName;
    int value;
    public Command_RemoveById(int value){
        this.value = value;
    }

    public Command_RemoveById(){
    }

    @Override
    public ArrayList<String> execute() throws CommandExecutionException {
        return Receiver.removeById(AbstractCommand.getCollection(), this.value);
    }

    @Override
    public String getName(){
        return commandName = "RemoveById <int Id>";
    }

    @Override
    public String toString(){
        return "RemoveById <int Id>: удаляет элемент из коллекции по его Id.";
    }
}
