package laba.com.company;

import laba.commands.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

/**
 * Main class.
 */

public class Main {
    public static void main (String[] args) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException, InvocationTargetException, NullPointerException{
        Reader reader = new Reader("file.json");
        reader.readFile();
        Invoker invoker = new Invoker();
        while (true){
            try {
                invoker.setCommand(Reader.readCommandFromConsole());
            }
            catch (IllegalArgumentException e){
                System.out.println("Вы ввели неправильные аргументы комманды. Повторите ввод.");
                continue;
            }
            try {
                invoker.executeCommand();
            }
            catch (CommandExecutionException | InvalidScriptException e){
                System.out.println("Ошибка в чтении скрипта.");
            }
        }
    }
    /*public static void main (String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NullPointerException, InvalidScriptException{
        AbstractCommand.addNewCommand(new Command_Help(), new Command_Info(), new Command_Show(),
                new Command_Add(), new Command_UpdateId(), new Command_RemoveById(), new Command_Clear(),
                new Command_Save(), new Command_ExecuteScriptFile(), new Command_Exit(), new Command_RemoveAtIndex(),
                new Command_AddIfMax(), new Command_Reorder(), new Command_MaxByCoordinates(),
                new Command_PrintUniquePrice(), new Command_PrintDescendingPrice());
        Reader reader = new Reader(System.getenv("File_Path"));
        Vector<Ticket> collection;
        collection = reader.readFile();
        AbstractCommand.setCollection(collection);
        Invoker invoker = new Invoker();
        while (true){
            try {
                invoker.setCommand(reader.readCommandFromConsole());
            }
            catch (IllegalArgumentException e){
                System.out.println("Вы ввели неправильные аргументы комманды. Повторите ввод.");
                continue;
            }
            try {
                invoker.executeCommand();
            }
            catch (InvalidScriptException | CommandExecutionException e){
                System.out.println("Ошибка в чтении скрипта.");
            }
        }
    }*/
}
