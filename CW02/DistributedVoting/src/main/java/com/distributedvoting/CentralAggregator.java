/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributedvoting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author fvasconcellos
 */
public class CentralAggregator {
    private String queueName;
    private static final Set<String> parties = Set.of("Green", "Red", "Blue");
    private Map<String, Map<String,Integer>> voteCenterPartialResult; 
    private Map<String,Integer> totalVoteResult; 
    private Channel chanell;
    
    public CentralAggregator(String queueName, Channel ch){
        this.queueName = queueName;
        this.chanell = ch;
        this.voteCenterPartialResult = new HashMap<String, Map<String, Integer>>();
        this.totalVoteResult = new HashMap<String, Integer>();
    }
    
    public void processVotes(String message) throws JsonProcessingException{
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        VoteBatch vb = mapper.readValue(message, VoteBatch.class);
        vb.voteCounter.forEach((k,v) -> {
            if(parties.contains(k)) {
                totalVoteResult.merge(k, v, Integer::sum);
            }
            else {
                System.err.println("  [!] Rejected votes for unknown party: " + k);
            }
        });
        Map<String, Integer> centerCount = this.voteCenterPartialResult.get(vb.centreId);
        if(centerCount != null ){
            vb.voteCounter.forEach((k,v) -> {
                if(parties.contains(k)) {
                    centerCount.merge(k, v, Integer::sum);
                }
                else {
                    System.err.println("  [!] Rejected votes for unknown party: " + k);
                }
            });
        }
        else{
            this.voteCenterPartialResult.put(vb.centreId, vb.voteCounter);
        }
       displayCurrentTally();
    }

    private void displayCurrentTally() {
        System.out.println(">>> CURRENT GLOBAL TALLY <<<");
        String winner = "Tie/None";
        int maxVotes = -1;

        for (var entry : totalVoteResult.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }
        System.out.println("Current Winner: " + winner.toUpperCase());
    }

}
