/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author LENOVO
 */
public class ReclamationService {
    Connection cnx;

    public ReclamationService() {
        cnx=DataSource.getInstance().getCnx();
        
    }
    
    
    
    
    public void ajouterReclamation(Reclamation r) throws SQLException{
       try {
           String sql="insert into reclamation( nom, prenom, email, tel,etat, description, date_reclamation)"
                   + " values(?,?,?,?,?,?,?)";
       
            PreparedStatement ste= cnx.prepareStatement(sql);
            ste.setString(1, r.getNom());
            ste.setString(2,r.getPrenom());
            ste.setString(3,r.getEmail());
            ste.setInt(4,r.getTel());
            ste.setString(5,r.getEtat());
            ste.setString(6,r.getDescription());
            ste.setDate(7,new java.sql.Date(r.getDate_reclamation().getTime()))  ;
        
            ste.executeUpdate();
            System.out.println("Reclamation Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
  
    
    public List<Reclamation> afficherReclamation(){
        List<Reclamation> reclamations = new ArrayList<>();
        String query="select * from reclamation";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs= ste.executeQuery();
            while(rs.next()){
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setNom(rs.getString("nom"));
                r.setPrenom(rs.getString("prenom"));
                r.setEmail(rs.getString("email"));
                r.setTel(rs.getInt("tel"));
                r.setEtat(rs.getString("etat"));
                r.setDescription(rs.getString("description"));
                r.setDate_reclamation(rs.getDate("date_reclamation"));
                reclamations.add(r);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return reclamations;
        
    }
    
    
    
    
    
   
    
    public void supprimerReclamation(int id) {
 try {
            String sql = "DELETE FROM reclamation WHERE id="+id+"";
            PreparedStatement ste  = cnx.prepareStatement(sql);
           
            ste.executeUpdate();
            System.out.println("Reclamation Supprimée ");
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    
    
    }
    
    
    
    
    
    
    public void modifierReclamation( Reclamation r) {
        String sql =  "UPDATE reclamation SET nom`=?,prenom`=?,`email`=?,"
                            + "tel`=?,etat`=?,"
                            + "description`=?,date_reclamation`=? WHERE id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
           
                    
                 
           
            ste.setString(1,r.getNom());
            ste.setString(2, r.getPrenom());
            ste.setString(3,r.getEmail());
            ste.setInt(4, r.getTel());
            ste.setString(5,r.getEtat());
            ste.setString(6,r.getDescription());
            ste.setDate(7,new java.sql.Date(r.getDate_reclamation().getTime()));
            ste.setLong(8,r.getId());
            ste.executeUpdate();
       
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        
        
        

    }
    


  public void ChercherReclamation() throws SQLException {
        
        List<Reclamation> Reclamation = new ArrayList<>();
        try {

            String sql = "Select * from reclamation WHERE `id` =" ;
            ResultSet rs;
            PreparedStatement ste;
            ste = cnx.prepareStatement(sql);
            rs = ste.executeQuery();

            while (rs.next()) {
                Reclamation u = new Reclamation();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(2));
                u.setPrenom(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setTel(rs.getInt(5));
                u.setEtat(rs.getString(6));
                u.setDescription(rs.getString(7));
                u.setDate_reclamation(rs.getDate(8));
                

                Reclamation.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println(Reclamation);
    }

    public List<Reclamation> findByName(String Nom) {

        List<Reclamation> Reclamation = afficherReclamation();
        List<Reclamation> resultat = Reclamation.stream().filter(reclamation -> Nom.equals(reclamation.getNom())).collect(Collectors.toList());
        if (resultat.isEmpty()) {
            System.out.println("Aucun reclamation par ce nom");
            return null;

        } else {
            System.out.println("reclamation : ");
            return resultat;

        }
    }

}

