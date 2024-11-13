package project;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mohamed Abouzaid
 */
//Abstraction Functiom
// Af(c) : c has String username 
//         c has String password
//         username : password
//Rep Inavriant
// RI(c):  username is String
//         password is String
//         username is unique
public abstract class Profile {
    
    private String username;
    private String password;
    
    public Profile(String username, String password){
        //Creator
        this.password = password;
        this.username = username;
    }
    
    public String getUsername(){
        //Effects: returns the username
        return this.username;
    }
    public void setUsername(String username){
        //Requires: a non null String
        //Modifies: username
        //Effects: sets the new value for username
        this.username = username;
    }
    public String getPassword(){
        //Effects: returns the password
        return this.password;
    }
    public void setPassword(String password){
        //Requires: a non null String
        //Modifies: password 
        //Effects: sets the new value of the password
        this.password = password;
    }
    public abstract String getRole();
    
    @Override
    public String toString(){
        return username+":"+ password;
    }
    public boolean repOK(){
        return username instanceof String && password instanceof String;
    }
}
