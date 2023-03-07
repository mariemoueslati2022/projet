/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Entities.evenement;
import utils.DataSource;
import Entities.offre;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 *
 * @author sony
 */
public  class ServiceOffre  {
     private Connection cnx;
    
    public ServiceOffre()
    {
        cnx= DataSource.getInstance().getCnx();
    }
    public void ajouterr(offre o) throws SQLException {

      String req;
           req = "INSERT INTO `offre`(`id_offre`, `nom`, `date_datedeb`, `date_fin`, `pourcentage` ) VALUES ('"+o.getId_offre()+"','"+o.getNom()+"','"+o.getDate_datedeb()+"','"+o.getDate_fin()+"','"+o.getPourcentage()+"')";
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("offre ajouté avec success");
        
    }

    public void modifierr(offre o,int id_offre) throws SQLException {
        String req ="UPDATE `offre` SET ',nom ='"+o.getNom()+"',`date_datedeb`='"+o.getDate_datedeb()+ "'`date_fin`='"+o.getDate_fin()+"',`pourcentage`='"+o.getPourcentage()+"' WHERE id="+id_offre;
    
        
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("modifié avec succes");
        
        
    }

    public void supprimerr(int o) throws SQLException {
        String req="DELETE FROM `offre` WHERE id="+o;
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("offre supprimer avec succes");
        
    }
    
    /**
     *
     * @param searchKeyword
     * @return
     */
    
    

    
 

    private Comparator<offre> getComparator(String sortBy, boolean isAscending) {
    Comparator<offre> comparator = null;
    
    if (sortBy.equals("name")) {
        comparator = Comparator.comparing(offre::getNom);
    } else if (sortBy.equals("date_fin")) {
        comparator = Comparator.comparing(offre::getDate_fin);
    } else if (sortBy.equals("date_datedeb")) {
        comparator = Comparator.comparing(offre::getDate_datedeb);        
    } else if (sortBy.equals("pourcentage")) {
        comparator = Comparator.comparing(offre::getPourcentage);
    }
    
    if (!isAscending) {
        comparator = comparator.reversed();
    }
    
    return comparator;
}

    

  
   }
