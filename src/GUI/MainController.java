/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author alaed
 */

public class MainController implements Initializable {
    private Parent fxml;

    @FXML
    private VBox VBox;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_register;

    @FXML
void openSignin(ActionEvent event) {
    TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
    t.setToX(360);
    t.play();
    t.setOnFinished(e->{
        try {
            fxml=FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            VBox.getChildren().removeAll();
            VBox.getChildren().setAll(fxml);
                    } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
}

@FXML
void openRegister(ActionEvent event) {
    TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
    t.setToX(0);
    t.play();
    t.setOnFinished(e->{
        try {
            fxml=FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            VBox.getChildren().removeAll();
            VBox.getChildren().setAll(fxml);
                    } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
        t.setToX(0);
        t.play();
        t.setOnFinished(e->{
            try {
                fxml=FXMLLoader.load(getClass().getResource("SignUp.fxml"));
                VBox.getChildren().removeAll();
                VBox.getChildren().setAll(fxml);
                        } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
    });

    }    
    
}
