/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import Entities.Commande;
import Entities.ProduitS;
import Entities.commande_panier;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import Services.Service;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowCommandeController implements Initializable {

    @FXML
    private ListView<ProduitS> listView;
   
    ObservableList<ProduitS> data;
    
    
    Service ds = new Service();
    
    @FXML
    private Label prix;
    @FXML
    private Label username;
    @FXML
    private Label etat;
    @FXML
    private Button Accepter;
    @FXML
    private Button Refuser;
    

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        if (ds.get_Commande(ListeCommandeController.ide).getEtat().equals("Accepter") || ds.get_Commande(ListeCommandeController.ide).getEtat().equals("Refuser")){
            
        //Accepter.setVisible(true);
        //Refuser.setVisible(true);

        }
        
        
        List<ProduitS> listProduit = new ArrayList<>();
        
        username.setText("sarra");
        prix.setText(String.valueOf(ds.get_Commande(ListeCommandeController.ide).getPrix()));
        etat.setText(ds.get_Commande(ListeCommandeController.ide).getEtat());
        
        try {
            for (commande_panier p : ds.getAllCommandePanier(ListeCommandeController.ide)){
                
                
                listProduit.add(ds.get_produit(p.getId_produit()));
            }
            
         ObservableList<ProduitS> observableList = FXCollections.observableList(listProduit);
        data = (ObservableList<ProduitS>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<ProduitS> param) -> new ListViewProduit());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

       
    }    

    @FXML
    private void Accepter(ActionEvent event) throws SQLDataException {
            
        ds.UpdateCommandeEtat(ListeCommandeController.ide, "Accepter") ;
                      System.out.println("controller.ShowCommandeController.Accepter()"+ListeCommandeController.ide);

               Parent root;
               try {
               root = FXMLLoader.load(getClass().getResource("/gui/ShowCommande.fxml"));
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
    private void Refuser(ActionEvent event) throws SQLDataException {
        
              
              ds.UpdateCommandeEtat(ListeCommandeController.ide, "Refuser") ;

        
               Parent root;
               try {
               root = FXMLLoader.load(getClass().getResource("/gui/ShowCommande.fxml"));
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

    

