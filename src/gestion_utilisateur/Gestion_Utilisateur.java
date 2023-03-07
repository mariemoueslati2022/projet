/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestion_utilisateur;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author alaed
 */
public class Gestion_Utilisateur extends Application {

    public void start(Stage primaryStage) {
    System.out.println("Starting application...");
    try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/Main.fxml"));
    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/AdminScreen.fxml"));
    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/UserScreen.fxml"));
    System.out.println("About to load FXML file...");
    Parent root = loader.load();
    System.out.println("FXML file loaded successfully.");

    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);
    primaryStage.setTitle("User");
    primaryStage.initStyle(StageStyle.TRANSPARENT);

    primaryStage.setScene(scene);
    System.out.println("About to show the stage...");
    primaryStage.show();
    System.out.println("Stage shown successfully.");

} catch (IOException ex) {
    System.out.println("Error loading FXML file:");
    ex.printStackTrace();
}

    }




    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
