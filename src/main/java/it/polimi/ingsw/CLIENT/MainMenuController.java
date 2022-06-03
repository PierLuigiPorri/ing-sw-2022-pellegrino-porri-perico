package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.EXCEPTIONS.MessageCreationError;
import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainMenuController implements Runnable {
    private static GUIAPP GUI;

    //TODO: radiobuttons
    private Stage popupWindow;
    private Button popupButton1;

    @FXML
    private TextField nickname, idGame;
    @FXML
    private CheckBox expertGame;
    @FXML
    private ToggleButton twoPlayers, threePlayers;
    @FXML
    private Button newGame, joinGame;
    @FXML
    private RadioButton randomGame, IDGame;
    @FXML
    private Pane join, mainButtons;

    public static void setGUI(GUIAPP gui){
        GUI=gui;
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
    public void newGame() {
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
                GUI.send(new CreationMessage(0, nickname.getText(), gt, np));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            waitGameStart();

            if (!GUI.getResponses().isEmpty()) {
                ResponseMessage lastMessage = GUI.getResponses().remove(GUI.getResponses().size() - 1);
                if (lastMessage.allGood) {
                    //startGame();TODO: PASSA A NUOVA SCENA E CHIUDE TUTTO QUELLO CHE Cè APERTO
                } else {
                    //menu(); TODO: POPUP ERRORE E RIMANE DOVE SEI
                }
            }
        }else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    @FXML
    public void joinGame() {
        if (!nickname.getText().equals("")) {

            disableButtons();

            mainButtons.setVisible(false);

            join.setVisible(true);


            IDGame.setDisable(true);


        } else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    public void setIDGameButton(){
        IDGame.setDisable(false);
        randomGame.setDisable(false);
    }

    public void startButton() {

        int choice=0;

        if (randomGame.isSelected()) {
            choice = 0;
        } else if (IDGame.isSelected()) {
            choice = 1;
        }

        try {
            if (choice == 0) {
                GUI.send(new CreationMessage(1, nickname.getText()));
            }
            if (choice == 1) {
                GUI.send(new CreationMessage(2, nickname.getText(), Integer.parseInt(idGame.getText())));
            }
        } catch (NumberFormatException | MessageCreationError e) {
            System.out.println(e.getMessage());
        }
        waitGameStart();
        if (GUI.getResponses().isEmpty()) {
            //startGame();
        } else {
            ResponseMessage lastMessage = GUI.getResponses().remove(GUI.getResponses().size() - 1);
            //menu();
        }
    }

    @FXML
    private void waitGameStart() {
        disableButtons();
        GUI.waitForMessage();
        GUI.setGameScene("fxml/waitGameToStart.fxml");
    }

    @FXML
    private void showPopup(String title, String message, String button1) {
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinHeight(100);
        popupWindow.setMinWidth(100);
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