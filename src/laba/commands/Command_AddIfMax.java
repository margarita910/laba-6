package laba.commands;

import laba.com.company.Receiver;
import laba.com.company.Ticket;

import java.util.ArrayList;

/**
 Команда, которая добавляет элемент в коллекцию, если его значение больше максимального.
 */

public class Command_AddIfMax extends AbstractCommand implements Command{
    private String commandName;

    private Ticket ticket;

    public Command_AddIfMax(Ticket ticket){
        this.ticket = ticket;
    }

    public Command_AddIfMax(){
    }

    @Override
    public ArrayList<String> execute(){
       return Receiver.addIfMax(AbstractCommand.getCollection(), this.ticket);
    }

    @Override
    public String getName(){
        return commandName = "AddIfMax <ticket>";
    }

    @Override
    public String toString(){
        return "AddIfMax: добавляет элемент в коллекцию, если значение его поля coordinates (или значение поля price) больше максимального.";
    }
}
