package GUI;

import Entities.Avis;
import Entities.StarRating;
import utils.DataSource;
import Services.AvisServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

public class GestionAvisController implements Initializable {

    /*@FXML
    private TableView<Avis> tableView;
    @FXML
    private TextArea ratingColumn;
    @FXML
    private TextArea commentaireColumn;
    @FXML
    private TextArea titreColumn;*/

    @FXML
    private Button supprimerButton;

    Notifications no;
    String erreur;

    ObservableList<Avis> avisList = FXCollections.observableArrayList();
    ObservableList<Avis> data = FXCollections.observableArrayList();

    AvisServices as = new AvisServices();
    Avis avis = null;
    Connection cnx;
    @FXML
    private Button modifierAvis;
    @FXML
    private TextArea ratingAvis;
    @FXML
    private TextArea commentaireAvis;
    @FXML
    private TextArea titreAvis;
    @FXML
    private TextArea idAvis;
    @FXML
    private ListView<Avis> listviewOffre;

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
            ResultSet rs = cnx.createStatement().executeQuery("select * from avis");
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getInt("rating"),
                        rs.getString("commentaire"),
                        rs.getString("titre")
                ));

            }

        } catch (Exception ex) {
            Logger.getLogger(GestionAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        listviewOffre.setItems(avisList);

        listviewOffre.setCellFactory(new Callback<ListView<Avis>, ListCell<Avis>>() {
            @Override
            public ListCell<Avis> call(ListView<Avis> listView) {
                return new ListCell<Avis>() {
                    @Override
                    protected void updateItem(Avis item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(new StarRating(item.getRating()).display() + "            " + item.getCommentaire() + "             " + item.getTitre() + "          ");
                        }
                    }
                };
            }
        });

    }

    @FXML
    private void updateAvis(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Warning");
        alert.setContentText("Es-tu sûre de modifier!");

        String value1 = idAvis.getText();
        String rating = ratingAvis.getText();
        String commentaire = commentaireAvis.getText();
        String titre = titreAvis.getText();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Avis a = new Avis(Integer.parseInt(value1), Integer.parseInt(rating), commentaire, titre);
            AvisServices es = new AvisServices();
            es.modifierAvis(a);

            idAvis.setText(null);
            ratingAvis.setText(null);
            commentaireAvis.setText(null);
            titreAvis.setText(null);

            refresh();
        }

    }

    public void refresh() {
        avisList.clear();
        cnx = DataSource.getInstance().getCnx();
        avisList = FXCollections.observableArrayList();

        try {
            Connection cnx = DataSource.getInstance().getCnx();
            ResultSet rs = cnx.createStatement().executeQuery("select * from avis");
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getInt("rating"),
                        rs.getString("commentaire"),
                        rs.getString("titre")
                ));

            }

        } catch (Exception ex) {
            Logger.getLogger(GestionAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        listviewOffre.setItems(avisList);

        listviewOffre.setCellFactory(new Callback<ListView<Avis>, ListCell<Avis>>() {
            @Override
            public ListCell<Avis> call(ListView<Avis> listView) {
                return new ListCell<Avis>() {
                    @Override
                    protected void updateItem(Avis item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getRating() + "            " + item.getCommentaire() + "             " + item.getTitre() + "          ");
                        }
                    }
                };
            }
        });

    }
    @FXML
    private void selected(MouseEvent event) {
        Avis clicked = listviewOffre.getSelectionModel().getSelectedItem();
        idAvis.setText(String.valueOf(clicked.getId()));
        ratingAvis.setText(String.valueOf(clicked.getRating()));
        commentaireAvis.setText(String.valueOf(clicked.getCommentaire()));
        titreAvis.setText(String.valueOf(clicked.getTitre()));

    }

    class StarRatingCell extends TableCell<Avis, Integer> {  //pour mettre les etoiles au graphique

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                StarRating starRating = new StarRating(item);
                setText(starRating.display());
            } else {
                setText(null);
            }
        }
    }

    public GestionAvisController() {

        cnx = DataSource.getInstance().getCnx();
    }

    

    

    /*private void refreshList() {
        data.clear();
        data = FXCollections.observableArrayList(as.afficher());
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentaireColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        listviewOffre.setItems(data);
    }*/

    @FXML
    private void deleteAvis(ActionEvent event) {
        avis = listviewOffre.getSelectionModel().getSelectedItem();
        AvisServices as = new AvisServices();
        int input = JOptionPane.showConfirmDialog(null, "Voulez vous supprimer !?",
                "Choisir une option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (input == 0) {
            as.supprimerAvis(avis.getId());

            no = Notifications.create()
                    .title("Avis Supprimé")
                    .text("Avis supprimé avec succès")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(6));
            no.showInformation();
            refresh();
        } else if (input == 1) {

        }

    }

    @FXML
    private void AjouterAvisRD(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../GUI/AjouterAvis.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refreshList();
        //refreshList();

    }
}
