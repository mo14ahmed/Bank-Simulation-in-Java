/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author Mohamed Abouzaid
 */
public abstract class Level {
    //OverView: This class is an abstract class which is used to be inhertied
    //The other levels inherit this class and override the abstarct functions
    
    public abstract Level changeLevel(double balance);
    public abstract double onlinePurchase(double cost);
    
}
