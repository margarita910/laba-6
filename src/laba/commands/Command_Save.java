package laba.commands;

import laba.com.company.CommandExecutionException;
import laba.com.company.Receiver;

import java.io.IOException;
import java.util.ArrayList;

/**
 Команда, которая сохраняет коллекцию в файл.
 */

public class Command_Save extends AbstractCommand implements Command{
    private String commandName;
    String Path;
    public Command_Save(){
    }

    public Command_Save(String Path){
        this.Path = Path;
    }

    @Override
    public ArrayList<String> execute() throws IOException, CommandExecutionException {
        try {
            Receiver.save(AbstractCommand.getCollection());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName(){
        return commandName = "Save";
    }

    public String getPath() {
        return Path;
    }

    @Override
    public String toString(){
        return "Save: сохраняет коллекцию в файл.";
    }
}
