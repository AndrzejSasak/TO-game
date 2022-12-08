package com.game.multiplayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DotPrintService {

    private Runnable waitRunnable = new Runnable() {
        public void run() {
            System.out.print(".");
        }
    };

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    public void startTimer()
    {
        executor.scheduleAtFixedRate(waitRunnable, 0, 1, TimeUnit.SECONDS);
    }

    public void stopTimer()
    {
        executor.shutdown();
        System.out.print('\n');
    }
}
