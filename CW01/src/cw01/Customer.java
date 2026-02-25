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

    Customer(Buffet buffet, String name) {
        this.b = buffet;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            int action = (int) ((Math.random() * 6) + 1);
            System.out.println("Action type:" + action);
            if (action <= 5) {
                b.takeOrder(action);
            } else if (action == 6) {
                b.piano.play();
            } else {
                Long music_length = (long) (1 + Math.random() * 100);
                System.out.println("Listen to music for: " + music_length + "s");
                Thread.sleep(music_length * 100);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrompida");
        }
    }

}
