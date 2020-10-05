package laba.com.company;

import com.google.gson.JsonIOException;
import laba.commands.AbstractCommand;
import laba.commands.Command;
import laba.commands.Command_ExecuteScriptFile;
import laba.commands.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.google.gson.Gson;

import static java.util.Collections.*;

/**
 * Receiver.
 */

public class Receiver implements Serializable{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    static ArrayList<String> scriptNames = new ArrayList<>();
    public static int countExecute;

    public static ArrayList<String> getScriptNames(){
        return scriptNames;
    }

    public static ArrayList<String> help(){
        ArrayList<String> youCanUse = new ArrayList<>();
        youCanUse.add("Доступны следующие команды:");
        youCanUse.add(ANSI_YELLOW+"Add:"+ANSI_RESET+" добавляет элемент класса Ticket в коллекцию.");
        youCanUse.add(ANSI_YELLOW+"AddIfMax:"+ANSI_RESET+" добавляет элемент в коллекцию, если значение его поля coordinates (или значение поля price) больше максимального.");
        youCanUse.add(ANSI_YELLOW+"Clear:"+ANSI_RESET+" очищает коллекцию.");
        youCanUse.add(ANSI_YELLOW+"ExecuteScriptFile <file_name>:"+ANSI_RESET+" исполняет скрипт.");
        youCanUse.add(ANSI_YELLOW+"Exit:"+ANSI_RESET+" осуществляет выход из программы без сохранения изменений.");
        youCanUse.add(ANSI_YELLOW+"Help:"+ANSI_RESET+" показывает все доступные комманды. ");
        youCanUse.add(ANSI_YELLOW+"Info:"+ANSI_RESET+" выводит информацию о коллекции.");
        youCanUse.add(ANSI_YELLOW+"MaxByCoordinates:"+ANSI_RESET+" выводит элемент коллекции, значение поля coordinates которого является максимальным.");
        youCanUse.add(ANSI_YELLOW+"PrintDescendingPrice:"+ANSI_RESET+" выводит знаения поля price всех элементов в порядке убывания.");
        youCanUse.add(ANSI_YELLOW+"PrintUniquePrice <float price>:"+ANSI_RESET+" выводит уникальные значения поля price всех элементов коллекции.");
        youCanUse.add(ANSI_YELLOW+"RemoveAtIndex <int index>:"+ANSI_RESET+" удаляет элемент, находящийся в заданной позиции.");
        youCanUse.add(ANSI_YELLOW+"RemoveById <int Id>:"+ANSI_RESET+" удаляет элемент из коллекции по его Id.");
        youCanUse.add(ANSI_YELLOW+"Reorder:"+ANSI_RESET+" отсортировывает коллекцию в порядке, обратном нынешнему.");
        youCanUse.add(ANSI_YELLOW+"Show:"+ANSI_RESET+" выводит все элементы коллекции.");
        youCanUse.add(ANSI_YELLOW+"UpdateId <int Id>:"+ANSI_RESET+" обновляет значение элемента коллекции, ID которого равно заданному.");
        return youCanUse;
    }

    public static ArrayList<String> info(Vector<Ticket> collection){
        ArrayList<String> information = new ArrayList<>();
        information.add(ANSI_YELLOW+"Коллекция: "+ANSI_RESET+collection.getClass());
        information.add(ANSI_YELLOW+"Колличество элементов: "+ANSI_RESET+collection.size());
        if (collection.size() > 0 ) {
            information.add(ANSI_YELLOW+"Тип элементов: "+ANSI_RESET+collection.get(0).getClass());
            information.add(ANSI_YELLOW+"Коллекция создана: "+ANSI_RESET+collection.get(0).getCreationDate());
        }
        else information.add("Вы всегда можете добавить новые элементы в коллекцию =)");
        return information;
    }

    public static ArrayList<String> show (Vector<Ticket> collection) throws NullPointerException{
        ArrayList<String> showElement = new ArrayList<>();
        if (collection.size() > 0){
            for (Ticket ticket : collection){
                showElement.add(ticket.toString());
            }
        }
        else{
            showElement.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        }
        return showElement;
    }

    public static ArrayList<String> add(Vector<Ticket> collection, Ticket ticket){
        ArrayList<String> addElement = new ArrayList<>();
        ticket.setId(collection.size()+1);
        collection.add(ticket);
        addElement.add(ANSI_GREEN+"Ticket добавлен в коллекцию."+ANSI_RESET);
        return addElement;
    }

    public static boolean detect (int value, Vector<Ticket> collection) throws IllegalArgumentException{
        if (value < 0 ) throw new IllegalArgumentException();
        if (collection.size()>0){
            for (Ticket ticket : collection) {
                if (ticket.getId() == value) {
                    return true;
                }
                else return false;
            }
        }
        else System.out.println("Коллекция не имеет элементов.");
        throw new IllegalArgumentException();
    }

    public static ArrayList<String> update(Vector<Ticket> collection, int value, Ticket ticket){
        boolean update = false;
        ArrayList<String> updateTicket = new ArrayList<>();
        if (value < 0 ) throw new IllegalArgumentException();
        if (collection.size() > 0){
            for (Ticket ticket1 : collection){
                if (ticket1.getId() == value){
                    ticket.setId(value);
                    collection.set(value - 1, ticket);
                    updateTicket.add(ANSI_GREEN+"Элемент успешно обновлен."+ANSI_RESET);
                    update = true;
                }
            }
            if (update == false){
                updateTicket.add(ANSI_RED+"Элемента с таким Id не существует."+ANSI_RESET);
            }
        }
        else updateTicket.add(ANSI_RED+"Коллекция не имеет элементов. Заменить ticket невозможно."+ANSI_RESET);
        return updateTicket;
    }

    public static ArrayList<String> removeById(Vector<Ticket> collection, int value){
        int b = 1;
        ArrayList<String> removeId = new ArrayList<>();
        for (int i = 0; i<collection.size(); i++){
            b++;
            if (Objects.equals(Integer.valueOf(collection.get(i).getId()),value)){
                collection.remove(i);
                removeId.add(ANSI_GREEN+"Элемент успешно удален."+ANSI_RESET);
                b=0;
            }
        }
        if (b>collection.size()){
                removeId.add(ANSI_RED+"Элемента с таким Id не существует."+ANSI_RESET);
        }
        return removeId;
    }

    public static ArrayList<String> removeAtIndex(Vector<Ticket> collection, int index){
        ArrayList<String> removeIndex = new ArrayList<>();
        if (collection.size() > 0){
            if (index < collection.size()){
                collection.remove(index);
                removeIndex.add(ANSI_GREEN+"Элемент успешно удален."+ANSI_RESET);
            }
            else removeIndex.add(ANSI_RED+"Элемента с таким индексом не существует."+ANSI_RESET);
        }
        else removeIndex.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return removeIndex;
    }

    public static ArrayList<String> clear(Vector<Ticket> collection){
        ArrayList<String> clearCollection = new ArrayList<>();
        if (collection.size() > 0){
            collection.clear();
            clearCollection.add(ANSI_GREEN+"Коллекция очищена."+ANSI_RESET);
        }
        else clearCollection.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return clearCollection;
    }

    public static void save(Vector<Ticket> collection) throws IOException {
        Gson gson = new Gson();
        FileOutputStream file;
        String json = gson.toJson(collection);
        try {
            file = new FileOutputStream(System.getenv("File_Path"));
            file.write(json.getBytes());
        }
        catch (FileNotFoundException exp){
            System.out.println(ANSI_RED+"Файл не найден."+ANSI_RESET);
            return;
        }
        file.close();
        //System.out.println(ANSI_BLUE+"Изменения сохранены."+ANSI_RESET);
    }

    public static ArrayList<String> exit(){
        ArrayList<String> exitMessage = new ArrayList<>();
        exitMessage.add("Программа завершена.");
        System.exit(0);
        return exitMessage;
    }

    public static ArrayList<String> reorder (Vector<Ticket> collection) {
        ArrayList<String> reorderCollection = new ArrayList<>();
        if (collection.size()>0){
            reverse(collection);
            reorderCollection.add(ANSI_GREEN+"Коллекция отсортирована в обратном порядке."+ANSI_RESET);
        }
        else reorderCollection.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return reorderCollection;
    }

    public static ArrayList<String> addIfMax(Vector<Ticket> collection, Ticket ticket){
        ArrayList<String> addIfMaxCommandMessage = new ArrayList<>();
        class AddIfMaxCoordinates implements Comparator<Ticket>{
            @Override
            public int compare (Ticket ticket1, Ticket ticket2){
                int result = 2;
                if (!(ticket1.getCoordinates().getX() == null || ticket2.getCoordinates().getX() == null)){
                    if (ticket1.getCoordinates().getX() > ticket2.getCoordinates().getX()) result = 1;
                    else if (ticket1.getCoordinates().getX() < ticket2.getCoordinates().getX()) result = -1;
                    else if (ticket1.getCoordinates().getX().equals(ticket2.getCoordinates().getX())){
                        if (ticket1.getCoordinates().getY() > ticket2.getCoordinates().getY()) result = 1;
                        else if (ticket1.getCoordinates().getY() < ticket2.getCoordinates().getY()) result = -1;
                        else if (ticket1.getCoordinates().getY() == ticket2.getCoordinates().getY()) result = 0;
                    }
                }
                return result;
            }
        }

        class AddIfMaxPrice implements Comparator<Ticket>{
            @Override
            public int compare(Ticket ticket1, Ticket ticket2){
                int result = 2;
                if (!(ticket1.getPrice() < 0 || ticket2.getPrice() < 0)){
                    if (ticket1.getPrice() > ticket2.getPrice()) result = 1;
                    else if (ticket1.getPrice() < ticket2.getPrice()) result = -1;
                    else if (ticket1.getPrice().equals(ticket2.getPrice())) result = 0;
                }
                return result;
            }
        }

        Ticket maxByCoordinates = max(collection, new AddIfMaxCoordinates());
        Ticket maxByPrice = max(collection, new AddIfMaxPrice());
        if (collection.size() > 0){
            if (maxByCoordinates.compareTo(ticket) < 0 ) {
                addIfMaxCommandMessage.add("Данный Ticket является максимальным по значению поля coordinates.");
                ticket.setId(collection.size()+1);
                collection.add(ticket);
                addIfMaxCommandMessage.add(ANSI_GREEN+"Ticket добавлен в коллекцию."+ANSI_RESET);
            }
            else if (maxByPrice.getPrice() < ticket.getPrice()) {
                addIfMaxCommandMessage.add("Данный Ticket является максимальным по значению поля price.");
                ticket.setId(collection.size()+1);
                collection.add(ticket);
                addIfMaxCommandMessage.add(ANSI_GREEN+"Ticket добавлен в коллекцию."+ANSI_RESET);
            }
            else if ((maxByCoordinates.compareTo(ticket) > -1) && (!(maxByPrice.getPrice() < ticket.getPrice())))
                addIfMaxCommandMessage.add("Данный Ticket не является максимальным ни по значению поля coordinates, ни по значению поля price."+ ANSI_RED + " Ticket не добавлен." + ANSI_RESET);
        }
        else {
            addIfMaxCommandMessage.add("Коллекция пуста. Данный ticket является максимальным по всем параметрам.");
            ticket.setId(collection.size()+1);
            collection.add(ticket);
            addIfMaxCommandMessage.add(ANSI_GREEN+"Ticket добавлен в коллекцию."+ANSI_RESET);
        }
        return addIfMaxCommandMessage;
    }

    public static ArrayList<String> maxByCoordinates(Vector<Ticket> collection) {
        ArrayList<String> maxCoordinates = new ArrayList<>();
        if (collection.size() > 0){
            class FoundMaxCoordinates implements Comparator<Ticket>{
                @Override
                public int compare (Ticket ticket1, Ticket ticket2){
                    int result = 0;
                    if (!(ticket1.getCoordinates().getX() == null || ticket2.getCoordinates().getX() == null )){
                        result = ticket1.getCoordinates().compareTo(ticket2.getCoordinates());
                    }
                    return result;
                }
            }
            maxCoordinates.add(max(collection, new FoundMaxCoordinates()).toString());
        }
        else maxCoordinates.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return maxCoordinates;
    }

    public static ArrayList<String> printUniquePrice(Vector<Ticket> collection, float price){
        ArrayList<String> uniquePrice = new ArrayList<>();
        if (collection.size() > 0){
            for (Ticket ticket : collection){
                if(ticket.getPrice() != null){
                    if (!(ticket.getPrice() == price)){
                        uniquePrice.add(ticket.getPrice().toString()+"\n");
                    }
                }
                else uniquePrice.add(ANSI_RED+"Билет с данным ID не содержит значения price."+ANSI_RESET);
            }
        }
        else uniquePrice.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return uniquePrice;
    }


    public static ArrayList<String> printFieldDescendingPrice(Vector<Ticket> collection){
        ArrayList<String> descending = new ArrayList<>();
        if (collection.size() > 0){
            class SortByPrice implements Comparator<Ticket>{
                @Override
                public int compare(Ticket ticket1, Ticket ticket2){
                    int result = 0;
                    if (!(ticket1.getPrice() < 0 || ticket2.getPrice() < 0)){
                        result = ticket1.getPrice().compareTo(ticket2.getPrice());
                    }
                    return result;
                }
            }
            Comparator comparator = Collections.reverseOrder(new SortByPrice());
            sort(collection, comparator);
            for (Ticket ticket : collection) {
                descending.add(ticket.getPrice().toString());
            }
        }
        else descending.add(ANSI_RED+"Коллекция не имеет элементов."+ANSI_RESET);
        return descending;
    }

    /**
     * The method that reads the script.
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalArgumentException
     * @throws InvalidScriptException
     * @throws ClassNotFoundException
     */

    public static ArrayList<String> readCommandFromFile(String Path) throws IOException, IllegalAccessException, InvocationTargetException, InstantiationException, IllegalArgumentException, InvalidScriptException, ClassNotFoundException, CommandExecutionException {
        ArrayList<String> message = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(Path));
            scriptNames.add(Path);
        }
        catch (IOException e){
            message.add(ANSI_RED+"Ошибка в пути к файлу."+ANSI_RESET);
        }

        Scanner scanner;
        String line;
        String data;
        Class myObject;
        Command command;
        String name = null;
        while ((line = bufferedReader.readLine()) != null) {
            command = null;
            message.add("");
            message.add(ANSI_YELLOW+"data: "+ANSI_RESET + line);
            scanner = new Scanner(line);
            while (scanner.hasNext()) {
                while (true) {
                    try {
                        data = scanner.nextLine() + " ";
                        name = data.substring(0, data.indexOf(" "));
                        myObject = Class.forName("laba.commands.Command_" + name);
                        break;
                    } catch (ClassNotFoundException e) {
                        message.add("Несуществующая команда " +ANSI_RED+ name + ANSI_RESET + ". Исправьте скрипт.");
                    }
                }
                data = data.trim();
                Constructor[] constructors = myObject.getConstructors();
                Class[] parameterTypes = constructors[0].getParameterTypes();
                try {
                    if (parameterTypes.length == 0) {
                        command = (Command) constructors[0].newInstance();
                        message.add("Полученная из скрипта команда: " + ANSI_YELLOW+ command.getName()+ANSI_RESET);
                    }
                    else if (parameterTypes.length == 1) {
                        if (parameterTypes[0].toString().compareTo("int") == 0) {
                            command = (Command) constructors[0].newInstance(Integer.parseInt(data.substring(data.lastIndexOf(" ") + 1)));
                            message.add("Полученная из скрипта команда: " +ANSI_YELLOW+ command.getName()+ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class laba.com.company.Ticket") == 0) {
                            command = (Command) constructors[0].newInstance(createTicket(bufferedReader));
                            message.add("Полученная из скрипта команда: " + ANSI_YELLOW+command.getName()+ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("float") == 0) {
                            command = (Command) constructors[0].newInstance(Float.parseFloat(data.substring(data.lastIndexOf(" ") + 1)));
                            message.add("Полученная из скрипта команда: " + ANSI_YELLOW+command.getName()+ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class java.lang.String") == 0) {
                            command = (Command) constructors[0].newInstance(data.substring(data.indexOf(" ") + 1));
                            message.add("Полученная из скрипта команда: " +ANSI_YELLOW+ command.getName()+ANSI_RESET);
                        }
                    }
                    else if (parameterTypes.length == 2) {
                        command = (Command) constructors[0].newInstance(Integer.parseInt(data.substring(data.indexOf(" ") + 1)), createTicket(bufferedReader));
                        message.add("Полученная из скрипта команда: " +ANSI_YELLOW+ command.getName()+ANSI_RESET);
                    }
                } catch (NumberFormatException | InputMismatchException | InvalidScriptException e) {
                    throw new InvalidScriptException();
                }
                if ((command instanceof Command_ExecuteScriptFile)){
                    Iterator iterator = scriptNames.iterator();
                    boolean check = false;
                    while (iterator.hasNext()){
                        if (parameterTypes[0].toString().equals(iterator.next())) check = true;
                    }
                    if (check){
                        message.add(ANSI_RED+"\tПопытка зациклить программу прервана."+ANSI_RESET);
                    }
                    else{
                        scriptNames.add(parameterTypes[0].toString());
                        ++countExecute;
                        message.add(ANSI_GREEN+"\tИсполняется полученный из скрипта скрипт."+ANSI_RESET);
                        ArrayList<String> strings = command.execute();
                        for (String string : strings){
                            message.add(string);
                        }
                        --countExecute;
                        message.add(ANSI_GREEN+"\tСкрипт, полученный из скрипта, исполнен."+ANSI_RESET);
                    }
                }
                else {
                    if (command != null){
                        ArrayList<String> strings = command.execute();
                        for (String string : strings){
                            message.add(string);
                        }
                    }
                }
            }
        }
        if (countExecute == 0){
            scriptNames.clear();
        }
        return message;
    }

    private static Ticket createTicket (BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        String name = null;
        float price;
        String comment;
        TicketType type;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            if (!input.isEmpty()) name = scanner.next().trim();
        }
        System.out.println("name: " + name);
        Coordinates coordinates = createCoordinates(bufferedReader);
        price = getFloat(bufferedReader);
        System.out.println("price: " + price);
        comment = getComment(bufferedReader);
        System.out.println("comment: "+ comment);
        type = createType(bufferedReader);
        System.out.println("type: "+type);
        Person person = createPerson(bufferedReader);
        return new Ticket(name, coordinates, price, comment, type, person);
    }

    private static Float getFloat(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        Float result = null;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            try{
                if (!input.isEmpty()) result = scanner.nextFloat();
            }
            catch (InputMismatchException e){
                System.out.println("Ошибка в скрипте: Неверные данные. Данная строка должна содержать дробное число (float).");
                throw new InvalidScriptException();
            }
        }
        return result;
    }

    private static String getComment(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        String result = null;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            try{
                if (!input.isEmpty()) result = scanner.next();
            }
            catch (InputMismatchException e){
                System.out.println("Ошибка в скрипте: Неверные данные. Данная строка должна содержать строку.");
                throw new InvalidScriptException();
            }
        }
        return result;
    }

    private static Coordinates createCoordinates(BufferedReader bufferedReader) throws IOException, InputMismatchException, InvalidScriptException{
        System.out.println("coordinates: ");
        Float x = null;
        double y;
        x = getFloat(bufferedReader);
        System.out.println("x: "+x);
        y = getDouble(bufferedReader);
        System.out.println("y: "+y);
        return new Coordinates(x, y);
    }

    private static Double getDouble(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        Double result = null;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) result = scanner.nextDouble();
            }
            catch (InputMismatchException e){
                System.out.println("Ошибка в скрипте: Неверные данные. Данная строка должна содержать дробное число (double).");
                throw new InvalidScriptException();
            }
        }
        return result;
    }

    private static TicketType createType(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        TicketType type = null;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) type = TicketType.valueOf(scanner.next().trim());
            }
            catch (IllegalArgumentException e){
                System.out.println("Ошибка в скрипте: Неверные данные.");
                throw new InvalidScriptException();
            }
        }
        return type;
    }

    private static Person createPerson(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        int height = getInteger(bufferedReader);
        System.out.println("height: " + height);
        Color eyeColor = createColor(bufferedReader);
        System.out.println("eyeColor: "+ eyeColor);
        Color hairColor = createColor(bufferedReader);
        System.out.println("hairColor: "+hairColor);
        Country nationality = createCountry(bufferedReader);
        System.out.println("nationality: "+nationality);
        return new Person(height, eyeColor, hairColor, nationality);
    }

    private static Integer getInteger(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        Integer result = null;
        String input = "";
        while (input.isEmpty()){
            input = bufferedReader.readLine();
            scanner = new Scanner(input);
            try{
                if (!input.isEmpty()) result = scanner.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Ошибка в скрипте: Неверные данные. Данная строка должна содержать целое число (int).");
                throw new InvalidScriptException();
            }
        }
        return result;
    }

    private static Color createColor(BufferedReader bufferedReader) throws IOException, InvalidScriptException{
        Scanner scanner;
        Color color = null;
        String string = "";
        while (string.isEmpty()){
            string = bufferedReader.readLine();
            scanner = new Scanner(string);
            try {
                if (!string.isEmpty()) color = Color.valueOf(scanner.next().trim());
            }
            catch (IllegalArgumentException e){
                System.out.println("Ошибка в скрипте: Неверные данные.");
                throw new InvalidScriptException();
            }
        }
        return color;
    }

    private static Country createCountry(BufferedReader bufferedReader) throws IOException, InvalidScriptException {
        Scanner scanner;
        Country country = null;
        String string = "";
        while (string.isEmpty()){
            string = bufferedReader.readLine();
            scanner = new Scanner(string);
            try {
                if (!string.isEmpty()) country = Country.valueOf(scanner.next().trim());
            }
            catch (IllegalArgumentException e){
                System.out.println("Ошибка в скрипте: Неверные данные.");
                throw new InvalidScriptException();
            }
        }
        return country;
    }
}

