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

    //TODO: radiobuttons
    private ClientMsgHandler msgHandler;
    private Object lock;

    private Stage popupWindow;
    private Button popupButton1;

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


        if(!nickname.getText().equals("")) {
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
        }else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
            popupButton1.setOnAction(e -> {
                popupWindow.close();
            });
        }
    }

    @FXML
    public void joinGame(ActionEvent event) {
        if(!nickname.getText().equals("")) {
            /////////////////////////////////
            waitGameStart();

            if (msgHandler.getResponses().isEmpty()) {
                //startGame();
            } else {
                ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
                //menu();
            }
        }else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
            popupButton1.setOnAction(e -> {
                popupWindow.close();
            });
        }
    }

    @FXML
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
    }

    @FXML
    private void showPopup(String title, String message, String button1) {
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinHeight(300);
        popupWindow.setMinWidth(300);
        Label label = new Label();
        label.setText(message);

        if(button1!=null)
            popupButton1 =new Button(button1);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, popupButton1);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
    }

    @Override
    public void run() {

    }
}
