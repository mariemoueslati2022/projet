/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.evenement;
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
public class evenementController implements Initializable {

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
    private TextField pourcentage;
    private TextField nomoffre;
    private DatePicker DateD;
    private DatePicker DateF;
    @FXML
    private TableColumn<evenement, String> colnom;
 
    @FXML
    private TableColumn<evenement, String> actions;
    @FXML
    private TextField prixtf;
    @FXML
    private TextField nomtf;
    @FXML
    private DatePicker datetf;
    @FXML
    private TextField nbrplacestf;
    @FXML
    private TextField typetf;
    @FXML
    private TableColumn<evenement,Integer> colnbrplaces;
    @FXML
    private TableColumn<evenement, String> coltype;
    @FXML
    private TableColumn<evenement, Date> coldate;
    @FXML
    private TableColumn<evenement, Float> colprix;
    @FXML
    private TableView<evenement> tabevent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        showoffres();
        //showRec();
        //searchRec();
    }

    public ObservableList<evenement> getoffreList() {
        ObservableList<evenement> compteList = FXCollections.observableArrayList();
        Connection conn = DataSource.getInstance().getCnx();
        String query = "SELECT * FROM evenement";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evenement evenement;

            while (rs.next()) {
                evenement = new evenement(rs.getInt("id"), rs.getString("nom"),rs.getInt("nbre_deplaces"), rs.getString("type"), rs.getFloat("prix") , rs.getDate("date"));
                compteList.add(evenement);
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
        ObservableList<evenement> list = getoffreList();
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colnbrplaces.setCellValueFactory(new PropertyValueFactory<>("nbre_deplaces"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        

        tabevent.setItems(list);
        Callback<TableColumn<evenement, String>, TableCell<evenement, String>> cellFoctory = (TableColumn<evenement, String> param) -> {
            // make cell containing buttons
            final TableCell<evenement, String> cell = new TableCell<evenement, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    evenement evenement = null;
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
                                    evenement evenements;
                                    evenements = tabevent.getSelectionModel().getSelectedItem();
                                    String query = "DELETE FROM `evenement` WHERE id =" + evenements.getId();
                                    Connection conn = DataSource.getInstance().getCnx();
                                    ps = conn.prepareStatement(query);
                                    ps.execute();
                                    showoffres();

                                } catch (SQLException ex) {
                                    Logger.getLogger(evenementController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                } else if (result.get() == ButtonType.CANCEL) {
                                    showoffres();
                                }
                            }
                        });

                        editIcon.setOnMouseClicked((new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {

                                evenement evenements = tabevent.getSelectionModel().getSelectedItem();
                                
                                nomtf.setText(String.valueOf(evenements.getNom()));
                                nbrplacestf.setText(String.valueOf(evenements.getNbre_deplaces()));
                                 typetf.setText(String.valueOf(evenements.getType()));
                                  prixtf.setText(String.valueOf(evenements.getPrix()));
                                datetf.setValue(evenements.getDate().toLocalDate()); 
                                
                                

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
        tabevent.setItems(list);

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
           String nom  = nomtf.getText();
        String type  = typetf.getText();
        String Date = String.valueOf(datetf.getValue());
        int nbreplace = Integer.parseInt(nbrplacestf.getText()) ; 
        Float price  = Float.valueOf(prixtf.getText()) ;
            evenement evenement = tabevent.getSelectionModel().getSelectedItem();
            String query = "UPDATE evenement SET nom = '" + nom + "' ,  nbre_deplaces = '" + nbreplace + "' , type = '" + type + "' , prix = '" + price + "', date = '" + Date + "' WHERE id='" + evenement.getId() + "' ";
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
