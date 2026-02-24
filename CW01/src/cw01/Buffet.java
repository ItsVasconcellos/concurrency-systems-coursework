package cw01;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fvasconcellos
 */
public class Buffet {
    private int coffeeCups;
    private int teaCups;
    private int cakeSlices;

    synchronized void refillCoffeCups(int coffeeCups) {
        if (coffeeCups <= 0) {
            throw new IllegalArgumentException("Cannot refill 0 or negative cups of coffee into the buffet");
        }
        this.coffeeCups += coffeeCups;
    }

    synchronized void refillTeaCups(int teaCups) {
        if (teaCups <= 0) {
            throw new IllegalArgumentException("Cannot refill 0 or negative cups of tea into the buffet");
        }
        this.teaCups += teaCups;
    }

    synchronized void refillCakeSlices(int cakeSlices) {
        if (cakeSlices <= 0) {
            throw new IllegalArgumentException("Cannot refill 0 or negative slices of cake into the buffet");
        }
        this.cakeSlices += cakeSlices;
    }

    synchronized void getOrder(int cakeSlices, int teaCups, int cofeeeCups) {
        if (cakeSlices == 1) {
            while (this.cakeSlices <= 0) {
                wait();
            }
            --cakeSlices;
            notifyAll();
        }
        if (teaCups == 1) {
            while (this.teaCups <= 0) {
                wait();
            }
            --teaCups;
            notifyAll();
        }
        if (cofeeeCups == 1) {
            while (this.teaCups <= 0) {
                wait();
            }
            --teaCups;
            notifyAll();
        }
        return;
    }

    synchronized void takeOrder(int orderType) {
        switch (orderType) {
            case 1:
                getOrder(cakeSlices = 0, teaCups = 0, coffeeCups = 1);
                break;
            case 2:
                getOrder(cakeSlices = 0, teaCups = 1, coffeeCups = 0);
                break;
            case 3:
                getOrder(cakeSlices = 1, teaCups = 1, coffeeCups = 0);
                break;
            case 4:
                getOrder(cakeSlices = 1, teaCups = 0, coffeeCups = 1);
                break;
            case 5:
                getOrder(cakeSlices = 1, teaCups = 0, coffeeCups = 0);
                break;
            default:
                throw new IllegalArgumentException("sss");
                break;
        }
    }

}
