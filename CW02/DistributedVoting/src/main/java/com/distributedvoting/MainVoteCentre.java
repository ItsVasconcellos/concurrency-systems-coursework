/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributedvoting;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author fvasconcellos
 */
public class MainVoteCentre {
    
    public static final String queueName = "Elections";
    
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException{
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            VoteCentre vc = new VoteCentre(channel, queueName);
            for(int i = 0; i<5; i++){
                vc.generateVotes();
                TimeUnit.SECONDS.sleep(1);
                vc.sendVotes();
            }
            vc.sendFinalMessage();
        }
        catch(IOException | TimeoutException | InterruptedException e){
            System.out.println("Error happened during execution: " + e.getMessage() );
        }

    }    
}
