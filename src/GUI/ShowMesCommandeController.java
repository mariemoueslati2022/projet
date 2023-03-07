/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import Entities.Commande;
import Entities.ProduitS;
import Entities.commande_panier;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
 * @author HP
 */
public class ShowMesCommandeController implements Initializable {

    @FXML
    private ListView<ProduitS> listView;
    
    ObservableList<ProduitS> data;
    
    Service ds = new Service();
    @FXML
    private Label prix;
    @FXML
    private Label etat;
    @FXML
    private Button Annuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             
        
        
        
        List<ProduitS> listProduit = new ArrayList<>();
        
        prix.setText(String.valueOf(ds.get_Commande(ShowCommandeClientController.idcommande).getPrix()));
        etat.setText(ds.get_Commande(ShowCommandeClientController.idcommande).getEtat());
        
        try {
            for (commande_panier p : ds.getAllCommandePanier(ShowCommandeClientController.idcommande)){
                
                
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
    private void Annuler(ActionEvent event) throws SQLDataException {
        
               ds.UpdateCommandeEtat(ShowCommandeClientController.idcommande, "Annuler") ;
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

    @FXML
    private void Facture(ActionEvent event) throws FileNotFoundException, DocumentException, SQLDataException{
    
        
        Commande c = ds.get_Commande(ShowCommandeClientController.idcommande);
        
        List<commande_panier> ls =ds.getAllCommandePanier(ShowCommandeClientController.idcommande);
        
 
        
        String file_name="C:\\Users\\HP\\Desktop\\Pidev2\\pdfpayment.pdf";
        Document document = new Document();
        
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        
        Paragraph p =new Paragraph("ME/Mme sarra Nous vous remercions sincèrement pour votre achat et espérons que vous serez satisfait de votre produit est voici les détails de payment :");
        Paragraph p1 =new Paragraph("Le prix est :"+c.getPrix()+"\n Etat ="+c.getEtat());
         document.add(p);
        document.add(p1);
        for (commande_panier cp : ls){
         Paragraph p2 =new Paragraph("Le Produit est :"+ds.get_produit(cp.getId_produit()).getNom()+"\n Avec prix ="+ds.get_produit(cp.getId_produit()).getPrix());
          document.add(p2);

        }
        

        
        document.close();
    
    
    
      
    }

    
}
