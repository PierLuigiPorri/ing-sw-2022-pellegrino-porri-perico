package it.polimi.ingsw.CLIENT;

import com.sun.javafx.scene.control.IntegerField;
import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuController implements Runnable {

    private ClientMsgHandler msgHandler;
    private Object lock;
    private Stage popupWindow;
    private Button popupButton1, popupButton2;
    private IntegerField popupIntField;

    @FXML
    private TextField nickname;
    @FXML
    private CheckBox expertGame;
    @FXML
    private ToggleButton twoPlayers, threePlayers;
    @FXML
    private Button newGame, joinGame;

    public MainMenuController() {
    }

    public void setMsgHandler(ClientMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public void update(UpdateMessage update) {

    }

    public void setKill() {

    }

    public void enableButtons(){
        threePlayers.setDisable(false);
        twoPlayers.setDisable(false);
        expertGame.setDisable(false);
        newGame.setDisable(false);
        joinGame.setDisable(false);
    }

    public void disableButtons(){
        nickname.setDisable(true);
        threePlayers.setDisable(true);
        twoPlayers.setDisable(true);
        expertGame.setDisable(true);
        newGame.setDisable(true);
        joinGame.setDisable(true);
    }

    @FXML
    public void newGame(ActionEvent event) {
        int gt; //Game Type
        int np; //Number of players

        if (expertGame.isSelected()) {
            gt = 1;
        } else gt = 0;

        //Number of players request
        if (twoPlayers.isSelected() && !threePlayers.isSelected()) {
            np = 2;
        } else if (!twoPlayers.isSelected() && threePlayers.isSelected()) np = 3;
        else np = 0;


        try {
            msgHandler.send(new CreationMessage(0, nickname.getText(), gt, np));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        waitGameStart();

        if (!msgHandler.getResponses().isEmpty()) {
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            if (lastMessage.allGood) {
                //startGame();
            } else {
                //menu();
            }
        }
    }

    @FXML
    public void joinGame(ActionEvent event) {
        displayJoinGamePopup("Join Game", "Which option do you prefer?");
        waitGameStart();

        if (msgHandler.getResponses().isEmpty()) {
            //startGame();
        } else {
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            //menu();
        }
    }

    private void waitGameStart() {
        disableButtons();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/waitGameToStart.fxml")));
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setScene(scene);
            Image icon = new Image("Graphical_Assets/sfondo.jpg");
            window.getIcons().add(icon);
            window.setTitle("Eriantys");
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        enableButtons();
    }

    public void displayJoinGamePopup(String title, String message) {

        showPopup(title, message, "Join a random Game", "Join game with ID", "Enter here the ID of the game you want to join:");

        popupButton1.setDefaultButton(true);
        popupButton1.setOnAction(e -> {
            try {
                msgHandler.send(new CreationMessage(1, nickname.getText()));
            } catch (Exception k) {
                System.out.println(k.getMessage());
            }
            popupWindow.close();
        });

        popupButton2.setOnAction(e -> {
            try {
                msgHandler.send(new CreationMessage(2, nickname.getText(), popupIntField.getValue()));
            } catch (Exception k) {
                System.out.println(k.getMessage());
            }
            popupWindow.close();
        });

        popupWindow.showAndWait();
    }

    private void showPopup(String title, String message, String button1, String button2, String intField) {
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinHeight(300);
        popupWindow.setMinWidth(300);
        Label label = new Label();
        label.setText(message);
        Label label1=new Label();

        if(button1!=null)
            popupButton1 =new Button(button1);
        if(button2!=null)
            popupButton2 =new Button(button2);
        if(intField!=null){
            label1.setText(intField);
            popupIntField=new IntegerField();
        }

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, popupButton1, popupButton2, label1, popupIntField);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
    }

    @Override
    public void run() {

    }
}
