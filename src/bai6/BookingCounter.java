package bai6;

import java.util.*;

public class BookingCounter implements Runnable {
    String name;
    List<Room> rooms;
    Random random = new Random();
    volatile boolean running=true;
    volatile boolean paused=false;

    public BookingCounter(String name,List<Room> rooms) {
        this.name=name;
        this.rooms=rooms;
    }

    public void pause(){
        paused=true;
    }

    public void resume(){
        paused=false;
    }

    public void stop(){
        running=false;
    }

    @Override
    public void run() {
        System.out.println(name+" bắt đầu bán vé...");
        while(running) {
            if(paused) {
                sleep(500);
                continue;
            }

            Room room = rooms.get(random.nextInt(rooms.size()));
            boolean vip = random.nextBoolean();
            Ticket t = room.holdTicket(vip);

            if(t==null) {
                sleep(500);
                continue;
            }

            System.out.println(name+" giữ vé "+t.id);
            sleep(3000);
            room.sellTicket(t);
            System.out.println(name+" bán vé "+t.id);
            sleep(500);
        }
    }

    void sleep(int ms) {
        try{Thread.sleep(ms);}catch(Exception e){}
    }
}
