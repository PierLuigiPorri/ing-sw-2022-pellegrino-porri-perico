package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;


public class GUIAPP extends Application implements View {

    private final Object lock;
    private final ClientMsgHandler msgHandler;
    private final AckSender ackSender;

    public GUIAPP(){
        lock = new Object();
        msgHandler = new ClientMsgHandler("127.0.0.1", 4000, lock); //Connection setup with this IP and Port numbers
        ackSender = new AckSender(msgHandler, 5000);
    }

    @Override
    public void init(){
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        msgHandler.setView(this);
    }

    @Override
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


    @Override
    public void stop(){
        System.out.println("Thanks for playing!");
        System.exit(0);
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

    @Override
    public void moveMotherNature() {

    }

    @Override
    public void gateToIsland() {

    }

    @Override
    public void gateToHall() {

    }

    @Override
    public void cloudToGate() {

    }

    @Override
    public void playCard() {

    }

    @Override
    public void update(UpdateMessage update) {

    }

    @Override
    public void setKill() {

    }
}
