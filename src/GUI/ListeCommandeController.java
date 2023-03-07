/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Commande;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Services.Service;

/**
 * FXML Controller class
 *
 * @author Abderrazekbenhamouda
 */
public class ListeCommandeController implements Initializable {

    @FXML
    private TableView<Commande> table;
    @FXML
    private TableColumn<Commande, Integer> ref;
    @FXML
    private TableColumn<Commande, Float> prix;
    
    private ObservableList<Commande> UserData = FXCollections.observableArrayList();
    
    Service s = new Service();
    
    public static int ide ;
    @FXML
    private TableColumn<Commande,String> etat;
    @FXML
    private TextField rechercher;
    
   
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                try {
            List<Commande> listb= new ArrayList<Commande>();
            
            listb = s.getAllCommande();
     
            System.out.println("hello"+listb);
            UserData.clear();
            UserData.addAll(listb);
            table.setItems(UserData);
            
            ref.setCellValueFactory
                      (
                              new PropertyValueFactory<>("id_commande")
                      );
            prix.setCellValueFactory
                      (
                              new PropertyValueFactory<>("prix")
                      );
             etat.setCellValueFactory
                      (
                              new PropertyValueFactory<>("etat")
                      );
            
        } catch (SQLDataException ex) {
            Logger.getLogger(ListeCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }    

    @FXML
    private void consulter(ActionEvent event) {
        
                      ide =  table.getSelectionModel().getSelectedItem().getId_commande();     

             Parent root;
           try {
              root = FXMLLoader.load(getClass().getResource("/gui/ShowCommande.fxml"));
               Stage myWindow = (Stage) table.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
    }

    @FXML
    private void rechercher(ActionEvent event) {
        
          ref.setCellValueFactory
                      (
                              new PropertyValueFactory<>("id_commande")
                      );
            prix.setCellValueFactory
                      (
                              new PropertyValueFactory<>("prix")
                      );
             etat.setCellValueFactory
                      (
                              new PropertyValueFactory<>("etat")
                      );
        
                  
            List<Commande> list= new ArrayList<Commande>();
            
            //tableview.setItems(observablelist);
            
            FilteredList<Commande> filtredData= new FilteredList<>(UserData, b ->true);
            rechercher.textProperty().addListener((observable,oldValue,newValue) -> {
                Predicate<? super Commande> Reservation;
                filtredData.setPredicate((Commande e) -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    if(String.valueOf(e.getPrix()).toLowerCase().indexOf(lowerCaseFilter) != -1 ){
                        return true;
                    }
                    else if (e.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    }
                    
                    else
                        return false;
                } );
            });
             // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Commande> sortedData = new SortedList<>(filtredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
        
    }
    
}
