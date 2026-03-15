package bai6;

public class Ticket {
    String id;
    boolean sold;
    boolean held;
    long holdExpiry;
    boolean vip;

    public Ticket(String id) {
        this.id = id;
    }
}