package cw01;

public class Simulation {
    private boolean isSimulating;
    private boolean isSpedUp;

    Simulation() {
        this.isSimulating = true;
        this.isSpedUp = false;
    }

    public void main(int customers, int staff, int[] buffet_items) {
        Buffet buffet = new Buffet(buffet_items[0], buffet_items[1], buffet_items[2]);
        Simulation s = this;
        int size = customers + staff;
        Thread threads[] = new Thread[size];

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

    public void setIsSimulating() {
        this.isSimulating = !this.isSimulating;
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
