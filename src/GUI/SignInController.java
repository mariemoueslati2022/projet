package GUI;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.UserService;
import Entities.User;
import Services.UserSession;
import com.jfoenix.controls.JFXPasswordField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import org.mindrot.jbcrypt.BCrypt;


/**
 * FXML Controller class
 *
 * @author alaed
 */
public class SignInController implements Initializable {
    UserService sc=new UserService();
    private Parent fxml;
    @FXML
    private VBox VBox;

    @FXML
    private Button btn_forgetpassword;

    @FXML
    private Button btn_login;

    @FXML
    private JFXTextArea tf_password;

    @FXML
    private JFXTextArea tf_email;

    @FXML
    void openHome(ActionEvent event)  {
        if (event.getSource() == btn_login) {
            if (validateEmail(tf_email) & validatePassword(tf_password)) {
                User user = sc.login(tf_email.getText(), tf_password.getText());
                System.out.println(user);
                UserService.userSession = new UserSession();
                System.out.println(user);
        if (user != null) {
            System.out.println(user.getEmail());
            
            System.out.println("user found login to screen user");
            if (user.getRole().equals("user")) {
                System.out.println(user.getRole());
                
                try {
                    System.out.println("login to user page");
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage home = new Stage();
                    fxml = FXMLLoader.load(getClass().getResource("UserScreen.fxml"));
                    Scene scene = new Scene(fxml);
                    home.setScene(scene);
                    home.show();
                    sc.userSession.setUserEmail(user.getEmail());

                } catch (IOException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                
                try {
                    System.out.println("login to admin page");
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    Stage home = new Stage();
                    fxml = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    Scene scene = new Scene(fxml);
                    home.setScene(scene);
                    home.show();
                } catch (IOException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Failed To Open Home");
        }
            }
        
        }

        
    }

    @FXML
    void sendPassword(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sc = new UserService();
    }

    private boolean validateEmail(JFXTextArea email) {
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

    private boolean validatePassword(JFXTextArea password) {
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

    
}
