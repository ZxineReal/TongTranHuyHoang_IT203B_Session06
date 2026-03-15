package bai5;

import java.util.*;

public class TicketPool {
    private String roomName;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int capacity) {
        this.roomName = roomName;
        for (int i = 1; i <= capacity; i++) {
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new Ticket(id, roomName));
        }
    }

    public synchronized Ticket holdTicket(boolean isVIP) {
        for (Ticket t : tickets) {
            if (!t.isSold() && !t.isHeld()) {
                t.setHeld(true);
                t.setVIP(isVIP);
                long expiry = System.currentTimeMillis() + 5000;
                t.setHoldExpiryTime(expiry);
                return t;
            }
        }
        return null;
    }

    public synchronized boolean sellHeldTicket(Ticket ticket) {
        if (ticket == null) return false;
        if (ticket.isHeld() && !ticket.isSold()) {
            ticket.setSold(true);
            ticket.setHeld(false);
            return true;
        }
        return false;
    }

    public synchronized void releaseExpiredTickets() {
        long now = System.currentTimeMillis();
        for (Ticket t : tickets) {
            if (t.isHeld() && !t.isSold() && now > t.getHoldExpiryTime()) {
                System.out.println(
                        "TimeoutManager: Vé " + t.getTicketId() + " hết hạn giữ, trả lại kho");
                t.setHeld(false);
            }
        }
    }

    public String getRoomName() {
        return roomName;
    }
}
