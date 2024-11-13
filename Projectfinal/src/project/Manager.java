/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author Mohamed Abouzaid
 */
// Abstraction function
// Af(m) :  only one object of Manager
//      m:  admin is Manager, Password is admin
//Rep invariant
//RI(c)  : username is admin
//         password is admin
//         role is Manager
public class Manager extends Profile{
    
    //overview: The purpose of this class is to initialize only one admin using the singleton method
    // the manager has admin as username, admin as password and Manager as role.
    
    private static Manager s = null;
    private final static String role = "Manager";
    
    private Manager(){
        // creator
        super("admin", "admin");
    }
    
    public static Manager getInstance(){
        // singleton method
        if (s == null){
            s = new Manager();
        }
        return s;
    }

    @Override
    public String getRole() {
        //Effects: returns the role which is "Manager"
        return role;
    }
    
    @Override
    public String toString(){
        return super.getUsername() + ":" + super.getPassword() +" "+ role;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean repOK(){
        return super.getPassword().equals("admin") && super.getUsername().equals("admin") && role.equalsIgnoreCase("Manager");
    }
    
}
