/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI ;



import Entities.ProduitS;
import javafx.scene.control.ListCell;

/**
 *
 * @author dell
 */
public class ListViewPanier extends ListCell<ProduitS> {
    
    
     @Override
     public void updateItem(ProduitS e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            PaniertemController data = new PaniertemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}

