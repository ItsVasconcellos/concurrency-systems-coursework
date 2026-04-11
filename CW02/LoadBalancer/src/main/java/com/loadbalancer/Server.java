/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loadbalancer;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author fvasconcellos
 */
public class Server {
    private boolean isAvailable;
    private String name;
    private int totalCapacity;
    private AtomicInteger currentCapacity = new AtomicInteger(0);

    public Server(String name, int capacity){
        this.name = name;
        this.totalCapacity = capacity;
        this.isAvailable = true;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public int getTotalCapacity() {
        return totalCapacity - currentCapacity.get();
    }
    
    public void process(int id) throws InterruptedException{
        try {
        currentCapacity.incrementAndGet();
        long startTime = System.currentTimeMillis();
        System.out.printf("[Request %d] STARTED on %s at %dms\n", id, this.name, startTime % 100000);
        
        Random rand = new Random();
        int time = rand.nextInt(1,10);
        
            Thread.sleep(time*1000);
        currentCapacity.decrementAndGet();
        long endTime = System.currentTimeMillis();
        System.out.printf("[Request %d] FINISHED on %s at %dms (Duration: %dms)\n", 
                          id, this.name, endTime % 100000, (endTime - startTime));
        }
         catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
