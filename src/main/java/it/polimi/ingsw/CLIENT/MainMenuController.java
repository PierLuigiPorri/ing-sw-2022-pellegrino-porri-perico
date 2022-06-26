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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class for the Main Menu scene. Lets the user decide between creating a new game or joining
 * an existing one, each option with their own parameters to be given. Then moves on to the Wait scene if
 * the action is performed correctly.
 * @author GC56
 */
public class MainMenuController{

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

    /**
     * Method associated with an FXML button. Initiates the join process. If "random game" is selected, it will attempt
     * to join the first available game. Otherwise, it will attempt to join a game with the given id.
     * Will return an error if no games are available with the given parameters.
     */
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
                    if(!idTextField.getText().isEmpty()) {
                        int id = Integer.parseInt(idTextField.getText());
                        gui.send(new CreationMessage(2, nicknameTextField.getText(), id));
                        //gui.startGame();
                        delay(1000, () -> checkJoin(Integer.toString(id)));
                    }
                    else{
                        showPopup("ERROR", "Set the game ID first!");
                        popupButton1.setOnAction(e -> popupWindow.close());
                        popupWindow.showAndWait();
                    }
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

    /**
     * Applies a delay to a given action.
     * @param millis The delay to be applied. Long parameter.
     * @param continuation The action to be performed after the delay.
     */
    public void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    /**
     * Checks if the joining process with the given id has gone through successfully.
     */
    private void checkJoin(String gameid) {
        if(!gui.gameStarted){
            if (gui.getResponses().isEmpty()) {
                gui.setGameid(gameid);
                gui.setScene("fxml/waitGameToStart.fxml");
                gui.setUserNickname(nicknameTextField.getText());
            }
        }
    }

    /**
     * Method associated with an FXML button. Starts the creation process. Attempts to send a creation request
     * to the server with the given parameters. If done successfully, moves on to the Wait scene.
     */
    @FXML
    private void startNew(){

        //Game Type
        int gt;
        if (expertCheck.isSelected()) {
            gt = 1;
        } else gt = 0;
        //Number of players
        //Number of players
        int np;
        if(nPlayers.getSelectedToggle().equals(twoPlayers)){
            np =2;
        }
        else if(nPlayers.getSelectedToggle().equals(threePlayers)){
            np =3;
        }
        else{
            System.out.println("Errore");
            System.exit(0);
            np =0;
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
        popupWindow.setMinHeight(200);
        popupWindow.setMinWidth(200);
        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font("papyrus",16));
        popupButton1 =new Button("OK");
        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, popupButton1);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
    }
}