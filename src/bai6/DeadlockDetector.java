package bai6;

import java.lang.management.*;

public class DeadlockDetector implements Runnable {
    @Override
    public void run() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        while(true) {
            try {
                Thread.sleep(5000);
                long[] ids = bean.findDeadlockedThreads();

                if(ids!=null) {
                    System.out.println("DEADLOCK DETECTED!");
                    ThreadInfo[] infos = bean.getThreadInfo(ids);
                    for(ThreadInfo info:infos)
                        System.out.println(info);
                } else {
                    System.out.println("Không phát hiện deadlock.");
                }
            }
            catch(Exception e){}
        }
    }
}