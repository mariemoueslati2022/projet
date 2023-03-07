/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Commande;
import Entities.ProduitS;
import Entities.User;
import Entities.commande_panier;
import Entities.panier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

/**
 *
 * @author Abderrazekbenhamouda
 */
public class Service {
    
    
        private final Connection cnx;

    private static Service instance;
    
        public Service() {
        cnx = DataSource.getInstance().getCnx();
    }
    
    public static Service getInstance()
    {
        if (instance == null) {
            instance = new Service();
        }
        return instance; 
    }
    
    
    
    
        public ObservableList<ProduitS> getAllProduit() throws SQLDataException {

        
        List<ProduitS> list =new ArrayList<ProduitS>();
        int count =0;
        
        String requete="select * from produits";
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                ProduitS e = new ProduitS();
                e.setId(rs.getInt("id_produit"));
                e.setNom(rs.getString("nom"));
                e.setPrix(rs.getFloat("prix"));
                
                list.add(e);
                
                count++;
            }
            if(count == 0){
                return null;
           }else{
             ObservableList lc_final = FXCollections.observableArrayList(list);

               return lc_final;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
}
        
        public List<Commande> getAllCommande() throws SQLDataException {

        
        List<Commande> list =new ArrayList<Commande>();
        int count =0;
        
        String requete="select * from commande";
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                Commande e = new Commande();
                e.setId_commande(rs.getInt("id_commande"));
                e.setId_user(rs.getInt("id_user"));
                e.setType(rs.getString("type"));
                e.setPrix(rs.getFloat("prix"));
                e.setEtat(rs.getString("etat"));
                
                list.add(e);
                
                count++;
            }
            if(count == 0){
                return null;
           }else{
            // ObservableList lc_final = FXCollections.observableArrayList(list);

               return list;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
}

      public Commande get_Commande(int id) {
        Commande e = new Commande();
        String requete = "select * from commande where id_commande="+id;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                e.setId_commande(rs.getInt("id_commande"));
                e.setId_user(rs.getInt("id_user"));
                e.setType(rs.getString("type"));
                e.setPrix(rs.getFloat("prix"));
                e.setEtat(rs.getString("etat"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
          System.out.println("service.Service.get_Commande()"+e.toString());
        return e;

    }
              
        
      public ProduitS get_produit(int id) {
        ProduitS e = new ProduitS();
        String requete = "select * from produits where id_produit="+id;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                e.setId(rs.getInt("id_produit"));
                e.setNom(rs.getString("nom"));
                e.setPrix(rs.getFloat("prix"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }
      
      
    public User get_User(int id) {
        User e = new User();
        String requete = "select * from user where id_user="+id;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                e.setId(rs.getInt("id_user"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }
    
    
        public panier get_PanierByProduit(int id) {
        panier e = new panier();
        String requete = "select * from panier where id_produit="+id;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                e.setId_panier(rs.getInt("id_panier"));
                e.setId_produit(rs.getInt("id_produit"));
                e.setQuantité(rs.getInt("quantite"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
            System.out.println("service.Service.get_PanierByProduit()"+e.toString());
        return e;

    }
      
      
          
   public void addPanier(panier p)throws SQLDataException, SQLException{
        
         
      String query ="INSERT INTO `panier`( `id_produit`, `quantite`) VALUES (?,?)";
 
         PreparedStatement st;
        
        try {
            st = cnx.prepareStatement(query);
                st.setInt(1,p.getId_produit());
                st.setInt(2,p.getQuantité());
                st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



   
       public List<commande_panier> getAllCommandePanier(int id) throws SQLDataException {

        
        List<commande_panier> list =new ArrayList<commande_panier>();
        int count =0;
        
        String requete="select * from `commande-panier` where id_commande="+id;
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                commande_panier e = new commande_panier();
                e.setId_pannierCommande(rs.getInt("id"));
                e.setId_commande(rs.getInt("id_commande"));
                e.setId_produit(rs.getInt("id_produit"));
                
                list.add(e);
                count++;
            }
            if(count == 0){
                return list;
           }else{

               return list;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
       }


   
   
   
       public List<panier> getAllPanier() throws SQLDataException {

        
        List<panier> list =new ArrayList<panier>();
        int count =0;
        
        String requete="select * from panier";
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                panier e = new panier();
                e.setId_panier(rs.getInt("id_panier"));
                e.setId_produit(rs.getInt("id_produit"));
                e.setQuantité(rs.getInt("quantite"));
                
                list.add(e);
                count++;
            }
            if(count == 0){
                return list;
           }else{

               return list;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
       }
      
      
      
     public boolean deleteFromPanier(int idp) throws SQLDataException {

        
        
        try {
            
            Statement st=cnx.createStatement();
            String req= "DELETE FROM `panier` WHERE `id_produit`="+idp;
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
     
     
          public boolean deletePanier() throws SQLDataException {

        
        
        try {
            
            Statement st=cnx.createStatement();
            String req= "TRUNCATE TABLE `panier`";
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
          
          
       public boolean ModifierQuantite(int id,int q) throws SQLDataException {
              String query = "UPDATE `panier` SET `quantite`=? WHERE `id_produit` = ?";
		PreparedStatement st;
               try {
                st = cnx.prepareStatement(query);
            
                st.setInt(1,q);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("trueeeeeeeee");
                return true;
                
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
   
     public void addCommande(Commande  p)throws SQLDataException, SQLException{
        
         
      String query ="INSERT INTO `commande`( `id_user`, `type`,`prix`,`etat`) VALUES (?,?,?,?)";
 
         PreparedStatement st;
        
        try {
            st = cnx.prepareStatement(query);
                st.setInt(1,p.getId_user());
                st.setString(2,p.getType());
                st.setFloat(3, p.getPrix());
                st.setString(4,p.getEtat());
                st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
          public void addCommandePanier(commande_panier  p)throws SQLDataException, SQLException{
        
         
      String query ="INSERT INTO `commande-panier`( `id_commande`, `id_produit`) VALUES (?,?)";
 
         PreparedStatement st;
        
        try {
            st = cnx.prepareStatement(query);
                st.setInt(1,p.getId_commande());
                st.setInt(2, p.getId_produit());
                st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
          
                 public int getIdCommande () throws SQLException{
       
                     int c =0 ;
                    String query = "SELECT Max(id_commande) FROM commande ";
                    Statement st  = cnx.createStatement();
                    ResultSet rs = st.executeQuery(query);
                     while(rs.next()){         
                      c=c+ rs.getInt(1);
                     }
                     return c ;


                   }
    
                 
                 
            
       public boolean UpdateCommandeEtat(int id,String s) throws SQLDataException {
              String query = "UPDATE `commande` SET `etat`=? WHERE `id_commande` = ?";
		PreparedStatement st;
               try {
                st = cnx.prepareStatement(query);
                st.setString(1,s);
                st.setInt(2,id);
                st.executeUpdate();
                System.out.println("trueeeeeeeee");
                return true;
                
        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }               
        
   public List<Commande> getMesCommande(int id) throws SQLDataException {

        
        List<Commande> list =new ArrayList<Commande>();
        int count =0;
        
        String requete="select * from commande where id_user="+id;
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                Commande e = new Commande();
                e.setId_commande(rs.getInt("id_commande"));
                e.setId_user(rs.getInt("id_user"));
                e.setType(rs.getString("type"));
                e.setPrix(rs.getFloat("prix"));
                e.setEtat(rs.getString("etat"));
                
                list.add(e);
                
                count++;
            }
            if(count == 0){
                return null;
           }else{
            // ObservableList lc_final = FXCollections.observableArrayList(list);

               return list;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
}
    
}
