package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

public class Command_PrintDescendingPrice extends AbstractCommand implements Command {
    private String commandName;

    public Command_PrintDescendingPrice(){
    }

    @Override
    public ArrayList<String > execute(){
        return Receiver.printFieldDescendingPrice(AbstractCommand.getCollection());
    }

    @Override
    public String getName(){
        return commandName = "PrintDescendingPrice";
    }

    @Override
    public String toString(){
        return "PrintDescendingPrice: выводит знаения поля price всех элементов в порядке убывания.";
    }
}
