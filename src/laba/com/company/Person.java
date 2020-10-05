package laba.com.company;

import java.io.Serializable;
import  java.util.Objects;

/**
 * Class Person. Used in class Ticket.
 */

public class Person implements Serializable {

    private int height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null

    public Person(){
    }

    public Person(int height, Color eyeColor, Color hairColor, Country nationality){
        if (height < 46 || height > 240){
            throw new IllegalArgumentException("Значение height должно быть больше нуля, но меньше 240.");
        }
        else{
            this.height = height;
            this.eyeColor = eyeColor;
            this.hairColor = hairColor;
            this.nationality = nationality;
        }
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return this.nationality.equals(person.nationality) || this.hairColor.equals(person.hairColor) || this.eyeColor.equals(person.eyeColor)||this.height==person.height;
    }

    @Override
    public int hashCode(){
        return Objects.hash(height, eyeColor, hairColor, nationality);
    }

    @Override
    public String toString(){
        return "height: " + height + ", eyeColor: " + eyeColor + ", hairColor: " + hairColor + ", nationality: " + nationality;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Color getHairColor(){
        return this.hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Country getNationality(){
        return this.nationality;
    }
}