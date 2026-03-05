package cw01;

import java.util.concurrent.Semaphore;;

public class Piano {
    private final Semaphore semaphore;

    public Piano() {
        semaphore = new Semaphore(2, true);
    }

    public void play() {
        try {
            semaphore.acquire();
            Long songs = (long) (1 + Math.random() * 100);
            Thread.sleep(songs * 100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally { 
            semaphore.release();
        }
    }
}
