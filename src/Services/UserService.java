/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
import utils.DataSource;
import Entities.User;
import Services.UserSession;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 *
 * @author alaed
 */
public class UserService {
    
                public static UserSession userSession;

    

    
         Connection cnx  = DataSource.getInstance().getCnx();


    
    public boolean register(User user) {
        String sql = "INSERT INTO user(nom, prenom, email, password, creation_date, image_profile, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            pst.setString(4, hashedPassword);
            pst.setDate(5, new Date(System.currentTimeMillis()));
            pst.setString(6, user.getImage_profile());
            pst.setString(7, "user");
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    public ObservableList<User> getAll() {
           ObservableList<User> list = FXCollections.observableArrayList();
        try {
            String req = "Select * from user";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(2));
                u.setPrenom(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setCreation_date(rs.getDate(6));
                u.setImage_profile(rs.getString(7));
                u.setRole(rs.getString(8));
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public void supprimer(int id) {
         try {
             String req = "DELETE FROM `user` WHERE id ="+id;
             Statement st = cnx.createStatement();  
             st.executeUpdate(req);
         } catch (SQLException ex) {
             System.out.println(ex.getMessage());
         }
    }
    
    public void modifier(int id,User u) {
     try{
         PreparedStatement ps = cnx.prepareStatement("UPDATE user SET `nom`=? ,  `prenom`= ? , `email`= ? , `image_profile`= ? , `role`= ? WHERE id= '"+id+"'");
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getImage_profile());
            ps.setString(5, u.getRole());

             if(ps.executeUpdate() > 0)
            {
               
                JOptionPane.showMessageDialog(null, "User Updated", "Edit user", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println("Product Updated");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "User Not Updated", "Edit categorie", JOptionPane.ERROR_MESSAGE);
              //System.out.println("Some Error Message Here");  
            }
            System.out.println("User Modifieé avec succées ");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public User login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Date creationDate = rs.getDate("creation_date");
                String imageProfile = rs.getString("image_profile");
                String role = rs.getString("role");
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    User user = new User(nom, prenom, email, hashedPassword, creationDate, imageProfile, role);
                    user.setId(id);
                    return user;
                } else {
                    System.out.println("Password incorrect");
                    return null;
                }
            } else {
                System.out.println("User not found");
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void ajouter(User u) {
        try {
           String requete = "INSERT INTO `user` (`nom`, `prenom`, `email`, `password`, `creation_date`, `image_profile`, `role`) VALUES ( ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getEmail());
            pst.setString(4, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt() ));
            pst.setDate(5, u.getCretaion_date());
            pst.setString(6, u.getImage_profile());
            pst.setString(7, "user");
            if(pst.executeUpdate() > 0)
            {
               
                JOptionPane.showMessageDialog(null, "User Added", "Add user", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println("Product Updated");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "User Not Added", "Add user", JOptionPane.ERROR_MESSAGE);
              //System.out.println("Some Error Message Here");  
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

public User GetUserByMailSession(String mail) {
        User user = null;


        String pass = "";
        try {
            String requete = "Select id, nom, prenom, password, creation_date, image_profile, role from user where email = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, mail);
            ResultSet rs;
            rs = pst.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        mail,
                        pass,
                        rs.getDate("creation_date"),
                        rs.getString("image_profile"),
                        rs.getString("role")
                );

                System.out.println(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (StringIndexOutOfBoundsException ex) {
            /*Login_pageController lc = new Login_pageController();
            new animatefx.animation.Shake(lc.getPasswordtxt()).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            lc.getPasswordtxt().setEffect(in);
             */
        }
        return user;
    }
    
}
