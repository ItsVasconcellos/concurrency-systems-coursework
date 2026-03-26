/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributeudnumericalintegration;

/**
 *
 * @author fvasconcellos
 */
public class IntegrationResult {
    public final double result;
    public final long time_diff;
    public final String name;
    
    public IntegrationResult(double result, long time_diff, String name){
        this.result = result;
        this.time_diff = time_diff;
        this.name = name;
    }
}
