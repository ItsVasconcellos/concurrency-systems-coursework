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
public class TrapezoidalRuleStrategy implements IntegratorStrategy{
    @Override
    public double calculateIntegral(Function<Double, Double> f, double a, double b, int n) {
        double dx = (b-a)/n;
        double sum = 0;
        double y_k;
        double y_k1;
        double x_k;
        double x_k1;
        double diff_k;
        for(int i = 0; i < n; i++){
            x_k = (dx*i)+a;
            x_k1 = (dx*(i+1))+a;
            y_k = f.apply(x_k);
            y_k1 = f.apply(x_k1);
            diff_k = y_k1-y_k;
            sum += (y_k+y_k1)/2;
        }
        return dx*sum;
    }
    
    public String getName(){
        return "Trapezoidal Rule";
    }

}
