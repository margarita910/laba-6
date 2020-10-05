package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 Команда, отсортировывающая коллекцию в порядке, обратном данному.
 */

public class Command_Reorder extends AbstractCommand implements Command{
    private String commandName;

    public Command_Reorder(){
    }

    @Override
    public ArrayList<String> execute(){
        return Receiver.reorder(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "Reorder";
    }

    @Override
    public String toString(){
        return "Reorder: отсортировывает коллекцию в порядке, обратном нынешнему.";
    }
}
