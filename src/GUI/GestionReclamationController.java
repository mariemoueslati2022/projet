/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Avis;
import Entities.Reclamation;
import utils.DataSource;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import Services.AvisServices;
import Services.ReclamationService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class GestionReclamationController implements Initializable {

    @FXML
    private TableColumn<Reclamation, String> tblNom;
    @FXML
    private TableColumn<Reclamation, String> tblPrenom;
    @FXML
    private TableColumn<Reclamation, String> tblEmail;
    @FXML
    private TableColumn<Reclamation, Integer> tblTel;
    @FXML
    private TableColumn<Reclamation, String> tblEtat;
    @FXML
    private TableColumn<Reclamation, String> tblDescription;
    @FXML
    private TableColumn<Reclamation, Date> tblDateReclamation;
    @FXML
    private TableView<Reclamation> TableReclamation;
    @FXML
    private TextField recherchet;
    ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();
    ObservableList<Reclamation> data = FXCollections.observableArrayList();

    ReclamationService ds = new ReclamationService();
    Reclamation reclamation = null;
    Connection cnx;
    @FXML
    private TextArea nomRec;
    @FXML
    private TextArea preRec;
    @FXML
    private TextArea telfRec;
    @FXML
    private TextArea emailRec;
    @FXML
    private TextArea etatRec;
    @FXML
    private TextArea descriptionRec;
    @FXML
    private TextArea dateRec;
    @FXML
    private TextArea idRec;

    public GestionReclamationController() {

        cnx = DataSource.getInstance().getCnx();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection cnx = DataSource.getInstance().getCnx();
            ResultSet rs = cnx.createStatement().executeQuery("select * from reclamation");

            while (rs.next()) {
                reclamationList.add(new Reclamation(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("tel"),
                        rs.getString("etat"),
                        rs.getString("description"),
                        rs.getDate("date_reclamation")
                ));

            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadDate();
        TableReclamation.setItems(reclamationList);

    }

    private void loadDate() {
        tblNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tblPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tblEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tblDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblDateReclamation.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));

    }

    @FXML
    public void refreshlist() {
        data.clear();
        data = FXCollections.observableArrayList(ds.afficherReclamation());
        tblNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tblPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tblEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tblDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblDateReclamation.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));

        TableReclamation.setItems(data);
    }

    @FXML
    private void deleteReclamation(MouseEvent event) {
        reclamation = TableReclamation.getSelectionModel().getSelectedItem();
        ReclamationService ds = new ReclamationService();
        int input = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer !?",
                "Choisir une option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (input == 0) {
            ds.supprimerReclamation(reclamation.getId());

        } else if (input == 1) {

        }

    }

    @FXML
    private void UpdateReclamation(MouseEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Warning");
       alert.setContentText("Es-tu sûre de modifier!");

       
        String value1 = idRec.getText();
        String nom = nomRec.getText();
        String prenom =preRec.getText();
        String telf =telfRec.getText();
         String email= emailRec.getText();
         String etat=etatRec.getText();
         String description= descriptionRec.getText();
         String date=  dateRec.getText();    
                        
            
       
        Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
     
         Reclamation r= new Reclamation(Integer.parseInt(value1),nom,prenom,telf,email,etat,description,date);
         
         ReclamationService rs=new ReclamationService();
         rs.modifierReclamation(r);
      
        idRec.setText(null);
        nomRec.setText(null);
        preRec.setText(null);
        telfRec.setText(null);
           emailRec.setText(null); 
        etatRec.setText(null);
       descriptionRec.setText(null);
         dateRec.setText(null);
        }
    }

    @FXML
    private void recherche_avance(KeyEvent event) {
        System.out.println("*******************");

        //System.out.println(id.departement);
        FilteredList<Reclamation> filtereddata = new FilteredList<>(data, b -> true);
        System.out.println(recherchet.getText());
        recherchet.textProperty().addListener((observable, oldvalue, newValue) -> {
            filtereddata.setPredicate(reclamation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercasefilter = newValue.toLowerCase();
                if (reclamation.getNom().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (reclamation.getPrenom().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (reclamation.getEmail().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(reclamation.getTel()).toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (reclamation.getEtat().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (reclamation.getDescription().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(reclamation.getDate_reclamation()).toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });

        });
        System.out.println(filtereddata);
        SortedList<Reclamation> sorteddata = new SortedList<>(filtereddata);
        sorteddata.comparatorProperty().bind(TableReclamation.comparatorProperty());
        TableReclamation.setItems(filtereddata);
    }

    /*@FXML
    private void exel(MouseEvent event) {
        WritableWorkbook wworkbook;
        try {
            wworkbook = Workbook.createWorkbook(new File("C:\\Users\\LENOVO\\Desktop\\ReclamationExcel.xls"));

            String query = "select nom,prenom,email,tel,etat,description,date_reclamation from reclamation";
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery();
            WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 2, "A label record");
            wsheet.addCell(label);
            int i = 0;

            int j = 1;
            while (rs.next()) {

                i = 0;

                label = new Label(i++, j, j + "");
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("nom"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("prenom"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("email"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("tel"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("etat"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("description"));
                wsheet.addCell(label);
                label = new Label(i++, j, rs.getString("date_reclamation"));
                wsheet.addCell(label);

                j++;
            }

            wworkbook.write();
            wworkbook.close();
            System.out.println("fineshed");

        } catch (Exception e) {
            System.out.println(e);
        }
    }*/

    @FXML
    private void AjouterReclamatioRD(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../GUI/AjouterReclamation.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshlist();
    }

}
