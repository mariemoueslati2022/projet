/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Commande;
import Entities.commande_panier;
import Entities.panier;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import Services.Service;

/**
 * FXML Controller class
 *
 * @author Abderrazekbenhamouda
 */
public class PasserCommandeController implements Initializable {

    @FXML
    private ChoiceBox<String> checbox;
    @FXML
    private Label username;
    @FXML
    private Label prix;
    
    private Float somme = 0f ;
    
    Service s = new Service ();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            username.setText("Sarra");
            List<panier> lpanier = s.getAllPanier();
            
            for(panier p : lpanier){
            
            somme = ((s.get_produit(p.getId_produit()).getPrix())*p.getQuantité())+somme ;
            }
            prix.setText(String.valueOf(somme));
           checbox.getItems().addAll("en ligne","Espéce");


            
            
        } catch (SQLDataException ex) {
            Logger.getLogger(PasserCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void PasserCommande(ActionEvent event) throws SQLException {
        
        
        Commande c = new Commande();
        c.setPrix(somme);
        c.setId_user(1);
        c.setType(checbox.getValue());
        c.setEtat("ATT");
        s.addCommande(c);
        List<panier> lpanier = s.getAllPanier();
        
        for(panier p : lpanier){
            commande_panier cp = new commande_panier();
            cp.setId_produit(p.getId_produit());
            cp.setId_commande(s.getIdCommande()); 
            s.addCommandePanier(cp);
            s.deletePanier();

        }
            Notifications notificationBuilder = Notifications.create()
                .title("Done").text("votre Commande est passé avec succes").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.CENTER_LEFT)
               .onAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent event)
                   {
                       System.out.println("clicked ON ");
               }});
       notificationBuilder.darkStyle();
       notificationBuilder.show();
        
        
                     Parent root;
           try {
              root = FXMLLoader.load(getClass().getResource("/gui/ShowCommandeClient.fxml"));
               Stage myWindow = (Stage) prix.getScene().getWindow();
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
