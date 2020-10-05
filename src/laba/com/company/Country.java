package laba.com.company;

import java.io.Serializable;

/**
 * Countries used in class Person.
 */

public enum Country implements Serializable {
    UNITEDKINGDOM ("UNITEDKINGDOM"),
    THAILAND ("THAILAND"),
    NORTHKOREA("NORTHKOREA");
    private String title;
    Country(String title){
        this.title = title;
    }
    @Override
    public String toString(){
        return title;
    }
}

