/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributeudnumericalintegration;

import java.security.InvalidParameterException;
import java.util.concurrent.Callable;
import java.util.function.Function;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author fvasconcellos
 */
public class IntegrationWorker implements Callable<IntegrationResult>{ 
    private IntegratorStrategy integrationStrategy;
    private Function<Double, Double> f;
    private String equation;
    private double a;
    private double b;
    private int n;
    
    public IntegrationWorker(IntegratorStrategy intStrat,String equation, double a, double b, int n){
        this.integrationStrategy = intStrat;
        this.equation = equation;
        this.a = a;
        this.b = b;
        this.n = n;
    }
    
    private Function<Double,Double> parseEquation(){
         final Expression expression = new ExpressionBuilder(this.equation)
                    .variable("x")
                    .build();
        return (Double xValue) -> { return expression.setVariable("x", xValue).evaluate();};
    } 
    
    
    public IntegrationResult call() throws InvalidParameterException{
        try {
            long beginTime = System.nanoTime();
            this.f = this.parseEquation();
            double result = this.integrationStrategy.calculateIntegral(f, a, b, n);
            String name = this.integrationStrategy.getName();
            long endTime = System.nanoTime();
            long diff = endTime - beginTime;
            
            return new IntegrationResult(result, diff, name);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
}
