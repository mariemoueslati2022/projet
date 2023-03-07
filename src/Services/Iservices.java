package Services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author user
 */
public interface Iservices <T> {
     void ajouter(T t) throws SQLException;
    void  supprimer (int t)throws SQLException;
    List<T> afficher ()throws SQLException;
    void ajouterr(T o) throws SQLException;
    void modifierr(T o,int id)throws SQLException;
    void  supprimerr (int o)throws SQLException;
    List<T> afficherr ()throws SQLException;
    List<T> sortObjectss(String sortBy, boolean isAscending);
    
}
