/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.distributeudnumericalintegration;

import java.util.function.Function;

/**
 *
 * @author fvasconcellos
 */
public interface IntegratorStrategy {
    public double calculateIntegral(Function<Double, Double> f, double a, double b, int n);
    public String getName();
}
