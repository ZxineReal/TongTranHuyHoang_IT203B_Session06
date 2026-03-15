package bai6;

import java.util.*;

public class TimeoutManager implements Runnable {
    List<Room> rooms;

    public TimeoutManager(List<Room> rooms) {
        this.rooms=rooms;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                for(Room r:rooms) {
                    r.releaseExpired();
                }

            }catch(Exception e){}
        }
    }
}