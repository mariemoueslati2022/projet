/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import Entities.ProduitS;
import Entities.panier;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import Services.Service;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowPanierController implements Initializable {

    @FXML
    private ListView<ProduitS> listView;
   
    ObservableList<ProduitS> data;
    
    public static int idE ;
    
    Service ds = new Service();
    

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        List<ProduitS> listProduit = new ArrayList<>();
        
        
        try {
            for (panier p : ds.getAllPanier()){
                listProduit.add(ds.get_produit(p.getId_produit()));
            }
            
         ObservableList<ProduitS> observableList = FXCollections.observableList(listProduit);
        data = (ObservableList<ProduitS>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<ProduitS> param) -> new ListViewProduit());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
        
        // TODO
        

        
        
        // TODO
    }    




    @FXML
    private void PasserCommande(ActionEvent event) throws SQLDataException {

                          Parent root;
                       try {
              root = FXMLLoader.load(getClass().getResource("/gui/PasserCommande.fxml"));
               Stage myWindow = (Stage) listView.getScene().getWindow();
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
    private void AnnulerCommande(ActionEvent event) throws SQLDataException {
        
             ds.deletePanier();
               Parent root;
                       try {
              root = FXMLLoader.load(getClass().getResource("/gui/ShowProduit.fxml"));
               Stage myWindow = (Stage) listView.getScene().getWindow();
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
    private void SuprimerItem(ActionEvent event) throws SQLDataException {
        
                ObservableList<ProduitS> e = listView.getSelectionModel().getSelectedItems();
         for (ProduitS m : e) {
             ds.deleteFromPanier(m.getId());
         }
         
                  Parent root;
                       try {
              root = FXMLLoader.load(getClass().getResource("/gui/ShowPanier.fxml"));
               Stage myWindow = (Stage) listView.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
               }


        
    }




    }

    

