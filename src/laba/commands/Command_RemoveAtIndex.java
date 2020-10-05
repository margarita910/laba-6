package laba.commands;

import laba.com.company.Receiver;

import java.io.IOException;
import java.util.ArrayList;

/**
 Команда, которая удаляет элемент, находящийся в заданной позиции.
 */

public class Command_RemoveAtIndex extends AbstractCommand implements Command {
    private String commandName;

    int index;

    public Command_RemoveAtIndex(int index){
        this.index = index;
    }

    public Command_RemoveAtIndex(){
    }

    @Override
    public ArrayList<String> execute() throws IOException {
       return Receiver.removeAtIndex(AbstractCommand.getCollection(), this.index);
    }

    @Override
    public String getName(){
        return commandName = "RemoveAtIndex <int index>";
    }

    @Override
    public String toString(){
        return "RemoveAtIndex <int index>: удаляет элемент, находящийся в заданной позиции.";
    }
}

