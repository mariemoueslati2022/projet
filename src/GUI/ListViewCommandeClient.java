/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI ;



import Entities.Commande;
import Entities.ProduitS;
import javafx.scene.control.ListCell;

/**
 *
 * @author dell
 */
public class ListViewCommandeClient extends ListCell<Commande> {
    
    
     @Override
     public void updateItem(Commande e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            CommandeClientItemController data = new CommandeClientItemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}
