package it.polimi.ingsw.CLIENT;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;


public class GUIAPP extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    @FXML
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/mainMenu.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Image icon=new Image("Graphical_Assets/sfondo.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Eriantys");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
