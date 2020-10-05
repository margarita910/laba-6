package Server;

import laba.com.company.Reader;
import laba.commands.*;
import laba.com.company.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private Vector<Ticket> collection = new Vector<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Server(){
    }

    public void startServer(String path, int port) throws InvalidScriptException{
        while (true){
            try{
                Reader read = new Reader(path);
                collection = read.readFile();
                System.out.println(ANSI_BLUE+"Данные загружены из файла."+ANSI_RESET);
                break;
            }
            catch (Exception exp){
                System.out.println(ANSI_RED+"Не удалось открыть файл\nВведите название файла"+ANSI_RESET);
                path = new Scanner(System.in).nextLine();
            }
        }

        System.out.printf(ANSI_RED +"Адрес: " + ANSI_RESET+"%s ", "127.0.0.1");
        System.out.println(ANSI_RED +"Порт: "+ ANSI_RESET+ port);
        System.out.println("Доступна специальная команда "+ANSI_GREEN+ "Save,"+ANSI_RESET+ " сохраняющая все изменения в файл.");
        System.out.println("Для вызова данной команды введите "+ANSI_GREEN+"<save>."+ANSI_RESET);

        try (Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open().bind(new InetSocketAddress(port), 7)
        ) {
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                console();
                selector.selectNow();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        SocketChannel channel = serverChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println(ANSI_RED+"Принято новое соединение."+ANSI_RESET);
                    }

                    if (key.isReadable()) {
                        if(key.channel() instanceof SocketChannel) {
                            Object receive = receiveData(((SocketChannel) key.channel()));
                            Command command = ((Command) receive);
                            assert command != null;
                            System.out.println(ANSI_YELLOW+"Принятая команда: "+ANSI_RESET+ command.getName());
                            if (command instanceof Command_Exit){
                                System.out.println(ANSI_RED+"Клиент завершил работу."+ANSI_RESET);
                                Command_Save command_save = new Command_Save();
                                command_save.execute();
                                System.out.println(ANSI_BLUE+"Изменения сохранены."+ANSI_RESET);
                                key.channel().close();
                            }
                            else{
                                Reader read = new Reader(path);
                                collection = read.readFile();
                                ArrayList<String> message = executeCommand(command);
                                System.out.println(ANSI_GREEN+"Отправляем результат выполнения ...... "+ANSI_RESET);
                                sendData(message,((SocketChannel) key.channel()));
                                System.out.println(ANSI_YELLOW+"Выполнена команда: "+ANSI_RESET + command.getName());
                                Command_Save command_save = new Command_Save();
                                command_save.execute();
                            }
                        }else{
                            InputStream stream = Channels.newInputStream((ReadableByteChannel) key.channel());
                            DataInputStream inputStream = new DataInputStream(stream);
                            System.out.println(inputStream.readUTF());
                        }
                    }

                    iterator.remove();
                }

            }
        }
        catch(BindException e){
            System.out.println(ANSI_RED+"Порт уже используется!"+ANSI_RESET);
        }
        catch (IOException e) {
            System.out.println(ANSI_RED+"Клиент разорвал соединение."+ANSI_RESET);
            startServer(path, port);
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            e.printStackTrace();
        }
        catch (CommandExecutionException e){
            System.out.println(ANSI_RED+"Произошла ошибка выполнения команды"+ANSI_RESET);
        }
        catch (NullPointerException e){
            System.out.println(ANSI_RED+"Произошла ошибка выполнения команды."+ANSI_RESET);
            startServer(path, port);
        }
    }

    private void sendData(Object obj, SocketChannel channel) throws IOException{
        if (channel == null){
            System.out.println(ANSI_RED+"Соединене не создано."+ANSI_RESET);
            return;
        }
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteArray);
        stream.writeObject(obj);
        channel.write(ByteBuffer.wrap(byteArray.toByteArray()));
    }

    private Object receiveData(SocketChannel channel) throws IOException{
        if (channel == null){
            System.out.println(ANSI_RED+"Соединене не создано"+ANSI_RESET);
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(10000);
        channel.read(buffer);
        try {
            ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return objStream.readObject();
        }catch(Exception e){
            return null;
        }
    }

    private static ArrayList<String> executeCommand(Command command) throws IOException, InvalidScriptException, CommandExecutionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        return invoker.executeCommand();
    }

    private void console(){
        try {
            if (!reader.ready()) return;
            if (reader.lines().anyMatch(one -> one.toUpperCase().equals("SAVE"))) {
                Command save = new Command_Save();
                System.out.println(ANSI_GREEN+"Была вызвана серверная команда Save."+ANSI_RESET);
                Invoker invoker = new Invoker();
                invoker.setCommand(save);
                invoker.executeCommand();
                System.out.println(ANSI_BLUE+"Изменения сохранены."+ANSI_RESET);
                reader = new BufferedReader(new InputStreamReader(System.in));
            }
            else System.out.println(ANSI_RED+"Неверный ввод!"+ANSI_RESET);
        }
        catch (IOException e) {
            return;
        }
        catch (InvalidScriptException e){
            System.out.println(ANSI_RED+"Ошибка скрипта"+ANSI_RESET);
        }
        catch (CommandExecutionException e){
            System.out.println(ANSI_RED+"Ошибка исполнения команды."+ANSI_RESET);
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}