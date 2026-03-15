package bai6;

import java.util.*;

public class StatisticsService {
    List<Room> rooms;

    public StatisticsService(List<Room> rooms) {
        this.rooms=rooms;
    }

    public void showStats() {
        System.out.println("=== THỐNG KÊ HIỆN TẠI ===");
        int revenue=0;

        for(Room r:rooms) {
            int sold = r.soldCount();
            System.out.println("Phòng "+r.name+" đã bán "+sold+" vé");
            revenue += sold * 250000;
        }
        System.out.println("Tổng doanh thu: "+revenue+" VND");
    }
}
