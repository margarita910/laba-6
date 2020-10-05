package laba.com.company;

import java.io.Serializable;

/**
 * Colors used in class Person.
 */

public enum Color implements Serializable {
    GREEN ("GREEN"),
    BLUE ("BLUE"),
    YELLOW ("YELLOW"),
    BROWN ("BROWN"),
    RED ("RED"),
    WHITE ("WHITE");
    private String title;
    Color(String title){
        this.title = title;
    }
    @Override
    public String toString(){
        return title;
    }
}
