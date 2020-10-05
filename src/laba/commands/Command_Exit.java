package laba.commands;

import laba.com.company.Receiver;

import java.util.ArrayList;

/**
 Команда, осуществляющая выход из программы.
 */

public class Command_Exit extends AbstractCommand implements Command{
    private String commandName;

    public Command_Exit(){
    }

    @Override
    public ArrayList<String > execute(){
        return Receiver.exit();
    }

    @Override
    public String getName(){
        return commandName = "Exit";
    }

    @Override
    public String toString(){
        return "Exit: осуществляет выход из программы без сохранения изменений.";
    }
}
