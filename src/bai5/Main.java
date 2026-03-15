package bai5;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 8);
        TicketPool roomC = new TicketPool("C", 6);

        List<TicketPool> pools = Arrays.asList(roomA, roomB, roomC);

        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(
                    new BookingCounter("Quầy " + i, pools));
            t.start();
        }

        Thread timeoutManager =
                new Thread(new TimeoutManager(pools));
        timeoutManager.start();
    }
}