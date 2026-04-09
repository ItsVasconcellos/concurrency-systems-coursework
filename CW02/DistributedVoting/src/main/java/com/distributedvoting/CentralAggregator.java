/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributedvoting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.util.Collections;
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
    private int totalVoteCentre;
    private int RecievedVoteCente;
    
    public CentralAggregator(String queueName, Channel ch, int totalVoteCentre){
        this.queueName = queueName;
        this.chanell = ch;
        this.totalVoteCentre = totalVoteCentre;
        this.voteCenterPartialResult = new HashMap<String, Map<String, Integer>>();
        this.totalVoteResult = new HashMap<String, Integer>();
        this.RecievedVoteCente = 0;
    }
    
    public void processVotes(String message) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();        
        VoteBatch vb = mapper.readValue(message, VoteBatch.class);
        
        // Verify if that voteCentre has ended its votes and if yes, if all voteCentres have finished.
        if(vb.isAllVotesProcessed()){
            this.RecievedVoteCente += 1;
            this.verifyIfElectionsEnded();
            return;
        }
        
        // Get voteCentreId and the map of votes
        String centreId = vb.getCentreId();
        Map<String,Integer> voteMap = vb.getVoteCounter();
        
        // Add votes to the total amount of votes
        voteMap.forEach((k,v) -> {
            if(parties.contains(k)) {
                totalVoteResult.merge(k, v, Integer::sum);
            }
            else {
                System.err.println("  [!] Rejected votes for unknown party: " + k);
            }
        });
        
        // Add votes to the VoteCentre
        this.addVotesToVoteCentreMap(centreId, voteMap);
        displayCurrentTally();  
    }

    private void addVotesToVoteCentreMap(String centreId, Map<String,Integer> voteMap){
        Map<String, Integer> centerCount = this.voteCenterPartialResult.get(centreId);
        if(centerCount != null ){
            voteMap.forEach((k,v) -> {
                if(parties.contains(k)) {
                    centerCount.merge(k, v, Integer::sum);
                }
                else {
                    System.err.println("  [!] Rejected votes for unknown party: " + k);
                }
            });
        }
        else{
            this.voteCenterPartialResult.put(centreId,voteMap);
        }
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
    
    public void verifyIfElectionsEnded(){
        if(this.RecievedVoteCente == this.totalVoteCentre){
            this.printFinalElectionResult();
        }
    }

    
    public void printFinalElectionResult() {
        System.out.println("\n********************************************");
        System.out.println("         OFFICIAL FINAL ELECTION RESULTS     ");
        System.out.println("********************************************");
                
        String winnerName = totalVoteResult.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        int totalVoterTurnout = totalVoteResult.values().stream().reduce(totalVoteCentre, Integer::sum);
        double averageVotesPerCentre = 0;
        if (!voteCenterPartialResult.isEmpty()) {
            averageVotesPerCentre = (double) totalVoterTurnout / voteCenterPartialResult.size();
        }System.out.println("FINAL RESULTS BY PARTY:");
        
        Map<String,Integer> totalVotePerCentre = new HashMap();
        this.voteCenterPartialResult.forEach((k,v) -> {
            int totalVotes = v.values().stream().reduce(totalVoteCentre, Integer::sum);
            totalVotePerCentre.put(k, totalVotes);
        });
        
        String centreWithMostVotes = Collections.max(totalVotePerCentre.entrySet(), Map.Entry.comparingByValue()).getKey();
                
        totalVoteResult.forEach((party, total) -> System.out.println(" - " + party + ": " + total));

        System.out.println("\n--- THE WINNER: " + winnerName.toUpperCase() + " ---");
        System.out.println("\nSTATISTICS:");
        System.out.println(" - Total Voter Turnout: " + totalVoterTurnout);
        System.out.printf(" - Average Votes Cast per Centre: %.2f\n", averageVotesPerCentre);
        System.out.println(" - Vote Centre with most votes: " + centreWithMostVotes + "\n");
    }

}
