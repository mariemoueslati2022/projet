/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.offre;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import utils.DataSource;


/**
 * FXML Controller class
 *
 * @author islem
 */
public class offreController implements Initializable {

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    private TextField nbrticket;
    private ComboBox<String> nomevent;
    private ComboBox<String> status;
    private TextField catego;
    private TextField prixticket;
    ObservableList<offre> evenementsList = FXCollections.observableArrayList();
    ObservableList<String> List = FXCollections.observableArrayList();
    ObservableList<String> type = FXCollections.observableArrayList();
    ObservableList<Integer> Listev = FXCollections.observableArrayList();
    ObservableList<Integer> List2 = FXCollections.observableArrayList();
    ObservableList<String> Status = FXCollections.observableArrayList("active", "not active");
    private TableColumn<offre, Integer> nbrticketr;
    private TableColumn<offre, Integer> idevent;
    @FXML
    private Button updatebutton;
    private TableColumn<?, ?> prxtickett;
    @FXML
    private Label dateDebut;
    @FXML
    private Label DateFin;
    @FXML
    private TextField pourcentage;
    @FXML
    private TextField nomoffre;
    @FXML
    private DatePicker DateD;
    @FXML
    private DatePicker DateF;
    @FXML
    private TableColumn<offre, String> colnom;
    @FXML
    private TableColumn<offre, Date> coldated;
    @FXML
    private TableColumn<offre, Date> coldatefin;
    @FXML
    private TableColumn<offre, Float> colpou;
    @FXML
    private TableView<offre> taboffre;
    @FXML
    private TableColumn<offre, String> actions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        showoffres();
        //showRec();
        //searchRec();
    }

    public ObservableList<offre> getoffreList() {
        ObservableList<offre> compteList = FXCollections.observableArrayList();
        Connection conn = DataSource.getInstance().getCnx();
        String query = "SELECT * FROM offre";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            offre offre;

            while (rs.next()) {
                offre = new offre(rs.getInt("id_offre"), rs.getString("nom"), rs.getDate("date_datedeb"), rs.getDate("date_fin"), rs.getFloat("pourcentage"));
                compteList.add(offre);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return compteList;
    }

    /*/////////////////////////////////////////////////Controle de saisie/////////////////////////////////////////////////////////////////////*/
    private boolean validateFields() {
        if (catego.getText().isEmpty()) {
            showAlert("Error", "ID field is required.");
            return false;
        }

        if (prixticket.getText().isEmpty()) {
            showAlert("Error", "Name event field is required.");
            return false;
        }

        if (nomevent.getValue().isEmpty()) {
            showAlert("Error", "category event field is required.");
            return false;
        }

        if (status.getValue().isEmpty()) {
            showAlert("Error", "Nb trans event field is required.");
            return false;
        }

        if (nbrticket.getText().isEmpty()) {
            showAlert("Error", "Number of tickets field is required.");
            return false;
        }
        return true;
    }


    public void showoffres() {
        ObservableList<offre> list = getoffreList();
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coldated.setCellValueFactory(new PropertyValueFactory<>("date_datedeb"));
        coldatefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colpou.setCellValueFactory(new PropertyValueFactory<>("pourcentage"));
        

        taboffre.setItems(list);
        Callback<TableColumn<offre, String>, TableCell<offre, String>> cellFoctory = (TableColumn<offre, String> param) -> {
            // make cell containing buttons
            final TableCell<offre, String> cell = new TableCell<offre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    offre offre = null;
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Alert!");
                                alert.setContentText("This is an alert");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    
                                
                                try {
                                    PreparedStatement ps = null;
                                    offre offres;
                                    offres = taboffre.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `offre` WHERE id_offre =" + offres.getId_offre();
                                     Connection conn = DataSource.getInstance().getCnx();
                                    ps = conn.prepareStatement(query);
                                    ps.execute();
                                    showoffres();

                                } catch (SQLException ex) {
                                    Logger.getLogger(offreController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showoffres();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                offre offres = taboffre.getSelectionModel().getSelectedItem();
                                
                                nomoffre.setText(String.valueOf(offres.getNom()));
                                pourcentage.setText(String.valueOf(offres.getPourcentage()));
                                DateD.setValue(offres.getDate_datedeb().toLocalDate()); 
                                DateF.setValue(offres.getDate_fin().toLocalDate()); 
                                

                            }

                        }));
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

            };
            return cell;
        };
        actions.setCellFactory(cellFoctory);
        taboffre.setItems(list);

    }

  
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    

    @FXML
    private void onupdate(ActionEvent event) {
        if (event.getSource() == updatebutton) {
           String nom  = nomoffre.getText();
        String Date_debut = String.valueOf(DateD.getValue());
        String Date_fin = String.valueOf(DateF.getValue()) ;
        Float pourcentagef  = Float.valueOf(pourcentage.getText()) ;
            offre offre = taboffre.getSelectionModel().getSelectedItem();
            String query = "UPDATE offre SET nom = '" + nom + "' ,  date_datedeb = '" + Date_debut + "' , date_fin = '" + Date_fin + "' , pourcentage = '" + pourcentagef + "' WHERE id_offre='" + offre.getId_offre() + "' ";
            executeQuery(query);
            showoffres();
        }
    }

    private void executeQuery(String query) {
        Connection conn = DataSource.getInstance().getCnx();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }


}
