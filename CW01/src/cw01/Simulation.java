package cw01;

public class Simulation {
    private volatile boolean isSimulating;
    private volatile boolean isSpedUp;
    private Thread[] threads;

    Simulation() {
        this.isSimulating = true;
        this.isSpedUp = false;
    }

    public void main(int customers, int staff, int[] buffet_items) {
        this.isSimulating = true;
        Buffet buffet = new Buffet(buffet_items[0], buffet_items[1], buffet_items[2]);
        Simulation s = this;
        int size = customers + staff;
        this.threads = new Thread[size];

        for (int i = 0; i < customers; i++) {
            threads[i] = new Thread(new Customer(buffet, "Customer - " + (i + 1), s));
            threads[i].start();
        }

        for (int i = 0; i < staff; i++) {
            threads[i] = new Thread(new Staff(buffet, "Staff- " + (i + 1), s));
            threads[i].start();
        }

    }

    public void setIsSpedUp() {
        this.isSpedUp = !this.isSpedUp;
    }
    
    public void stopSimulation() {
        this.setIsSimulating(false);
        System.out.println("Starting shutdown...");

        for (Thread t : threads) {
            if (t != null) {
                t.interrupt();
            }
        }

        for (Thread t : threads) {
            if (t != null) {
                try {
                    t.join(1000); 
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage() );
                    Thread.currentThread().interrupt(); 
                }
            }
        }

        int active = 0;
        for (Thread t : threads) {
            if (t != null && t.isAlive()) active++;
        }
        System.out.println("Shutdown complete. Threads still active: " + active);
    }

    public void setIsSimulating(Boolean s) {
        this.isSimulating = s;
    }

    public boolean isSpedUp() {
        return isSpedUp;
    }

    public boolean isSimulating() {
        return isSimulating;
    }

    public long generateRandomTime() {
        if (isSpedUp) {
            return (long) (1 + Math.random() * 0.5 * 1000);
        }
        return (long) (1 + Math.random() * 1000);
    }
}
