/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Avis;
import Entities.Reclamation;
import Services.AvisServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class AjouterAvisController implements Initializable {

    AvisServices as = new AvisServices();

    
    @FXML
    private TextField tfrating;
    @FXML
    private TextField tfcommentaire;
    @FXML
    private TextField tftitre;

    Notifications no;
    String erreur;
    @FXML
    private Button btnajouter;
    @FXML
    private ImageView imageRating;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouteravis(ActionEvent event) throws IOException {

        StringBuilder errors = new StringBuilder();
        

        if (!Pattern.matches("[0-9]+", tfrating.getText())) {
            errors.append("Rating must be an integer between 0 and 20\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        } else {
            Avis u = new Avis();
            u.setRating(Integer.parseInt(tfrating.getText()));
            u.setCommentaire(tfcommentaire.getText());
            u.setTitre(tftitre.getText());

            try {
                as.ajouterAvis(u);
                no = Notifications.create()
                        .title("Avis Ajouter")
                        .text("Avis ajouté avec succès")
                        .graphic(null)
                        .position(Pos.TOP_CENTER)
                        .hideAfter(Duration.seconds(6));
                no.showInformation();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid rating value: " + ex.getMessage());
            }
        }
    }

}
