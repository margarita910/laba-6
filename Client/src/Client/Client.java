package Client;

import laba.com.company.Reader;
import laba.commands.Command;
import laba.commands.Command_Exit;
import laba.commands.Command_Save;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private String address = null;
    private int port = -1;
    private InputStream input= null;
    private OutputStream output= null;

    public void startClient(String address, int port){
        this.address = address;
        this.port = port;
        Scanner scanner = new Scanner(System.in);
        System.out.printf(ANSI_BLUE+"Подключение по адресу "+ANSI_RESET+ANSI_RED+"%s "+ANSI_RESET+ANSI_BLUE+"и порту "+ANSI_RESET+ANSI_RED+"%d "+ANSI_RESET+ANSI_BLUE+".......\n"+ANSI_RESET, address, port);
        try (Socket socket = new Socket(address,port);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream()){
            this.input = input;
            this.output = output;
            System.out.println(ANSI_GREEN+"Подключение успешно завершено."+ANSI_RESET);
            while (socket.isConnected()){
                Reader reader = new Reader();
                Command command = reader.readCommandFromConsole();
                if (command instanceof Command_Exit){
                    sendData(command);
                    Command_Exit exit = new Command_Exit();
                    exit.execute();
                }
                if (command instanceof Command_Save){
                    System.out.println(ANSI_RED+"Данная команда вам не доступна!"+ANSI_RESET);
                    continue;
                }
                if (command == null){
                    System.out.println(ANSI_RED+"Команда не найдена!"+ANSI_RESET);
                    continue;
                }
                showCommand(command);
            }
        }
        catch (Exception e){
            problem(e);
        }
    }

    private void sendData(Object object) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objOutput = new ObjectOutputStream(byteStream);
        objOutput.writeObject(object);
        output.write(byteStream.toByteArray());
    }

    private void receiveData() throws IOException, ClassNotFoundException{
        ObjectInputStream inputStream = new ObjectInputStream(input);
        ArrayList<String> receive = (ArrayList<String>)inputStream.readObject();
        for (String string : receive){
            System.out.println(string);
        }
        //return (ArrayList<String>) inputStream.readObject();
    }

    private void showCommand(Command command) throws IOException, ClassNotFoundException {
        sendData(command);
        receiveData();
        //ArrayList<String> receive = receiveData();
        //System.out.println(receive);
    }

    private void problem(Exception e){
        if (e instanceof ClassNotFoundException){
            System.out.println(ANSI_RED+"Данная команда не обнаружена на сервере!"+ANSI_RESET);
        }
        else if (e instanceof IOException){
            System.out.println(ANSI_RED+"Произошла ошибка подключения!"+ANSI_RESET);
            System.out.println(ANSI_BLUE+"Выберите действие:\n"+ANSI_RESET+ ANSI_YELLOW+"1. Повторить соединение\n2. Изменить адрес и порт\n3. Завершить сессию."+ANSI_RESET);
            int answer;
            while(true) {
                try {
                    answer = Integer.parseInt(new Scanner(System.in).nextLine());
                    break;
                } catch (Exception ex){
                    System.out.println(ANSI_RED+"Введите 1, 2 или 3."+ANSI_RESET);
                }
            }
            if (answer == 1) startClient(address, port);
            else if (answer == 2){
                restart();
            }
            else if (answer == 3){
                System.out.println(ANSI_BLUE+"Завершение сессии."+ANSI_RESET);
                System.exit(0);
            }
            else{
                System.out.println(ANSI_RED+"Такого варианта нет."+ANSI_RESET);
                problem(e);
            }
        }
        else if (e instanceof ConnectException){
            System.out.println(ANSI_RED+"Сервер временно не доступен. Пробуем подключиться еще раз."+ANSI_RESET);
            restart();
        }
            else {
                System.out.println(ANSI_RED+"Неизвестная ошибка."+ANSI_RESET);
                e.printStackTrace();
            }
    }

    private void restart(){
        Scanner scanner = new Scanner(System.in);
        int port; String address;

        while (true) {
            try {
                System.out.println(ANSI_GREEN+"Введите адрес: "+ANSI_RESET);
                address = scanner.nextLine();
                break;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (true){
            try {
                System.out.println(ANSI_GREEN+"Введите номер порта: "+ANSI_RESET);
                port = Integer.parseInt(scanner.nextLine());
                break;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        startClient(address, port);
    }
}
