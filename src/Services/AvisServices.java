
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import Entities.Avis;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author LENOVO
 */
public class AvisServices {

    Connection cnx;

    public AvisServices() {

        cnx = DataSource.getInstance().getCnx();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void ajouterAvis(Avis a) {

        
        String sql = "insert into avis(id,rating,commentaire,titre) values(?,?,?,?)";

        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, a.getId());
            ste.setInt(2, a.getRating());
            ste.setString(3, a.getCommentaire());
            ste.setString(4, a.getTitre());

            ste.executeUpdate();
            System.out.println("Avis Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Avis> afficher() {
        List<Avis> lu = new ArrayList<>();
        try {

            Statement ste = cnx.createStatement();
            String query = "select * from avis";
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                Avis a = new Avis();
                a.setId(rs.getInt("id"));
                a.setRating(rs.getInt("rating"));
                a.setCommentaire(rs.getString("commentaire"));
                a.setTitre(rs.getString("titre"));

                lu.add(a);

            }

        } catch (SQLException ex) {
            Logger.getLogger(AvisServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lu;

    }

    public void supprimerAvis(int id) {
        try {
            String sql = "DELETE FROM avis WHERE id=" + id + "";
            PreparedStatement ste = cnx.prepareStatement(sql);

            ste.executeUpdate();
            System.out.println("avis Supprimée ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierAvis(Avis a) {

        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(
                    "UPDATE avis set rating=?,commentaire=?,titre=? WHERE id=?");
           
            ste.setInt(1, a.getRating());
            ste.setString(2, a.getCommentaire());
            ste.setString(3, a.getTitre());
            ste.setInt(4, a.getId());
            
            ste.executeUpdate();
            System.out.println("Avis modifier");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
}

