package bai1234;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();
    private int nextId = 1;

    public TicketPool(String roomName, int initial) {
        this.roomName = roomName;
        addTickets(initial);
    }

    public synchronized Ticket sellTicket() {
        while (remainingTickets() == 0) {
            try {
                System.out.println("Phòng " + roomName + " hết vé, chờ thêm...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Ticket t : tickets) {
            if (!t.isSold()) {
                t.setSold(true);
                return t;
            }
        }

        return null;
    }

    public synchronized void addTickets(int count) {
        for (int i = 0; i < count; i++) {
            String id = roomName + "-" + String.format("%03d", nextId++);
            tickets.add(new Ticket(id, roomName));
        }

        System.out.println("Nhà cung cấp thêm " + count + " vé phòng " + roomName);
        notifyAll();
    }

    public synchronized int remainingTickets() {
        int count = 0;

        for (Ticket t : tickets) {
            if (!t.isSold())
                count++;
        }

        return count;
    }
}