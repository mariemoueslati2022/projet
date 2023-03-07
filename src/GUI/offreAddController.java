/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.offre;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class offreAddController implements Initializable {

    @FXML
    private Button btnajouter;
    
    
    offreController obj = new offreController() ;
    @FXML
    private Label dateDebut;
    @FXML
    private Label DateFin;
    @FXML
    private TextField pourcentage;
    @FXML
    private TextField nomoffre;
    @FXML
    private DatePicker DateD;
    @FXML
    private DatePicker DateF;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
          

    }    
       private boolean validateFields() {
        if (nomoffre.getText().isEmpty()) {
            showAlert("Error", "name field is required.");
            return false;
        }

        if (pourcentage.getText().isEmpty()) {
            showAlert("Error", "pourcentage field is required.");
            return false;
        }

        return true;
    }
         private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void OnCreate(ActionEvent event) {
         if (event.getSource() == btnajouter) {
            insert();
            obj.showoffres();

        }
    }

  
     private void executeQuery(String query) {
        Connection conn = DataSource.getInstance().getCnx();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }
    private void insert() {
        String nom  = nomoffre.getText();
        String Date_debut = String.valueOf(DateD.getValue());
        String Date_fin = String.valueOf(DateF.getValue()) ;
        Float pourcentagef  = Float.valueOf(pourcentage.getText()) ;
        validateFields() ;
        String query = "INSERT INTO `offre`( `nom`, `date_datedeb`, `date_fin`, `pourcentage`)  VALUES ('" + nom + "','" + Date_debut + "','" + Date_fin + "','" + pourcentagef + "')";
        executeQuery(query);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("offre");
        alert.setHeaderText(null);
        alert.setContentText("added succesfuly");
        alert.showAndWait();
}
    
    
}
