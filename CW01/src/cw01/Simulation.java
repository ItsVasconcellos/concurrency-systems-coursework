package cw01;

public class Simulation {
    private boolean isSimulating;
    private boolean isSpedUp;

    Simulation() {
        this.isSimulating = true;
        this.isSpedUp = false;
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
