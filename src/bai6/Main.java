package bai6;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    static ExecutorService executor;
    static List<Room> rooms = new ArrayList<>();
    static List<BookingCounter> counters = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("""
                1. Bắt đầu mô phỏng
                2. Tạm dừng
                3. Tiếp tục
                4. Thêm vé vào phòng
                5. Xem thống kê
                6. Phát hiện deadlock
                7. Thoát
                """);
            int choice = sc.nextInt();

            switch(choice) {
                case 1:
                    startSimulation(sc);
                break;
                case 2:
                    counters.forEach(BookingCounter::pause);
                    break;
                case 3:
                    counters.forEach(BookingCounter::resume);
                    break;
                case 4:
                    addTickets(sc);
                    break;
                case 5:
                    new StatisticsService(rooms).showStats();
                    break;
                case 6:
                    System.out.println("Đang quét deadlock...");
                    break;
                case 7:
                {
                    executor.shutdownNow();
                    System.out.println("Kết thúc chương trình.");
                    return;
                }
            }
        }
    }

    static void startSimulation(Scanner sc) {
        System.out.print("Số phòng: ");
        int r = sc.nextInt();
        System.out.print("Vé/phòng: ");
        int cap = sc.nextInt();
        System.out.print("Số quầy: ");
        int c = sc.nextInt();

        executor = Executors.newFixedThreadPool(c+2);

        for(int i = 0; i < r; i++) {
            rooms.add(new Room(""+(char)('A'+i),cap));
        }

        for(int i = 1; i <= c;i++) {
            BookingCounter counter = new BookingCounter("Quầy "+i,rooms);
            counters.add(counter);
            executor.submit(counter);
        }

        executor.submit(new TimeoutManager(rooms));
        executor.submit(new DeadlockDetector());

        System.out.println("Hệ thống đã khởi động.");
    }

    static void addTickets(Scanner sc) {
        System.out.print("Phòng: ");
        String name = sc.next();
        System.out.print("Số vé thêm: ");
        int count = sc.nextInt();

        for(Room r:rooms) {
            if(r.name.equals(name)) {
                for(int i = 0; i < count; i++) {
                    r.tickets.add(new Ticket(name+"-NEW"+i));
                }
            }
        }
    }
}
