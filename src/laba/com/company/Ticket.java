package laba.com.company;

import java.io.Serializable;
import java.util.Date;

/**
 The class whose objects are contained in the collection.
 */
public class Ticket implements Comparable, Serializable {
    private static Integer ID = 1;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private String comment; //Поле не может быть null
    private TicketType type; //Поле не может быть null
    private Person person; //Поле может быть null

    public Ticket (){
        this.id = ID;
        this.creationDate = new Date();
        ID++;
    }

    public Ticket (String name, Coordinates coordinates, float price, String comment, TicketType type, Person person){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        if (price < 0 || price == 0) {
            throw new IllegalArgumentException("Значение поля должно быть больше нуля.");
        }
        else {
            this.price = price;
        }
        this.comment = comment;
        this.type = type;
        this.person = person;
        ID++;
    }

    public Ticket (String name, Coordinates coordinates, float price, String comment, TicketType type){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        if (price < 0 || price == 0){
            throw new IllegalArgumentException("Значение поля должно быть больше нуля.");
        }
        else {
            this.price = price;
        }
        this.comment = comment;
        this.type = type;
        ID++;
    }

    public Ticket (String name, Coordinates coordinates, float price, String comment){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        if (price < 0 || price == 0){
            throw new IllegalArgumentException("Значение поля должно быть больше нуля.");
        }
        else {
            this.price = price;
        }
        this.comment = comment;
        ID++;
    }

    public Ticket (String name, Coordinates coordinates, float price){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        if (price < 0 || price == 0){
            throw new IllegalArgumentException("Значение поля должно быть больше нуля.");
        }
        else {
            this.price = price;
        }
        ID++;
    }

    public Ticket (String name, Coordinates coordinates){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        ID++;
    }

    public Ticket (String name){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        ID++;
    }

    public Ticket (String name, Coordinates coordinates, Person person){
        this.id = ID;
        this.creationDate = new Date();
        this.name = name;
        this.coordinates = coordinates;
        this.person = person;
        ID++;
    }

    public Ticket (Coordinates coordinates){
        this.id = ID;
        this.creationDate = new Date();
        this.coordinates = coordinates;
        ID++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public java.util.Date getCreationDate(){
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Float getPrice(){
        return price;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    @Override
    public String toString(){
        return  "id: "+id+'\n'+
                "name: "+name+'\n'+
                "coordinates: "+coordinates+'\n'+
                "creationDate: "+creationDate+'\n'+
                "price: "+price+'\n'+
                "ticket type: "+type+'\n'+
                "person: "+person+'\n'+
                "comment: "+comment+"."+'\n';
    }

    @Override
    public int compareTo(Object o) throws NullPointerException{
        if (!(o instanceof Ticket))
            throw new IllegalArgumentException("Объект класса Ticket можно сравнить только с объектами этого класса ");
        else {
            int result = 2;
            Ticket ticket = (Ticket) o;
            if (this.getCoordinates().getX() == null || ticket.getCoordinates().getX() == null){
                System.out.println("В одном из данных объектов нет поля coordinates.");
                return result;
            }

            else if (!(this.getCoordinates().getX() == null || ticket.getCoordinates().getX() == null) &&
                    (this.getCoordinates().getX() > ticket.getCoordinates().getX())) result = 1;
            else if (!(this.getCoordinates().getX() == null || ticket.getCoordinates().getX() == null) &&
                    (this.getCoordinates().getX() < ticket.getCoordinates().getX())) result = -1;
            else if (!(this.getCoordinates().getX() == null || ticket.getCoordinates().getX() == null) &&
                    (this.getCoordinates().getX().equals(ticket.getCoordinates().getX()))) {
                if (this.getCoordinates().getY() > ticket.getCoordinates().getY()) result = 1;
                else if (this.getCoordinates().getY() < ticket.getCoordinates().getY()) result = -1;
                else if (this.getCoordinates().getY() == ticket.getCoordinates().getY()) result = 0;
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        Ticket ticket = (Ticket) o;
        return this.id.equals(ticket.id) &&
                this.name.equals(ticket.name) &&
                this.coordinates == ticket.coordinates &&
                this.creationDate == ticket.creationDate &&
                this.comment.equals(ticket.comment) &&
                this.person == ticket.person &&
                this.price == ticket.price &&
                this.type == ticket.type;
    }
}
