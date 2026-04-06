/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.distributeudnumericalintegration;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

/**
 *
 * @author fvasconcellos
 */
public class ScanParser {
    private Scanner scan;
    
    public ScanParser(Scanner scan){
        this.scan = scan;
    }
    
    public String verifyParseEquation() 
    {   
        Function<Double,Double> f = null;
        boolean parsed =false;
        System.out.println("Insert the equation to be used. Use: *,/ for symbols, for power use **");
        String cleanEquation = "";
        while(!parsed){
            String inputEquation = this.scan.nextLine();
            cleanEquation = inputEquation.replace("**", "^");
            try {
                final Expression expression = new ExpressionBuilder(cleanEquation)
                    .variable("x")
                    .build();
                f = (Double xValue) -> { return expression.setVariable("x", xValue).evaluate(); };
                parsed= true;
            } catch (Exception e) {
                System.out.println("Error trying to parse equation, please use valid values." + e.getMessage() + " - " + e.getClass() );
            }
        }
        return cleanEquation;

    }  
    
    public double getBoundInterval(){
        double n = 0;
        boolean initialized = false;
        while(!initialized){
            try {    
                n = Double.parseDouble(this.scan.nextLine());
                initialized = true;
            } 
            catch(NumberFormatException e) {
                System.out.println("Caught: InputMismatchException -- Please input a valid boolean i.e. 1.1 or 1");
            }
        }
        return n;
    }
    
    public int getNumberOfIterations(){
        boolean positive_n = false;
        int n = -1;
        while(!positive_n){
            System.out.println("Insert an positive integer for the value of N");
            try {    
                n = Integer.parseInt(this.scan.nextLine());
                if(n >= 1) positive_n = true;
                else System.out.println("Please input an integer value >= 1.");
            } 
            catch(NumberFormatException e) {
                System.out.println("Caught: InputMismatchException -- Please input an integer value >= 1. ");
            }
        }
        return n;
    }
}
