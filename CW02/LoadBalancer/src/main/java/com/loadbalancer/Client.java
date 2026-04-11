/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.loadbalancer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author fvasconcellos
 */
public class Client {

    public static void main(String[] args) {
        // Create the Load Balancer
        LoadBalancer loadBalancer = new LoadBalancer();

        // Add servers to the load balancer
        Server server1 = new Server("Server_1", 5);
        Server server2 = new Server("Server_2", 4);
        Server server3 = new Server("Server_3", 3);
        loadBalancer.addServer(server1);
        loadBalancer.addServer(server2);
        loadBalancer.addServer(server3);

        // Simulate client requests        
        ExecutorService clientRequests = Executors.newFixedThreadPool(5);
        for (int i = 0; i <= 20; i++) {
            System.out.println("Distributing requests...");
            int requestId = i;
            clientRequests.submit(() -> {
                Server assignedServer = loadBalancer.selectServer();
                
                if (assignedServer != null) {
                    try{
                        assignedServer.process(requestId);
                    }
                    catch(Exception e){
                        System.out.println("" + e.getMessage());
                    }
                } else {
                    System.out.println("[Request " + requestId + "] REJECTED: All servers busy.");
                }
            });

            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
        clientRequests.shutdown();


        // Simulate a server failure
        System.out.println("\nSimulating server failure...");
        server2.setIsAvailable(false);

        // Distribute more requests after failure
        clientRequests = Executors.newFixedThreadPool(5);

        for (int i = 21; i <= 25; i++) {
            System.out.println("Distributing requests...");
            int requestId = i;
            clientRequests.submit(() -> {
                Server assignedServer = loadBalancer.selectServer();
                
                if (assignedServer != null) {
                    try{
                        assignedServer.process(requestId);
                    }
                    catch(Exception e){
                        System.out.println("" + e.getMessage());
                    }
                } else {
                    System.out.println("[Request " + requestId + "] REJECTED: All servers busy.");
                }
            });

            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
        clientRequests.shutdown();

    }
}
