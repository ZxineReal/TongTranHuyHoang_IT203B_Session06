package bai5;

import java.util.*;

public class TimeoutManager implements Runnable {
    private List<TicketPool> pools;

    public TimeoutManager(List<TicketPool> pools) {
        this.pools = pools;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                for (TicketPool pool : pools) {
                    pool.releaseExpiredTickets();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}