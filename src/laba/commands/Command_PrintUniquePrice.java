package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

public class Command_PrintUniquePrice extends AbstractCommand implements Command {
    private String commandName;
    float price;
    public Command_PrintUniquePrice(float price){
        this.price = price;
    }

    public Command_PrintUniquePrice(){
    }

    @Override
    public ArrayList<String > execute(){
       return Receiver.printUniquePrice(AbstractCommand.getCollection(), this.price);
    }

    @Override
    public String getName(){
        return commandName = "PrintUniquePrice <float price>";
    }

    @Override
    public String toString(){
        return "PrintUniquePrice <float price>: выводит уникальные значения поля price всех элементов коллекции.";
    }
}
