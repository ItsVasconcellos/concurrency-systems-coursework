/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributedvoting;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fvasconcellos
 */
public class VoteBatch implements Serializable {
    public String centreId;
    public Map<String,Integer> voteCounter;

    public String getCentreId() { return centreId; }

    public void setCentreId(String centreId) { this.centreId = centreId; }

    public Map<String, Integer> getVoteCounter() {
        return voteCounter;
    }

    public void setVoteCounter(Map<String, Integer> voteCounter) {
        this.voteCounter = voteCounter;
    }
    
    public VoteBatch( ){
    }
    
    public VoteBatch(String centreId, Map<String, Integer> voteCounter ){
        this.centreId = centreId;
        this.voteCounter = voteCounter;
    }
    
}
