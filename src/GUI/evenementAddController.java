/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import utils.DataSource;
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

/**
 * FXML Controller class
 *
 * @author islem
 */
public class evenementAddController implements Initializable {

    @FXML
    private Button btnajouter;
    
    
    evenementController obj = new evenementController() ;
    @FXML
    private TextField prixtf;
    @FXML
    private TextField nomtf;
    @FXML
    private DatePicker datetf;
    @FXML
    private TextField nbrplacestf;
    @FXML
    private TextField typetf;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
          

    }    
       private boolean validateFields() {
        if (nomtf.getText().isEmpty()) {
            showAlert("Error", "name field is required.");
            return false;
        }

        if (prixtf.getText().isEmpty()) {
            showAlert("Error", "prix field is required.");
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
        String nom  = nomtf.getText();
        String type  = typetf.getText();
        String Date = String.valueOf(datetf.getValue());
        int nbreplace = Integer.parseInt(nbrplacestf.getText()) ; 
        Float price  = Float.valueOf(prixtf.getText()) ;
        validateFields() ;
        String query = "INSERT INTO `evenement`( `nom`, `nbre_deplaces`, `type`, `prix`, `date`) VALUES('" + nom + "','" + nbreplace + "','" + type + "','" + price + "','" + Date + "')";
        executeQuery(query);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("offre");
        alert.setHeaderText(null);
        alert.setContentText("added succesfuly");
        alert.showAndWait();
}
    
    
}
