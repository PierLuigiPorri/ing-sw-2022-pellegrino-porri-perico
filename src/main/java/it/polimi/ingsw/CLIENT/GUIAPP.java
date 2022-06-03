package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;


public class GUIAPP extends Application {
    private static final Object lock=new Object();
    private static final ClientMsgHandler msgHandler = new ClientMsgHandler("127.0.0.1", 4000, lock);
    private static final AckSender ackSender = new AckSender(msgHandler, 5000);

    public static void main(String[] args) {
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        launch(args);
    }
    @Override
    @FXML
    public void start(Stage primaryStage) throws Exception {
        MainMenuController.setGUI(this);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/mainMenu.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Image icon=new Image("Graphical_Assets/sfondo.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Eriantys");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void send(MessageType message){
        msgHandler.send(message);
    }

    public void waitForMessage(){
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ResponseMessage> getResponses(){
        return msgHandler.getResponses();
    }

    public ArrayList<UpdateMessage> getUpdates(){
        return msgHandler.getUpdates();
    }

}
