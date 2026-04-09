/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributeudnumericalintegration;

import java.util.function.Function;

/**
 *
 * @author fvasconcellos
 */
public class RiemannSumStrategy implements IntegratorStrategy{

    @Override
    public double calculateIntegral(Function<Double, Double> f, double a, double b, int n) {
        double sum = 0;
        double dx = (b-a)/n;
        double x = 0;
        for(int i = 0; i < n; i++){
            x = (dx*i) + a;
            sum += f.apply(x);
        }
        return sum*dx;
    }
    
    public String getName(){
        return "Riemman's sum";
    }
    
}
