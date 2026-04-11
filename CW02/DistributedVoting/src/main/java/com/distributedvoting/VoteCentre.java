/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributedvoting;

import com.rabbitmq.client.Channel;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author fvasconcellos
 */
public class VoteCentre {
    private String centreId;
    private static final Set<String> parties = Set.of("Green", "Red", "Blue");
    private Channel channel;
    private Map<String,Integer> voteBatch; 
    private String queueName;

    public VoteCentre(Channel channel, String queueName){
        this.channel = channel;
        this.queueName = queueName;
        Random r = new Random();
        this.centreId = "Vote Centre-" + r.nextInt(1000);
    }
    
    public void generateVotes(){
        Map<String, Integer> hm = new HashMap();
        Random r = new Random();
        for(String party: parties){
            hm.put(party, r.nextInt(0,1000));
        }
        this.voteBatch = hm;
        System.out.println(" [x] " + centreId + " locally computed: " + this.voteBatch);
    }
    
    public void sendVotes(){
        try{
            VoteBatch vb = new VoteBatch(this.centreId, this.voteBatch, false);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(vb);
            this.channel.basicPublish("", this.queueName, null, json.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + json + "'");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage() + " - " + e.getClass());
        }
    }
    
    public void sendFinalMessage(){
        try{
            VoteBatch vb = new VoteBatch(this.centreId, new HashMap<>(), true);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(vb);
            this.channel.basicPublish("", this.queueName, null, json.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + json + "'");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage() + " - " + e.getClass());
        }
    }
}
