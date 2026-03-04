/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw01;

/**
 *
 * @author fvasconcellos
 */
public class Main {

    public static void main(String args[]) {
        int customers = 20;
        int staff = 3;
        System.out.println("Teste");
        Buffet buffet = new Buffet(5, 5, 5);
        Simulation s = new Simulation();
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
}
