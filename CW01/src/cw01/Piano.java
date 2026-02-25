package cw01;

import java.util.concurrent.Semaphore;;

public class Piano {
    private final Semaphore semaphore;

    public Piano() {
        semaphore = new Semaphore(2, true);
    }

    public void play() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " playing the piano, seats available:  " + semaphore.availablePermits()
                    + " clients in the queue " + semaphore.getQueueLength());
            semaphore.acquire();
            Long songs = (long) (1 + Math.random() * 100);
            System.out.println(Thread.currentThread().getName()
                    + " using the piano for  " + songs
                    + " songs, for time " + songs
                    + " s, available seats now: "
                    + semaphore.availablePermits());
            Thread.sleep(songs * 100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            // System.out.println(Thread.currentThread().getName()
            // + " packs groceries, there are available tills " +
            // semaphore.availablePermits()
            // + " clients in the queue " + semaphore.getQueueLength());
            semaphore.release();
            System.out.println(
                    Thread.currentThread().getName() + " will stop playing the piano, there are "
                            + semaphore.availablePermits()
                            + "available seats. Customers in the queue: " + semaphore.getQueueLength());
        }
    }
}
