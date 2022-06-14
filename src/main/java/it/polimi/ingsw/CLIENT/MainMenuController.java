package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainMenuController{
    private int gt; //Game Type
    private int np; //Number of players

    private static GUIAPP gui;
    @FXML
    private Stage popupWindow;
    @FXML
    private Button popupButton1;
    @FXML
    private TextField nicknameTextField, idTextField;
    @FXML
    private CheckBox expertCheck;
    @FXML
    private RadioButton twoPlayers, threePlayers;
    @FXML
    private ToggleGroup nPlayers;
    @FXML
    private RadioButton randomGame, idGame;
    @FXML
    private AnchorPane newPane, joinPane;

    public static void setGUI(GUIAPP guiApp){
        gui=guiApp;
    }

    @FXML
    private void startJoin(){
        if (!nicknameTextField.getText().equals("")) {
            if(randomGame.isSelected()){
                try {
                    gui.send(new CreationMessage(1, nicknameTextField.getText()));
                    //gui.startGame();
                    delay(1000, () -> checkJoin("Random"));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else if(idGame.isSelected()){
                try {
                    Integer id=Integer.parseInt(idTextField.getText());
                    gui.send(new CreationMessage(2, nicknameTextField.getText(),id));
                    //gui.startGame();
                    delay(1000, () -> checkJoin(id.toString()));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                System.out.println("Errore");
                System.exit(0);
            }
            gui.setUserNickname(nicknameTextField.getText());
            System.out.println((nicknameTextField.getText()));
        } else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!");
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    public void delay(long millis, Runnable continuation) {
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

    private void checkJoin(String gameid) {
        if(!gui.gameStarted){
            if (gui.getResponses().isEmpty()) {
                gui.setGameid(gameid);
                gui.setScene("fxml/waitGameToStart.fxml");
                gui.setUserNickname(nicknameTextField.getText());
            } else {
                ResponseMessage lastMessage = gui.getResponses().remove(gui.getResponses().size() - 1);
                showPopup(lastMessage.response, lastMessage.response);
                popupButton1.setOnAction(e -> popupWindow.close());
                popupWindow.showAndWait();
            }
        }
    }

    @FXML
    private void startNew(){

        if (expertCheck.isSelected()) {
            gt = 1;
        } else gt = 0;
        //Number of players
        if(nPlayers.getSelectedToggle().equals(twoPlayers)){
            np=2;
        }
        else if(nPlayers.getSelectedToggle().equals(threePlayers)){
            np=3;
        }
        else{
            System.out.println("Errore");
            System.exit(0);
            np=0;
        }
        if(!nicknameTextField.getText().equals("")) {
            try {
                gui.send(new CreationMessage(0, nicknameTextField.getText(), gt, np));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            gui.waitForMessage();
            if (!gui.getResponses().isEmpty()) {
                ResponseMessage lastMessage = gui.getResponses().remove(gui.getResponses().size() - 1);
                if (lastMessage.allGood) {
                    gui.setGameid(lastMessage.gameid.toString());
                    gui.setScene("fxml/waitGameToStart.fxml");
                    gui.setUserNickname(nicknameTextField.getText());
                } else {
                    showPopup("ERROR", "The creation of the game failed");
                    popupButton1.setOnAction(e -> popupWindow.close());
                    popupWindow.showAndWait();
                }
            }
        }else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!");
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    @FXML
    private void newGame() {
        joinPane.setVisible(false);
        newPane.setVisible(true);
    }

    @FXML
    private void joinGame() {
        newPane.setVisible(false);
        joinPane.setVisible(true);
    }
    @FXML
    private void showPopup(String title, String message) {
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinHeight(100);
        popupWindow.setMinWidth(100);
        Label label = new Label();
        label.setText(message);
        popupButton1 =new Button("OK");
        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, popupButton1);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
    }
}