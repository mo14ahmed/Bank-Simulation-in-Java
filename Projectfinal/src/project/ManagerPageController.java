/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Abouzaid
 */
public class ManagerPageController {
    private final ObservableList<String[]> data = FXCollections.observableArrayList();
    public ArrayList<Profile>  profilesArray = new ArrayList<>();
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField accNumber;
    @FXML
    private TextField balance;
    @FXML
    public TableView<String[]> table;
    @FXML
    private Button display;
    @FXML
    TableColumn<String[], String> usernameColumn;
    @FXML
    TableColumn<String[], String> accNumColumn;
    
    @FXML
    public void initialize() throws IOException {
        
        readFiles();
        // Initialize columns
        usernameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue()[0])); // Name
        accNumColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue()[1]));

        // Set the items in the table
        table.setItems(data);
    }
    @FXML
    public void displayAccounts() throws FileNotFoundException{
        table.getItems().clear();
        readFiles();
        for (Profile p: profilesArray){
            if (p instanceof Customer){
                Customer holder = (Customer) p;
                data.add(new String[] {holder.getUsername(), ""+holder.getAccount().getAccNumber()});
            }
        }
        
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
                    Scanner input = new Scanner(f);
                    String readFile = input.nextLine();
                    String[] arguments = readFile.split(" ");
                    if (arguments[4].equals("Customer")){
                        for(Profile p: profilesArray){
                            if (p.getUsername().equals(arguments[0])){
                                state =true;
                            }
                        }
                        if (state==false){
                          profilesArray.add(new Customer(arguments[0], arguments[1], Integer.parseInt(arguments[2]), Double.parseDouble(arguments[3])));
                    }
                    }
                    input.close();
                }
                
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid username or password");
        }
    }
    public void delete() throws IOException{
       String[] selected =  table.getSelectionModel().getSelectedItem();
       
       if (selected == null){
       }else{
           table.getItems().remove(selected);
           for (Profile p: profilesArray){
               if (selected[0].equals(p.getUsername())){
                   String directory = System.getProperty("user.dir");
                   Path path = Paths.get(directory+"/src/project/dataBase/"+selected[0]+".txt");
                   Files.delete(path);
                   
               }
           }
       }
       profilesArray.clear();
       readFiles();
   }
   public void createCustomer() throws IOException{
       String userName = username.getText();
       String passWord =  password.getText();
       int accountNumber = Integer.parseInt(accNumber.getText());
       
       boolean flag = true;
       for (Profile p: profilesArray){
           if (userName.equalsIgnoreCase(p.getUsername()) || accountNumber==((Customer)p).getAccount().getAccNumber()){
               flag = false;
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Information Dialog");
               alert.setHeaderText("Information Header");
               alert.setContentText("UserName is used");
               alert.showAndWait();
               username.setText("");
               password.setText("");
               accNumber.setText("");
               balance.setText("");
               break;
           }
       }
       if (flag){
           profilesArray.add(new Customer(userName, passWord, accountNumber, 100));
           String directory = System.getProperty("user.dir");
           File file = new File(directory+"/src/project/dataBase/"+userName+".txt");
           file.createNewFile();
           try (PrintWriter pw = new PrintWriter(file)) {
               pw.println(userName+" "+passWord +" "+ accountNumber +" "+ 100 +" Customer");
           }
           username.setText("");
           password.setText("");
           accNumber.setText("");
           balance.setText("");
       }
   
       displayAccounts();
   }
   public void logout() throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
       Scene scene = new Scene(root);
            // Get the controller of the Manager scene
                
       Stage primaryStage = (Stage) display.getScene().getWindow();
       primaryStage.setTitle("Login Page");
       primaryStage.setScene(scene);
       primaryStage.show();
   }
}
    

