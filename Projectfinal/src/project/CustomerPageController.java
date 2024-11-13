/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project;

import java.awt.MultipleGradientPaint.CycleMethod;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Abouzaid
 */
public class CustomerPageController implements Initializable {
    final ObservableList<String[]> purchaseList = FXCollections.observableArrayList();
    private double purchasePrice;
    Account customerAcc;
    Customer customer;
    Level accountLevel;
    @FXML 
    private AnchorPane card;
    @FXML
    private Label accountNumber;
    @FXML
    private Label balance;
    @FXML
    private Label stateLevel;
    @FXML 
    private TextField depositAmount;
    @FXML
    private TextField withdrawAmount;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private TableView table;
    @FXML
    TableColumn<String[], String> items;
    @FXML
    TableColumn<String[], String> price;
    @FXML
    private Label totalLabel;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        items.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue()[0])); // Name
        price.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue()[1]));
        table.setItems(purchaseList);
    }    
    
    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
       Scene scene = new Scene(root);
            // Get the controller of the Manager scene
                
       Stage primaryStage = (Stage) card.getScene().getWindow();
       primaryStage.setTitle("Login Page");
       primaryStage.setScene(scene);
       primaryStage.show();
    }
    public void displayUser(Customer c){
        customer = c;
        customerAcc = customer.getAccount();
        accountLevel = customer.getLevel();
        if (accountLevel instanceof SilverLevel){
            card.setStyle("-fx-background-color:#C4C4C4; -fx-background-radius:10px; -fx-border-radius:10px");
            stateLevel.setText("Silver");
        }else if(accountLevel instanceof GoldLevel){
            card.setStyle("-fx-background-color:#FFD700; -fx-background-radius:10px; -fx-border-radius:10px");
            stateLevel.setText("Gold");
           
        }else if(accountLevel instanceof PlatinumLevel){
            card.setStyle("-fx-background-color:#E5E4E2;-fx-background-radius:10px; -fx-border-radius:10px");
            stateLevel.setText("Platinum");
           
        }
        accountNumber.setText(customerAcc.getAccNumber()+"");
        balance.setText(""+customerAcc.getBalance());
  
    }
    public void withDraw() throws IOException{
        try{
            double amount = Double.parseDouble(withdrawAmount.getText());
            customerAcc.withdraw(amount);
            customer.setLevel(accountLevel.changeLevel(customerAcc.getBalance()));
            String directory = System.getProperty("user.dir");
            Path path = Paths.get(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            Files.delete(path);
            File file = new File(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            file.createNewFile();
            withdrawAmount.setText("");
            depositAmount.setText("");
            try (PrintWriter pw = new PrintWriter(file)) {
               pw.println(customer.getUsername()+" "+customer.getPassword() +" "+ customerAcc.getAccNumber() +" "+ customerAcc.getBalance() +" Customer");
            }
            displayUser(customer);
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("The amount should be Positive");
            alert.showAndWait();
            withdrawAmount.setText("");
            depositAmount.setText("");
        }
    }
    public void deposit() throws IOException{
     try{
            double amount = Double.parseDouble(depositAmount.getText());
            customerAcc.deposit(amount);
            customer.setLevel(accountLevel.changeLevel(customerAcc.getBalance()));
            String directory = System.getProperty("user.dir");
            Path path = Paths.get(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            Files.delete(path);
            File file = new File(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            file.createNewFile();
            withdrawAmount.setText("");
            depositAmount.setText("");
            try (PrintWriter pw = new PrintWriter(file)) {
               pw.println(customer.getUsername()+" "+customer.getPassword() +" "+ customerAcc.getAccNumber() +" "+ customerAcc.getBalance() +" Customer");
            }
            displayUser(customer);
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("The amount should be positive");
            alert.showAndWait();
            withdrawAmount.setText("");
            depositAmount.setText("");
        }
    }
    public void onlinePurchase() throws IOException{
        double finalCost= customer.getLevel().onlinePurchase(purchasePrice);
        if (finalCost>customer.getAccount().getBalance()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("You can not purchase upper your limit");
            alert.showAndWait();
            purchasePrice=0;
            purchaseList.clear();
            table.getItems().clear();
        }else if(finalCost<50){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Header");
            alert.setContentText("Purchase cost must be more than 50");
            alert.showAndWait();
            purchasePrice=0;
            purchaseList.clear();
            table.getItems().clear();
        }else{
            customerAcc.withdraw(finalCost);
            customer.setLevel(accountLevel.changeLevel(customerAcc.getBalance()));
            String directory = System.getProperty("user.dir");
            Path path = Paths.get(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            Files.delete(path);
            File file = new File(directory+"/src/project/dataBase/"+customer.getUsername()+".txt");
            file.createNewFile();
            withdrawAmount.setText("");
            depositAmount.setText("");
            try (PrintWriter pw = new PrintWriter(file)) {
               pw.println(customer.getUsername()+" "+customer.getPassword() +" "+ customerAcc.getAccNumber() +" "+ customerAcc.getBalance() +" Customer");
            }
            displayUser(customer);
            purchasePrice=0;
            purchaseList.clear();
            table.getItems().clear();
        }
         totalLabel.setText("Total: ");
    }
    @FXML
    public void addTicket(){
        purchaseList.add(new String[]{"Ticket", "50"});
        purchasePrice += 50;
        totalLabel.setText("Total: "+purchasePrice);
    }
    @FXML
    public void addCoffee(){
        purchaseList.add(new String[]{"Coffer", "7.5"});
        purchasePrice += 7.5;
        totalLabel.setText("Total: "+purchasePrice);
    }
    @FXML
    public void addGrocery(){
        purchaseList.add(new String[]{"Grocery", "200"});
        purchasePrice += 200;
        totalLabel.setText("Total: "+purchasePrice);
    }
    @FXML
    public void addMakeup(){
        purchaseList.add(new String[]{"Makeup", "77"});
        purchasePrice += 77;
        totalLabel.setText("Total: "+purchasePrice);
    }
    @FXML
    public void addMobile(){
        purchaseList.add(new String[]{"Mobile", "1096.9"});
        purchasePrice += 1096.9;
        totalLabel.setText("Total: "+purchasePrice);
    }
    @FXML
    public void addShirt(){
        purchaseList.add(new String[]{"Shirt", "16.7"});
        purchasePrice += 16.7;
        totalLabel.setText("Total: "+purchasePrice);
    }
}
