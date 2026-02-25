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
        int customers = 5;
        int staff = 3;
        System.out.println("Teste");
        Buffet buffet = new Buffet(2, 2, 2);
        Thread customerArray[] = new Thread[customers];
        for (int i = 0; i < customerArray.length; i++) {
            customerArray[i] = new Thread(new Customer(buffet, "Customer - " + (i + 1)));
            customerArray[i].start();
        }
        for (int j = 0; j < customerArray.length; j++) {
            try {
                customerArray[j].join();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
