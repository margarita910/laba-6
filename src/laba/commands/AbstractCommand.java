package laba.commands;

import laba.com.company.Ticket;

import java.util.Arrays;
import java.util.Vector;
import java.util.ArrayList;

public abstract class AbstractCommand implements Command {
    private static Vector<Ticket> collection;

    public static void setCollection (Vector<Ticket> collection){
        AbstractCommand.collection = collection;
    }

    public static Vector<Ticket> getCollection(){
        return collection;
    }

    private static ArrayList<AbstractCommand> commands = new ArrayList<>();

    public static ArrayList<AbstractCommand> getCommands(){
        return commands;
    }

    public static void addNewCommand (AbstractCommand... commandsList){
        commands.addAll(Arrays.asList(commandsList));
    }
}
