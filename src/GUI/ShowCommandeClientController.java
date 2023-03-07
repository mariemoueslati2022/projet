/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Commande;
import Entities.ProduitS;
import Entities.commande_panier;
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
 * @author HP
 */
public class ShowCommandeClientController implements Initializable {

    @FXML
    private ListView<Commande> listView;
    
    ObservableList<Commande> data;
    
    Service ds = new Service();
    
    public static int idcommande ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        
        List<Commande> listc = new ArrayList<>();
        try {
            for (Commande p : ds.getMesCommande(1)){
                
                
                listc.add(ds.get_Commande(p.getId_commande()));
            }
            
         ObservableList<Commande> observableList = FXCollections.observableList(listc);
        data = (ObservableList<Commande>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<Commande> param) -> new ListViewCommandeClient());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Consulter(ActionEvent event) {
        
         ObservableList<Commande> e = listView.getSelectionModel().getSelectedItems();
         for (Commande m : e) {
             idcommande =m.getId_commande();
         }
         
            Parent root;
               try {
               root = FXMLLoader.load(getClass().getResource("/gui/ShowMesCommande.fxml"));
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
