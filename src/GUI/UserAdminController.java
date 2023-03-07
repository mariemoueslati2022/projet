/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import Entities.User;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author alaed
 */
public class UserAdminController implements Initializable {
    
    ObservableList<User> ob1 = FXCollections.observableArrayList();
    
    private String[] role={"user","admin"};
    
    
    @FXML
    private ChoiceBox<String> CB_role;

    @FXML
    private TableView<User> TableUser;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<User, Date> cDate;

    @FXML
    private TableColumn<User, String> cEmail;

    @FXML
    private TableColumn<User, String> cImage;

    @FXML
    private TableColumn<User, String> cNom;

    @FXML
    private TableColumn<User, String> cPassword;

    @FXML
    private TableColumn<User, String> cPrenom;

    @FXML
    private TableColumn<User, String> cRole;

    @FXML
    private JFXTextArea tfEmail;

    @FXML
    private JFXTextArea tfImage;

    @FXML
    private JFXTextArea tfNom;

    @FXML
    private JFXTextArea tfPassword;

    @FXML
    private JFXTextArea tfPrenom;

    @FXML
    private JFXTextArea tfSerach;

    @FXML
    void Ajouter(ActionEvent event) {
       LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Date date = new Date(timestamp.getTime());
        
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String image_profile = tfImage.getText();
        
        User u = new User(nom, prenom, email, password, date , image_profile);
        UserService sc = new UserService();
        sc.ajouter(u);
        afficher();
     

    }

    @FXML
     private void Modifier(ActionEvent event) {
     LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);
    Date date = new Date(timestamp.getTime());

    UserService sc = new UserService();
    String nom = tfNom.getText();
    String prenom = tfPrenom.getText();
    String email = tfEmail.getText();
    String password = tfPassword.getText();
    String image_profile = tfImage.getText();
    String role=CB_role.getValue();
    User u = new User(nom, prenom, email, password, date , image_profile,role);

    User user = TableUser.getSelectionModel().getSelectedItem();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Êtes-vous sûr de modifier ?");
    Optional<ButtonType> action = alert.showAndWait();
    if (action.get() == ButtonType.OK) {
        sc.modifier(user.getId(), u);
        afficher();
       
    }
    afficher();
}


     @FXML
    void OnMouseClicked(MouseEvent event) {
       User  u =  TableUser.getSelectionModel().getSelectedItem();
     
        if (u != null) {

           
            tfNom.setText(String.valueOf(u.getNom()));
            tfPrenom.setText(String.valueOf(u.getPrenom()));
            tfEmail.setText(String.valueOf(u.getEmail()));
            tfPassword.setText(String.valueOf(u.getPassword()));
            tfImage.setText(String.valueOf(u.getImage_profile()));
        }
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        UserService sc=new UserService();
        User user = TableUser.getSelectionModel().getSelectedItem();
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Comfirmation");
          alert.setHeaderText(null);
          alert.setContentText("Êtes-vous sûr de supprimer ?");
          Optional<ButtonType> action = alert.showAndWait();
          if (action.get() == ButtonType.OK) {
         sc.supprimer(user.getId());
            afficher(); 
    }}

    @FXML
    void Upload(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String ImageName=f.getAbsolutePath();
        tfImage.setText(ImageName);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher();
    
        CB_role.getItems().addAll(role);
    } 
    
    void afficher() {
    UserService sl = new UserService();
    ob1 = FXCollections.observableList(sl.getAll());

        FilteredList<User> filteredData = new FilteredList<>(ob1, p -> true);

    // add listener to search field
        tfSerach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getRole().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(TableUser.comparatorProperty());

    TableUser.setItems(sortedData);
    cNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    cPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    cPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    cRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    cImage.setCellValueFactory(new PropertyValueFactory<>("image_profile"));

}
    
    private void filterTable(String text) {
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        for (User user : ob1) {
            if (user.getNom().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        TableUser.setItems(filteredList);
    }
    
}
    

