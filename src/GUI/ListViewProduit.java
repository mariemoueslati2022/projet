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
public class ListViewProduit extends ListCell<ProduitS> {
    
    
     @Override
     public void updateItem(ProduitS e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            ProduitItemController data = new ProduitItemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}
