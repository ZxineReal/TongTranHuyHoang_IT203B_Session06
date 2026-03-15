package bai1234;

import java.util.Random;

public class BookingCounter implements Runnable {

    private String counterName;
    private TicketPool roomA;
    private TicketPool roomB;
    private int soldCount = 0;
    private Random random = new Random();

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public boolean sellComboDeadlock() {

        Ticket tA = null;
        Ticket tB = null;

        synchronized (roomA) {

            tA = roomA.sellTicket();

            synchronized (roomB) {

                tB = roomB.sellTicket();

                if (tA != null && tB != null) {
                    soldCount += 2;
                    System.out.println(counterName + " bán COMBO: "
                            + tA.getTicketId() + " + " + tB.getTicketId());
                    return true;
                }
            }
        }

        if (tA != null)
            tA.setSold(false);

        if (tB != null)
            tB.setSold(false);

        return false;
    }

    public boolean sellComboSafe() {

        Ticket tA = null;
        Ticket tB = null;

        TicketPool first = roomA;
        TicketPool second = roomB;

        synchronized (first) {
            synchronized (second) {

                tA = roomA.sellTicket();
                tB = roomB.sellTicket();

                if (tA != null && tB != null) {

                    soldCount += 2;

                    System.out.println(counterName + " bán COMBO: "
                            + tA.getTicketId() + " + " + tB.getTicketId());

                    return true;
                }

                if (tA != null)
                    tA.setSold(false);

                if (tB != null)
                    tB.setSold(false);

                return false;
            }
        }
    }

    @Override
    public void run() {

        while (true) {

            boolean combo = Math.random() < 0.3;

            if (combo) {

                sellComboSafe();

            } else {

                Ticket t = roomA.sellTicket();

                if (t != null) {
                    soldCount++;
                    System.out.println(counterName + " bán vé " + t.getTicketId());
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
