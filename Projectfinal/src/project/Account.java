/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author Mohamed Abouzaid
 */
//Abstrction function:
//Af(c): c has an Integer accNumber
//       c has a double balance
//     c : accNumber : balance
//Rep Invariant
//RI(c):  accNumber must be int 
//        balance must be double greater than zero 

public class Account {
    
    //overview: Account is mutuable, the balance could be changed through deposit
    // withdraw. The Account class has a accNumber which is final could not be changed once created.
    //Also, the balance which is used in the functions stated before.
    
    private final int accNumber;
    private double balance;
    
    public Account(int accNumber, double initialBalance){
        //Creator
        this.balance = initialBalance;
        this.accNumber = accNumber;
        
    }
    
    public void setBalance(double balance){
        //Requires: a double balance more than zero
        //Modifies: balance  
        //Effects: sets the new balance
        this.balance = balance;
    }
    
    public double getBalance(){
        //Effects: returns the balance
        return balance;
    }
    
    public void withdraw(double amount){
        //Requires: a double amount less than the balance
        //Modifies:modifies the balance
        //Effects: subtract the amount from the balance
    if (amount >0){
            if (balance-amount >=0) {
                balance -= amount;
            }
    }
    }
    public int getAccNumber(){
        //Effects: returns the account number
        return accNumber;
    }
    
    public void deposit(double amount){
        //Requires: a double amount 
        //Modifies:modifies the balance
        //Effects: add the amount from the balance
        if (amount > 0){
            balance+= amount;
        }
    }
    @Override
   public String toString(){
       return accNumber +" : "+ balance;
   }
    public boolean repOK(){
        return (Integer)accNumber instanceof Integer && balance >0;
    }
}
