package bai6;

import java.util.*;

public class Room {
    String name;
    List<Ticket> tickets = new ArrayList<>();

    public Room(String name, int capacity) {
        this.name = name;
        for(int i=1;i<=capacity;i++)
        {
            tickets.add(new Ticket(name+"-"+String.format("%03d",i)));
        }
    }

    public synchronized Ticket holdTicket(boolean vip) {
        for(Ticket t : tickets)
        {
            if(!t.sold && !t.held) {
                t.held = true;
                t.vip = vip;
                t.holdExpiry = System.currentTimeMillis()+5000;
                return t;
            }
        }
        return null;
    }

    public synchronized void sellTicket(Ticket t) {
        if(t!=null && t.held) {
            t.sold=true;
            t.held=false;
        }
    }

    public synchronized void releaseExpired() {
        long now = System.currentTimeMillis();

        for(Ticket t : tickets) {
            if(t.held && !t.sold && now>t.holdExpiry) {
                t.held=false;
                System.out.println("Timeout: "+t.id+" trả lại kho");
            }
        }
    }

    public int soldCount() {
        int c = 0;

        for(Ticket t:tickets)
            if(t.sold) c++;
        return c;
    }
}