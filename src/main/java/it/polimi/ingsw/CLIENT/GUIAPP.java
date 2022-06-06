package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;


public class GUIAPP extends Application implements View {

    private String userNickname;
    private int playersNumber;
    private final Object lock;
    private final ClientMsgHandler msgHandler;
    private final AckSender ackSender;
    private UpdateMessage update;
    @FXML
    private Stage currentStage;

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

    @FXML
    @Override
    public void start(Stage primaryStage) throws Exception {
        currentStage=new Stage();
        MainMenuController.setGUI(this);
        BoardController.setGUI(this);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/mainMenu.fxml")));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        Image icon=new Image("Graphical_Assets/sfondo.jpg");
        currentStage.getIcons().add(icon);
        currentStage.setTitle("Eriantys");
        currentStage.setResizable(false);
        currentStage.show();
    }

    @FXML
    public void setScene(String address){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(address)));
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            double width = gd.getDisplayMode().getWidth()*0.9;
            double height = gd.getDisplayMode().getHeight()*0.9;
            Scene scene = new Scene(root, width, height);
            currentStage.setScene(scene);
            currentStage.centerOnScreen();
            currentStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        delay(1000, () -> startGame());
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    public void waitForMessage(){
        try {
            synchronized (lock) {
                //System.out.println("Ora dormo");
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    public void send(MessageType message){
        msgHandler.send(message);
    }



    public ArrayList<ResponseMessage> getResponses(){
        return msgHandler.getResponses();
    }

    public ArrayList<UpdateMessage> getUpdates(){
        return msgHandler.getUpdates();
    }

    @FXML
    public void startGame(){
        if (!msgHandler.getUpdates().isEmpty()) {
            UpdateMessage firstUpd = msgHandler.getUpdates().remove(msgHandler.getUpdates().size() - 1);
            System.out.println(firstUpd.update);
            update(firstUpd);
            setScene("fxml/board.fxml");
        } else {
            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!msgHandler.getUpdates().isEmpty()) {
                UpdateMessage firstUpd = msgHandler.getUpdates().remove(msgHandler.getUpdates().size() - 1);
                System.out.println(firstUpd.update);
                update(firstUpd);
                setScene("fxml/board.fxml");
            }
            else{
                if (!msgHandler.getResponses().isEmpty()){
                    ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
                    System.out.println(lastMessage.response);
                    setScene("fxml/mainMenu.fxml");
                }
            }
        }
    }

    public void setUserNickname(String nickname){
        this.userNickname=nickname;
    }

    public String getUserNickname(){
        return this.userNickname;
    }

    public void setPlayersNumber(int number){
        this.playersNumber=number;
    }

    public int getPlayersNumber(){
        return this.playersNumber;
    }

    @Override
    public void signalUpdate(){
        System.out.println("è arrivato un update");
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
        this.update=update;
    }

    public UpdateMessage getUpdate(){
        return update;
    }

    @Override
    public void setKill() {

    }
}
