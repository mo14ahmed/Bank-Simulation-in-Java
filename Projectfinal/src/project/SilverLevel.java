/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author Mohamed Abouzaid
 */
public class SilverLevel extends Level {
     @Override
    public Level changeLevel(double balance) {
        //Requires: the balance of the account which is double
        //Effects: returns the new level based on the balance in the account
        if (balance>=20000){
            return new PlatinumLevel();
        }else if (20000>balance && balance>=10000){
            return new GoldLevel();
        }else {
            return new SilverLevel();
        }
    }

    @Override
    public double onlinePurchase(double cost) {
        //Requires: the cost which is double 
        //Effects: retuns the new cost after adding the fee of the level
        if (cost>50){
            return cost +20;
        }
        return -1;
    }
}
