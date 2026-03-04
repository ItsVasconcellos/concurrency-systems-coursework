/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw01;

/**
 *
 * @author fvasconcellos
 */
public class Staff extends Thread {
    private String name;
    // 1 for coffee, 2 for tea and 3 for cakes.
    private int type;
    private Buffet b;
    private Simulation s;

    Staff(Buffet buffet, String name, Simulation simulation) {
        int t = (int) ((Math.random() * 3) + 1);
        this.b = buffet;
        this.type = t;
        this.name = name;
        this.s = simulation;
    }

    @Override
    public void run() {
        while (s.isSimulating()) {
            try {
                Long working_time = s.generateRandomTime();
                int quantity = (int) ((Math.random() * 4) + 1);
                System.out.println("Staff is refilling " + quantity + " " + mapType());
                this.b.refillItem(type, quantity);
                System.out.println("Staff will prepare more " + mapType() + " in " + working_time + "s");
                Thread.sleep(working_time * 1000);

            } catch (InterruptedException e) {
                System.out.println("Thread interrompida");
            }
        }
    }

    public String mapType() {
        switch (this.type) {
            case 1:
                return "Coffee";
            case 2:
                return "Tea";
            case 3:
                return "Cake";
            default:
                return "";
        }

    }

}
