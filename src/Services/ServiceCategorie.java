/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Categorie;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.sql.PreparedStatement;
import utils.DataSource;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author House
 */
public class ServiceCategorie implements IService<Categorie> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    public ServiceCategorie() {
        con = DataSource.getInstance().getCnx();

    }

    @Override
    public void ajouter(Categorie t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO Categorie ( `nom` ) VALUES ('" + t.getNom()+ "');";
        try {
            ste=con.createStatement();
            ste.executeUpdate(requeteInsert);
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @Override
    public void delete(Categorie t) throws SQLException {
        try {
            String requete = " delete from Categorie where id='"+t.getId()+"'" ;
            pst = con.prepareStatement(requete);
              ste=con.createStatement();
            ste.executeUpdate(requete);
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public void update(Categorie t) throws SQLException {
        try {
            String requete = " update Categorie set nom=?  where id='"+t.getId()+"'"  ;
            pst = con.prepareStatement(requete);
            pst.setString(1,t.getNom());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Categorie> readAll() throws SQLException {
    List<Categorie> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Categorie");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom=rs.getString("nom");
               Categorie p=new Categorie(id, nom);
     arr.add(p);
     }
    return arr;
    }
    
    @Override
    public List<Categorie> getTrier() throws SQLException {
    List<Categorie> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Categorie ORDER BY nom DESC");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom=rs.getString("nom");
               Categorie p=new Categorie(id, nom);
     arr.add(p);
     }
    return arr;
    }

  public Categorie getByName(String n) {
          Categorie a = null;
         String requete = " select* from Categorie  where (nom like '"+n+"%')" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Categorie(res.getInt(1),res.getString(2));}
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }
    
    public Categorie getById(int id) {
          Categorie a = null;
         String requete = " select* from Categorie  where id='"+id+"'" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Categorie(res.getInt(1),res.getString(2));}
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }
    
      public int calculer(int idcat) {
          int l = 0 ;
         String requete = "SELECT COUNT(*) FROM produit WHERE id_categorie= "+idcat ;
        try {
           
           Statement st =con.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          String chaine = String.valueOf(rs.getString(1));
           l=Integer.parseInt(chaine);
            System.out.println(l);}
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCategorie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return l ;
    }



}
