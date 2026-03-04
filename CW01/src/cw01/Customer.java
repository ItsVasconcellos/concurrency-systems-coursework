/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw01;

/**
 *
 * @author fvasconcellos
 */
public class Customer extends Thread {
    private String name;
    private Buffet b;
    private Simulation s;

    Customer(Buffet buffet, String name, Simulation simulation) {
        this.b = buffet;
        this.name = name;
        this.s = simulation;
    }

    @Override
    public void run() {
        while (s.isSimulating()) {
            try {
                int action = (int) ((Math.random() * 6) + 1);
                System.out.println("Action type:" + action);
                if (action <= 5) {
                    b.takeOrder(action, this.name);
                } else if (action == 6) {
                    b.piano.play();
                } else {
                    Long music_length = s.generateRandomTime();
                    System.out.println("Listen to music for: " + music_length + "s");
                    Thread.sleep(music_length * 100);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrompida");
            }
        }
    }
}
