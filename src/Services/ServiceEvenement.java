package Services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import utils.DataSource;
import Entities.evenement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 *
 * @author user
 */
public  class ServiceEvenement {
       private Connection cnx;
    
    public ServiceEvenement()
    {
        cnx= DataSource.getInstance().getCnx();
    }


    
    public void ajouter(evenement t) throws SQLException {

      String req;
           req = "INSERT INTO `evenement`(`id`, `nom`, `nbre_deplaces`, `type`, `prix`, `date`) VALUES ('"+t.getId()+"','"+t.getNom()+"','"+t.getNbre_deplaces()+"','"+t.getType()+"','"+t.getPrix()+"','"+t.getDate()+"')";
        Statement st=cnx.createStatement();
           int executeUpdate = st.executeUpdate(req);
        System.out.println("evenement ajouté avec success");
        
    }


        

    public void supprimer(int t) throws SQLException {
        String req="DELETE FROM `evenement` WHERE id="+t;
        Statement st=cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("evenement supprimer avec succes");
        
    }

    public List<evenement> afficher() throws SQLException {
        List<evenement> evenement= new ArrayList<>();
        
        String req="SELECT * FROM evenement";
               Statement st=cnx.createStatement();
        ResultSet RS=st.executeQuery(req);
        
          while (RS.next()) {
            //or rst.getInt(1)
            evenement e = new evenement ();//or rst.getInt(1)
            e.setId(RS.getInt(1));
            e.setNom(RS.getString(2));
            e.setNbre_deplaces(RS.getInt(3));
            e.setType(RS.getString(4));
            e.setPrix(RS.getFloat(5));
            e.setDate(RS.getDate(6));
             
           
           evenement.add(e);
        }
        return evenement;
    }
    
   
     
     

   

    private Comparator<evenement> getComparator(String sortBy, boolean isAscending) {
    Comparator<evenement> comparator = null;
    
    if (sortBy.equals("nom")) {
        comparator = Comparator.comparing(evenement::getNom);
    } else if (sortBy.equals("prix")) {
        comparator = Comparator.comparing(evenement::getPrix);
    } else if (sortBy.equals("date")) {
        comparator = Comparator.comparing(evenement::getDate);        
    } else if (sortBy.equals("nbre_deplaces")) {
        comparator = Comparator.comparing(evenement::getNbre_deplaces);
    }
    
    if (!isAscending) {
        comparator = comparator.reversed();
    }
    
    return comparator;
}


  

  


    
}
