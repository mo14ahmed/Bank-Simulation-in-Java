package project;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mohamed Abouzaid
 */
// Abstraction function
// Af(c) : c has an accout
//         c has a fixed String role of "Customer"
//         c has a level 
//    c :  username is Customer : password
//Rep invariant
//RI(c)  : c.role must be "Customer"
//       : c.account must be instanceof Account
//       : c.level must be instanceof Level
public class Customer extends Profile{
    //overview: The purpose of this class is to initialize the customer and its
    // properties. Each customer has a account which reprsents the bank account of
    // the customer. Each customer has a level which represents the level of its bank
    // account. Each customer has a role which is always 'Customer'
    //This class is mutuable, it is possible to change the value of Level for each Customer
    
    private Account account;
    private final static String role = "Customer";
    private Level levelState;
    
    //Creator 
    public Customer(String username, String password, int accNumber, double balance){
        super(username, password);   
        account =  new Account( accNumber, balance);
        if (balance>=20000){
            levelState= new PlatinumLevel();
        }else if (20000>balance && balance>10000){
            levelState = new GoldLevel();
        }else {
            levelState= new SilverLevel();
        }
    }
    
    @Override
    public String getRole(){
        //Effects:return the role of Customer
        return role;
    }
    public Account getAccount(){
        //Effects:returns the object account of the customer
        return account;
    }
     public Level getLevel(){
        //Effects:returns the object level of the customer
        return this.levelState;
    }
    public void setLevel(Level l){
        //Requires: a non null level obejct
        //Modifies:modifies the level of the customer 
        //Effects: give the customer level the new level state
       this.levelState = l;
    }
    @Override
    public String toString(){
        return super.getUsername()+"is Customer: "+super.getPassword();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean repOK(){
        return role.equals("Customer") && super.getUsername() instanceof String && super.getUsername() != null && super.getPassword() instanceof String;
    }
    
}
