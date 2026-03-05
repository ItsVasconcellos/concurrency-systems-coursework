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
        while (s.isSimulating() && !Thread.currentThread().isInterrupted()) {
            try {
                int action = (int) ((Math.random() * 6) + 1);
                Long action_length = s.generateRandomTime();
                if (action <= 5) {
                    System.out.println(this.name + " wants " + this.mapTypeAction(action) + " from the buffet.");
                    b.takeOrder(action, this.name);                
                    Thread.sleep(action_length * 100);
                } else if (action == 6) {
                    System.out.println(this.name + " will play the piano.");
                    b.piano.play();
                } else {
                    System.out.println(this.name + " will listen to music for: " + action_length + "ms");
                    Thread.sleep(action_length * 100);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
    
    private String mapTypeAction(int action){
        switch (action) {
            case 1:                
                return "a coffee"; 
            case 2:                
                return "a tea"; 
            case 3:                
                return "a cake"; 
            case 4:                
                return "coffee and cake"; 
            case 5:                
                return "tea and cake"; 
            
            default:
                throw new AssertionError();
        }
    }
}
