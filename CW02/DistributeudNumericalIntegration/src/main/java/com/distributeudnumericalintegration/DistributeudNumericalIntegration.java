/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.distributeudnumericalintegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.InputMismatchException;

/**
 *
 * @author fvasconcellos
 */
public class DistributeudNumericalIntegration {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scan = new Scanner(System.in);
        ScanParser sp = new ScanParser(scan);
        System.out.println("Welcome to the Distributed Numerical Integration System!");
        String f = sp.VerifyParseEquation();
//        Function<Double,Double> f = (x) -> Math.pow(x,2);
        System.out.println("Now, you will need to insert the interval to integrate the function, [A,B]");
        System.out.println("Insert the value for a");
        Double a = sp.get_bound_interval();
        System.out.println("Insert the value for b");
        Double b = sp.get_bound_interval();
        System.out.println("Now, you will need to insert how many portions should the method calculate. Remember that more means more precise");
        int n = sp.get_n();
        
        List<IntegratorStrategy> integrators = List.of(new RiemannSumStrategy(),new TrapezoidalRuleStrategy());
        
        ExecutorService executor = Executors.newFixedThreadPool(integrators.size());
        
        // Results 
        List<Future<IntegrationResult>> futures = new ArrayList<>();
        for(IntegratorStrategy i: integrators ){
            futures.add(executor.submit(new IntegrationWorker(i, f, a, b, n)));
        }
        
        
        for (Future<IntegrationResult> future : futures) {
            IntegrationResult result = future.get();

            System.out.println(result.name);
            System.out.println("Result: " + result.result);
            System.out.println("Time (ms): " + result.time_diff / 1_000_000.0);
            System.out.println();
        }

        executor.shutdown();
    }
}
