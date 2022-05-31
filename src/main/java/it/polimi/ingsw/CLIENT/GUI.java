package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class GUI extends Application implements Runnable, View {

    public static void main(String[] args) {
        launch(args);
    }

    public ClientMsgHandler msgHandler;
    @FXML
    public TextField nickname;
    @FXML
    public ToggleGroup playersnum;
    @FXML
    public CheckBox expertGame;
    @FXML
    public RadioButton twoPlayers, threePlayers;
    @FXML
    public Button newGame, joinGame;

    public GUI(){

    }

    public GUI(ClientMsgHandler msgHandler){
        this.msgHandler=msgHandler;
    }

    @Override
    public void start(Stage primaryStage) {
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
    public void run() {

    }
}
