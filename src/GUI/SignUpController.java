/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import services.UserService;
import Entities.User;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javax.swing.JFileChooser;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.scene.input.KeyEvent;
import org.controlsfx.glyphfont.Glyph;


/**
 * FXML Controller class
 *
 * @author alaed
 */
public class SignUpController implements Initializable {
    
    @FXML
    private JFXTextArea tf_Image;

    @FXML
    private JFXTextArea tf_email;

    @FXML
    private JFXTextArea tf_nom;

    @FXML
    private JFXTextArea tf_password;

    @FXML
    private JFXTextArea tf_prenom;
    @FXML
    private Glyph show;

    @FXML
    void Register(ActionEvent event) {
        if(validate_Email(tf_email)&validate_Name(tf_nom)&validate_firstname(tf_prenom)&validate_Password(tf_password)){
            LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Date date = new Date(timestamp.getTime());

        String nom = tf_nom.getText();
        String prenom = tf_prenom.getText();
        String email = tf_email.getText();
        String password = tf_password.getText();
        String image_profile = tf_Image.getText();

        User user = new User(nom, prenom, email, password, date , image_profile);

        UserService userService = new UserService();
        boolean success = userService.register(user);

        if (success) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User added successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add user");
            alert.showAndWait();
        }
        }
        
    }
    @FXML
    void Upload(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String ImageName=f.getAbsolutePath();
        tf_Image.setText(ImageName);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    private boolean validate_Name(JFXTextArea name) {
        Pattern p = Pattern.compile("[a-zA-Z_]+");
        Matcher m = p.matcher(name.getText());
        if ((name.getText().length() == 0)
                || (!m.find() && m.group().equals(name.getText()))) {
            new animatefx.animation.Shake(name).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            name.setEffect(in);
            return false;
        } else {
            name.setEffect(null);
            return true;
        }
    }

    private boolean validate_firstname(JFXTextArea prenom) {
        Pattern p = Pattern.compile("^\\d{8}$");
        Matcher m = p.matcher(prenom.getText());

        if ((prenom.getText().length() == 8)
                || (m.find() && m.group().equals(prenom.getText()))) {
            prenom.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(prenom).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            prenom.setEffect(in);
            return false;
        }
    }

    private boolean validate_Email(JFXTextArea email) {

        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(email.getText());
        if (((email.getText().length() > 8))
                && (m.find() && m.group().equals(email.getText()))) {
            email.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(email).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            email.setEffect(in);
            return false;
        }

    }

    private boolean validate_Password(JFXTextArea password) {

        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(password.getText());
        if (((password.getText().length() > 7))
                && (m.find() && m.group().equals(password.getText()))) {
            password.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(password).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            password.setEffect(in);
            return false;
        }

    }

    @FXML
    private void PasswordTyped(KeyEvent event) {
                show.textProperty().bind(Bindings.concat(tf_password.getText()));

    }

    @FXML
    private void show(KeyEvent event) {
       
    }
}
