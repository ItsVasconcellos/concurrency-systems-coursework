/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loadbalancer;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author fvasconcellos
 */
public class LoadBalancer {
    private ArrayList<Server> serverAddress;

    public LoadBalancer(){
        this.serverAddress = new ArrayList<Server>();
    }
    
    public void addServer(Server server) {
        serverAddress.add(server);
    }
    
    public Server selectServer(){
        Server bestServer = null;
        int maxAvailable = -1;

        for (Server s : serverAddress) {
            if (s.getIsAvailable() && s.getTotalCapacity() > maxAvailable) {
                maxAvailable = s.getTotalCapacity();
                bestServer = s;
            }
        }
        
        if(bestServer != null){
            return (bestServer.getTotalCapacity() > 0) ? bestServer : null;
        }
        else{
            for (Server s : serverAddress) {
                if (s.getIsAvailable() && s.getTotalCapacity() > maxAvailable) {
                    maxAvailable = s.getTotalCapacity();
                    bestServer = s;
                }
            }
            return (bestServer.getTotalCapacity() > 0) ? bestServer : null;
        }
    }
}
