/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import utils.DataSource;

import Entities.evenement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author islem
 */
public class EventScanController implements Initializable {

    @FXML
    private Button qrbtn;
    @FXML
    private TextField datetf;
    @FXML
    private TextField prixtf;
    @FXML
    private ImageView qrview;
    ObservableList<evenement> evenementsList = FXCollections.observableArrayList();
    ObservableList<String> List = FXCollections.observableArrayList();
    ObservableList<Integer> Listev = FXCollections.observableArrayList();
    ObservableList<Integer> List2 = FXCollections.observableArrayList();
    ObservableList<String> Status = FXCollections.observableArrayList("active", "not active");
    @FXML
    private ComboBox<String> titres;
    @FXML
    private Button qrbtn2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getTitreeventList();
    }

    public ObservableList<evenement> getTitreeventList() {

        Connection conn = DataSource.getInstance().getCnx();
        String query = "SELECT nom,id FROM evenement";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evenement Event;
            while (rs.next()) {
                
                String titre = rs.getString("nom").toString();
                int id_ev = rs.getInt("id");
                List.add(titre);
                Listev.add(id_ev);
                titres.setItems(List);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return evenementsList;

    }

    @FXML
    private void onclick(ActionEvent event) {
        try {
            // Generate the QR code
            String datef = datetf.getText();
            String prixf = prixtf.getText();
            String content = datef + "\n" + prixf;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);

            // Convert the bit matrix to an image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            Image image = new Image(new ByteArrayInputStream(Base64.getDecoder().decode(encodedImage)));

            // Set the image view to display the generated QR code
            qrview.setImage(image);
            qrbtn2.setDisable(false);
            qrbtn2.setOnAction(event2 -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save QR Code Image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
                File file = fileChooser.showSaveDialog(qrbtn2.getScene().getWindow());
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void OnSelect(ActionEvent event) {
        getdate();
        getprixticket();
    }

    private void getdate() {
        Connection conn = DataSource.getInstance().getCnx();
        Integer ss = Listev.get(List.indexOf(titres.getValue()));
        String query = "SELECT date FROM evenement where id = '" + ss + "' ";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evenement Event;
            while (rs.next()) {
                String type = rs.getString("date").toString();
                System.out.println(type);
                datetf.setText(type);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    private void getprixticket() {
        Connection conn = DataSource.getInstance().getCnx();
        Integer ss = Listev.get(List.indexOf(titres.getValue()));
        String query = "SELECT prix FROM evenement where id = '" + ss + "' ";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evenement Event;
            while (rs.next()) {
                String type = rs.getString("prix").toString();
                System.out.println(type);
                if (type == null) {
                    prixtf.setText("no price");
                } else {
                    prixtf.setText(type);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

}
