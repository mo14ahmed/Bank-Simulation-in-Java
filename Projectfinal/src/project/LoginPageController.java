/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Abouzaid
 */
public class LoginPageController  {
   public ArrayList<Profile> profilesArray = new ArrayList<>();
   @FXML
   TextField username;
   @FXML
   TextField role;
   @FXML
   PasswordField password;
   @FXML
   Button loginButton;
   @FXML
   public void handle(ActionEvent event) throws IOException {
            boolean flag = true;
            try {
                readFiles();
            } catch (FileNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Information Header");
                alert.setContentText("File Not Found");
                alert.showAndWait();
            }
            
            String enteredUserName = username.getText();
            String enteredPassword = password.getText();
            String enteredRole = role.getText();
            
            if ( enteredUserName.equals("admin")&& enteredPassword.equals("admin") && enteredRole.equals("Manager")){
                flag = false;
                Parent root = FXMLLoader.load(getClass().getResource("managerPage.fxml"));
                Scene scene = new Scene(root);
            // Get the controller of the Manager scene
                
                Stage primaryStage = (Stage) loginButton.getScene().getWindow();
                primaryStage.setTitle("Manager");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            for (Profile p: profilesArray){
                if (enteredUserName.equalsIgnoreCase(p.getUsername())&& enteredPassword.equals(p.getPassword()) && enteredRole.equals("Customer")){
                    flag = false;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("customerPage.fxml"));
                    Parent root = loader.load();
                    CustomerPageController controller = loader.getController();
                    controller.displayUser((Customer)p);
                    Scene scene = new Scene(root);
                    Stage primaryStage = (Stage) loginButton.getScene().getWindow();
                    primaryStage.setTitle("Customer Page");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    break;
                }
            }
            if (flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("Invalid inputs");
            alert.showAndWait();}
    }
    public void readFiles() throws FileNotFoundException{
        try{
            String directory = System.getProperty("user.dir");
            File file = new File(directory+"/src/project/dataBase");
            File[] fileArray = file.listFiles();
            boolean state = false;
            for(File f: fileArray){
                if (f.getName().equals("admin.txt")){}
                else{
                    try (Scanner input = new Scanner(f)) {
                        String readFile = input.nextLine();
                        String[] arguments = readFile.split(" ");
                        if (arguments[4].equals("Customer")){
                            for(Profile p: profilesArray){
                                if (p.getUsername().equals(arguments[0]) || ((Customer)p).getAccount().getAccNumber()== Integer.parseInt(arguments[2])){
                                    state =true;
                                }
                            }
                            if (state==false){
                                profilesArray.add(new Customer(arguments[0], arguments[1], Integer.parseInt(arguments[2]), Double.parseDouble(arguments[3])));
                            }
                        }
                    }
                }
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("File not available");
            alert.showAndWait();
        }
    }
    
}
