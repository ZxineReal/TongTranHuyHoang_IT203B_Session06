package bai5;

import java.util.*;

public class BookingCounter implements Runnable {
    private String counterName;
    private List<TicketPool> pools;
    private Random random = new Random();

    public BookingCounter(String counterName, List<TicketPool> pools) {
        this.counterName = counterName;
        this.pools = pools;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TicketPool pool = pools.get(random.nextInt(pools.size()));
                boolean vip = random.nextBoolean();
                Ticket ticket = pool.holdTicket(vip);

                if (ticket == null) {
                    System.out.println(counterName + ": Không còn vé phòng " + pool.getRoomName());
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println(counterName + ": Đã giữ vé " + ticket.getTicketId() + (vip ? " (VIP)" : "") + ". Vui lòng thanh toán trong 5s");

                Thread.sleep(3000);

                boolean paid = random.nextBoolean();

                if (paid) {
                    boolean success = pool.sellHeldTicket(ticket);
                    if (success)
                        System.out.println(counterName + ": Thanh toán thành công vé " + ticket.getTicketId());
                } else {
                    System.out.println(counterName + ": Khách không thanh toán vé " + ticket.getTicketId());
                }

                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
