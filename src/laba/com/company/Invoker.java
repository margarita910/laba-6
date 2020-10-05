package laba.com.company;

import com.google.gson.JsonIOException;
import laba.commands.Command;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 A class that starts any command.
 */
public class Invoker implements Serializable {
    private Command command;

    public Invoker(){
    }

    public void setCommand(Command command){
        this.command = command;
        //this.command.add(this.command);
    }

    public ArrayList<String> executeCommand() throws IOException, IllegalArgumentException, NullPointerException, InvalidScriptException, CommandExecutionException, JSONParserException, JsonIOException, InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return command.execute();
    }

    public Command getCommand(){
        return command;
    }
}

