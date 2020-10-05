package laba.com.company;

import java.io.Serializable;

/**
 * Coordinates used in class Ticket.
 */

public class Coordinates implements Comparable, Serializable {
    private Float x; //Поле не может быть null
    private double y;

    public Coordinates(){
    }

    public Coordinates(Float x, double y) {
        if (x == null) {
            throw new IllegalArgumentException("Данное поле не может быть null.");
        } else {
            this.x = x;
        }
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + ", " + "y = " + y + ".";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return this.x.equals(coordinates.x) && this.y == coordinates.y;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Coordinates))
            throw new IllegalArgumentException("Объект класса Coordinates можно сравнить только с объектами этого класса. ");
        else {
            int result = 0;
            Coordinates coordinates = (Coordinates) o;
            if (!((this.x == null) && (coordinates.x == null))){
                if (this.equals(coordinates)) result = 0;
                else if (this.getX() > coordinates.getX()) result = 1;
                else if (this.getX() < coordinates.getX()) result = -1;
                else if (this.getX().equals(coordinates.getX())){
                    if (this.getY() > coordinates.getY()) result = 1;
                    else result = -1;
                }

            }
            return result;
        }
    }
}

