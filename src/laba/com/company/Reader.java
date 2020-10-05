package laba.com.company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import laba.commands.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.*;
import java.util.*;

/**
 * The class that reads the file and laba.commands.commands from the console.
 */

public class Reader implements Serializable {
    public static String Path;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public Reader(){
    }

    public Reader(String Path){
        this.Path = Path;
    }

    public static Vector<Ticket> readFile(){
        BufferedReader bufferedReader;
        String data = "";
        try{
            bufferedReader = new BufferedReader(new FileReader(Path));
            while (bufferedReader.ready()){
                data += (char) bufferedReader.read();
            }
        }
        catch (IOException|NullPointerException e){
            System.out.println("Проблемы с чтением файла.");
            System.out.println("Путь к файлу file.json должен быть прописан в переменной окружения File_Path.");
            System.exit(-1);
        }
        Vector<Ticket> collection = new Vector<>();
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<LinkedTreeMap>>(){}.getType();
            ArrayList<LinkedTreeMap> objects = new Gson().fromJson(data, type);
            for (LinkedTreeMap object : objects) {
                LinkedTreeMap parameters = (LinkedTreeMap) object;
                Ticket ticket = new Ticket();

                try{
                    ticket.setId(((Double) parameters.get("id")).intValue());
                }
                catch (NullPointerException e){
                    System.out.println("Неверные данные в файле. Значение поля id не должно быть null.");
                    System.exit(-1);
                }
                catch (ClassCastException e){
                    System.out.println("Неверные данные в файле. Значение поля id должно иметь тип Integer.");
                    System.exit(-1);
                }

                try {
                    if ((parameters.get("creationDate")) != null){
                        DateFormat format = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a", Locale.ENGLISH);
                        Date creationDate = format.parse((String) parameters.get("creationDate"));
                        ticket.setCreationDate(creationDate);
                    }
                    else if (parameters.get("creationDate") == null){
                        System.out.println("Неверные данные в файле. Значение поля creationDate не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                }
                catch (ClassCastException | ParseException e){
                    System.out.println("Неверные данные в файле. Значение поля creationDate представляет собой объект класса java.util.Date. Формат ввода: <<Месяц число, год чч:мм:сс АМ/РМ>>.");
                    System.out.println("Пример ввода: \"Apr 24, 2020 11:26:07 AM\". ");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }

                try {
                    if ((parameters.get("name") != null) && !(parameters.get("name").equals("")) && !(parameters.get("name").equals(" "))) {
                        ticket.setName((String) parameters.get("name"));
                    } else {
                        System.out.println("Неверные данные в файле. Значение поля name не должно быть null. Строка не может быть пустой.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                } catch (ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля name должно иметь тип String.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }

                try {
                    if (!((((Double) parameters.get("price")).floatValue()) <= 0)) {
                        ticket.setPrice(((Double) parameters.get("price")).floatValue());
                    } else {
                        System.out.println("Неверные данные в файле. Значение поля price должно быть > 0.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                } catch (ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля price должно иметь тип float.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                } catch (NullPointerException e) {
                    System.out.println("Неверные данные в файле. Значение поля price НЕ МОЖЕТ быть null.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }

                try {
                    if ((parameters.get("comment") != null)) {
                        ticket.setComment((String) parameters.get("comment"));
                    } else {
                        System.out.println("Неверные данные в файле. Значение поля comment не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                } catch (ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля comment имеет тип String.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }

                try {
                    if ((parameters.get("type") != null)) {
                        ticket.setType(TicketType.valueOf(((String) parameters.get("type")).toUpperCase()));
                    } else {
                        System.out.println("Неверные данные в файле. Значение поля type не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                } catch (IllegalArgumentException | ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля type не является константой class'а TicketType.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }

                Coordinates coordinates = new Coordinates();
                LinkedTreeMap coordinate = (LinkedTreeMap) parameters.get("coordinates");
                try {
                    coordinates.setX(((Double) coordinate.get("x")).floatValue());
                } catch (ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля x должно иметь тип Float.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                } catch (NullPointerException e) {
                    System.out.println("Неверные данные в файле. Значение поля x не должно быть null.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }
                try {
                    coordinates.setY((double) coordinate.get("y"));
                } catch (ClassCastException e) {
                    System.out.println("Неверные данные в файле. Значение поля y должно иметь тип double.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                } catch (NullPointerException e) {
                    System.out.println("Неверные данные в файле. Значение поля y НЕ МОЖЕТ быть null.");
                    System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                    System.exit(-1);
                }
                ticket.setCoordinates(coordinates);

                Person person = new Person();
                LinkedTreeMap persons = (LinkedTreeMap) parameters.get("person");
                if (parameters.get("person") != null) {
                    try {
                        if (!((((Double) persons.get("height")).intValue()) < 46) && !((((Double) persons.get("height")).intValue() > 240))) {
                            person.setHeight((((Double) persons.get("height")).intValue()));
                        } else {
                            System.out.println("Неверные данные в файле. Значение поля height должно быть больше 46, но меньше 240.");
                            System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                            System.exit(-1);
                        }
                    } catch (ClassCastException e) {
                        System.out.println("Неверные данные в файле. Значение поля height должно иметь тип int.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    } catch (NullPointerException e) {
                        System.out.println("Неверные данные в файле. Значение поля height НЕ МОЖЕТ быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                    try {
                        person.setEyeColor(Color.valueOf(((String) persons.get("eyeColor")).toUpperCase()));
                    } catch (IllegalArgumentException | ClassCastException e) {
                        System.out.println("Неверные данные в файле. Значение поля eyeColor не является константой class'а Color.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    } catch (NullPointerException e) {
                        System.out.println("Неверные данные в файле. Значение поля eyeColor не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                    try {
                        person.setHairColor(Color.valueOf(((String) persons.get("hairColor")).toUpperCase()));
                    } catch (IllegalArgumentException | ClassCastException e) {
                        System.out.println("Неверные данные в файле. Значение поля hairColor не является константой class'а Color.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    } catch (NullPointerException e) {
                        System.out.println("Неверные данные в файле. Значение поля hairColor не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                    try {
                        person.setNationality(Country.valueOf(((String) persons.get("nationality")).toUpperCase()));
                    } catch (IllegalArgumentException | ClassCastException e) {
                        System.out.println("Неверные данные в файле. Значение поля nationality не является константой class'а Color.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    } catch (NullPointerException e) {
                        System.out.println("Неверные данные в файле. Значение поля nationality не должно быть null.");
                        System.out.println("Ошибка в объекте с Id: " + ((Double) parameters.get("id")).intValue());
                        System.exit(-1);
                    }
                } else person = null;
                ticket.setPerson(person);
                collection.add(ticket);
            }
            AbstractCommand.setCollection(collection);
        }
        catch (NullPointerException e){
            System.out.println("Произошла ошибка при загрузке коллекции.");
            System.exit(-1);
        }
        catch (JSONParserException | JsonSyntaxException e){
            System.out.println("Неверные данные в файле. Ошибка <Синтаксис JSON>.");
            System.exit(-1);
        }
        return collection;
    }

    /*public static void addCommand(){
        AbstractCommand.addNewCommand(new Command_Add(), new Command_AddIfMax(), new Command_Clear(), new Command_ExecuteScriptFile(),
                new Command_Exit(), new Command_Help(), new Command_Info(), new Command_MaxByCoordinates(), new Command_PrintDescendingPrice(),
                new Command_PrintUniquePrice(), new Command_RemoveAtIndex(), new Command_RemoveById(), new Command_Reorder(),
                new Command_Save(), new Command_Show(), new Command_UpdateId());
    }*/

    public static Command readCommandFromConsole() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("Введите название команды и её аргументы, если они требуются. Для просмотра доступных команд введите"+ANSI_GREEN+" Help. "+ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        Command command = null;
        String input =null;
        String name = null;
        Class myObject;
        while (true) {
            try {
                input = scanner.nextLine() + " ";
                name = input.substring(0, input.indexOf(" "));
                myObject = Class.forName("laba.commands.Command_" + name);
                Command comm = (Command) myObject.newInstance();
                System.out.println(comm);
            } catch (ClassNotFoundException e) {
                try {
                    myObject = Class.forName("Server.Command_" + name);
                    Command comm = (Command) myObject.newInstance();
                } catch (ClassNotFoundException exp) {
                    System.out.println("Вы ввели несуществующую команду.");
                    continue;
                }
            }
            try {
                input = input.trim();
                Constructor[] constructor = myObject.getConstructors();
                Class[] parameterTypes = constructor[0].getParameterTypes();
                if (parameterTypes.length == 0) {
                    command = (Command) constructor[0].newInstance();
                } else if (parameterTypes.length == 1) {
                    if (parameterTypes[0].toString().compareTo("int") == 0) {
                        command = (Command) constructor[0].newInstance(Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1)));
                    } else if (parameterTypes[0].toString().compareTo("class laba.com.company.Ticket") == 0) {
                        command = (Command) constructor[0].newInstance(createTicket());
                    } else if (parameterTypes[0].toString().compareTo("float") == 0) {
                        command = (Command) constructor[0].newInstance(Float.parseFloat(input.substring(input.lastIndexOf(" ") + 1)));
                    } else if (parameterTypes[0].toString().compareTo("class java.lang.String") == 0) {
                        try {
                            input.substring(input.indexOf(" "));
                        } catch (StringIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException();
                        }
                        command = (Command) constructor[0].newInstance(input.substring(input.indexOf(" ") + 1));
                    }
                } else if (parameterTypes.length == 2) {
                    command = (Command) constructor[0].newInstance(Integer.parseInt(input.substring(input.indexOf(" ") + 1)), createTicket());
                }
            } catch (IllegalArgumentException exp) {
                System.out.println("Вы ввели неверные аргументы команды. Повторите ввод.");
                continue;
            }
            break;
        }
        return command;
    }

    private static Ticket createTicket(){
        System.out.println("Для выполнения команды необходимо создать Ticket.");
        String input;
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Введите поле name: ");
        System.out.print("\tname: ");
        name = scanner.nextLine().trim();
        while (name.isEmpty()){
            System.out.println("Неверный ввод. Повторите ввод поля name.");
            System.out.print("\tname: ");
            name = scanner.nextLine().trim();
        }
        Coordinates coordinates = createCoordinates();
        float price;
        System.out.println("Введите значение поля price: ");
        while (true){
            System.out.print("\tprice: ");
            input = scanner.nextLine();
            if (readFloat(input)){
                price = Float.parseFloat(input);
                if ((price <= 0) || input.equals(" ")){
                }
                else break;
            }
            System.out.println("Вы ввели не правильное значение поля price. Значение данного поля должно быть числом типа float, больше 0. Повторите ввод поля price.");
        }
        String comment;
        System.out.println("Введите комментарий: ");
        System.out.print("\tcomment: ");
        comment = scanner.nextLine().trim();
        while (comment.isEmpty()){
            System.out.println("Неверный ввод. Значение данного поля не может быть null. Повторите ввод поля comment.");
            System.out.print("\tcomment: ");
            comment = scanner.nextLine().trim();
        }
        TicketType type;
        System.out.println("Задайте тип: ");
        type = createType();
        Person person = null;
        System.out.println("Хотите задать поле person? (Да - да, хотите; Пустая строка - нет, не хотите.)");
        String answer = scanner.nextLine().trim().toUpperCase();
        if (answer.compareTo("ДА") == 0){
            person = createPerson();
        }
        else if (answer.compareTo("") == 0) person = null;
        else {
            while (answer.compareTo("ДА") != 0 && answer.compareTo("") != 0){
                System.out.println("Неверный ввод. Ваш ответ не распознан. Повторите ввод");
                answer = scanner.nextLine().trim().toUpperCase();
            }
        }

        return new Ticket(name, coordinates, price, comment, type, person);
    }

    private static Coordinates createCoordinates(){
        Scanner scanner = new Scanner(System.in);
        Float x = null;
        double y;
        String input;
        System.out.println("Введите координаты: ");
        while (true){
            System.out.print("\tx: ");
            input = scanner.nextLine();
            if (readFloat(input)) x = Float.parseFloat(input);
            else {
                System.out.println("Вы ввели неверное значение x. Данное поле имеет тип float.");
            }
            if (x == null) System.out.println("Повторите ввод.");
            else break;
        }
        while (true){
            System.out.print("\ty: ");
            input = scanner.nextLine();
            if (readDouble(input)) y = Double.parseDouble(input);
            else {
                System.out.println("Вы ввели неверное значение y. Данное поле имеет тип double. Повторите ввод");
                continue;
            }
            break;
        }
        return new Coordinates(x, y);
    }

    private static TicketType createType() {
        System.out.println("Доступные варианты: ");
        for (TicketType type : TicketType.values()) {
            System.out.println("\t" + type);
        }
        String type = null;
        Scanner scanner = new Scanner(System.in);
        boolean test = false;
        while (!test) {
            System.out.print("\ttype: ");
            type = scanner.nextLine();
            for (TicketType type1 : TicketType.values()) {
                if (type1.name().equals(type.toUpperCase())) {
                    test = true;
                    break;
                }
            }
            if (!test){
                System.out.println("Вы ввели неверный тип. Повторите ввод.");
            }
        }
        return TicketType.valueOf(type.toUpperCase());
    }

    private static boolean readFloat(String string){
        try{
            Float.parseFloat(string);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    private static Person createPerson(){
        Scanner scanner = new Scanner(System.in);
        String input;
        int height;
        while (true){
            System.out.println("Введите рост: ");
            System.out.print("\theight: ");
            input = scanner.nextLine();
            if (readInt(input)) height = Integer.parseInt(input);
            else {
                System.out.println("Вы ввели неправильный рост. Повторите ввод.");
                continue;
            }
            if (height < 46 || height > 240){
                System.out.println("Вы ввели неправильный рост. Рост человека не может быть меньше 46 и больше 240. Повторите ввод.");
            }
            else break;
        }
        Color eyeColor;
        System.out.println("Введите цвет глаз: ");
        eyeColor = createColor();
        Color hairColor;
        System.out.println("Введите цвет волос: ");
        hairColor = createColor();
        Country nationality;
        System.out.println("Введите национальность: ");
        nationality = createCountry();
        return new Person(height, eyeColor, hairColor, nationality);
    }

    private static boolean readDouble(String string){
        try{
            Double.parseDouble(string);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    private static boolean readInt(String string){
        try{
            Integer.parseInt(string);
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    private static Color createColor(){
        System.out.println("Доступные варианты: ");
        for (Color color : Color.values()) {
            System.out.println("\t" + color);
        }
        String color = null;
        Scanner scanner = new Scanner(System.in);
        boolean test = false;
        while (!test) {
            System.out.print("\tcolor: ");
            color = scanner.nextLine();
            for (Color color1 : Color.values()) {
                if (color1.name().equals(color.toUpperCase())) {
                    test = true;
                    break;
                }
            }
            if (!test){
                System.out.println("Вы ввели неверный цвет. Повторите ввод.");
            }
        }
        return Color.valueOf(color.toUpperCase());
    }

    private static Country createCountry(){
        System.out.println("Доступные варианты: ");
        for (Country country : Country.values()) {
            System.out.println("\t" + country);
        }
        String country = null;
        Scanner scanner = new Scanner(System.in);
        boolean test = false;
        while (!test) {
            System.out.print("\tcountry: ");
            country = scanner.nextLine();
            for (Country country1 : Country.values()) {
                if (country1.name().equals(country.toUpperCase())) {
                    test = true;
                    break;
                }
            }
            if (!test){
                System.out.println("Вы ввели неверную страну. Повторите ввод.");
            }
        }
        return Country.valueOf(country.toUpperCase());
    }
}
