/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import Entities.User;
import Services.UserSession;
import services.UserService;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author alaed
 */
public class ProfileController implements Initializable {

    @FXML
    private Pane pnlOverview;
    @FXML
    private TextField prenom;
    @FXML
    private TextField nom;
    @FXML
    private TextField profile_image;
    @FXML
    private TextField email;
    @FXML
    private Button update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserSession userSession = new UserSession();
        System.out.println(userSession.getUser());
        nom.setText(userSession.getUser().getNom());
        prenom.setText(userSession.getUser().getPrenom());
        profile_image.setText(String.valueOf(userSession.getUser().getImage_profile()));
        email.setText(userSession.getUser().getEmail());
    }    

    @FXML
    private void updtahost(ActionEvent event) {
        UserService u = new UserService();
        UserSession session = new UserSession();
        int id = session.getUser().getId();
        String email = session.getUser().getEmail();

        String name = nom.getText();
        String firstname = prenom.getText();
        String image=profile_image.getText();
        User user = new User(Integer.parseInt(String.valueOf(id)),name, firstname,email,image,"user" );
        u.modifier(id,user);
    }

    @FXML
    private void Upload(ActionEvent event) {
    }
    
}
