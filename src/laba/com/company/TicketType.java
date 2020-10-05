package laba.com.company;

import java.io.Serializable;

/**
 Type objects <Ticket>.
 */

public enum TicketType implements Serializable {
    VIP ("VIP"),
    USUAL ("USUAL"),
    CHEAP ("CHEAP");
    private String title;
    TicketType(String title){
        this.title = title;
    }
    @Override
    public String toString(){
        return title;
    }
}