package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
    private Button newGame, joinGame, startNew, startJoin;
    @FXML
    private RadioButton randomGame, idGame;
    @FXML
    private ToggleGroup joinType;
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
                    Thread.sleep(2000);
                    if (gui.getResponses().isEmpty()) {
                        gui.setScene("fxml/waitGameToStart.fxml");
                    } else {
                        ResponseMessage lastMessage = gui.getResponses().remove(gui.getResponses().size() - 1);
                        showPopup(lastMessage.response, lastMessage.response,"ok");
                        popupButton1.setOnAction(e -> popupWindow.close());
                        popupWindow.showAndWait();
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else if(idGame.isSelected()){
                try {
                    int id=Integer.parseInt(idTextField.getText());
                    gui.send(new CreationMessage(2, nicknameTextField.getText(),id));
                    Thread.sleep(2000);
                    if (gui.getResponses().isEmpty()) {
                        gui.setScene("fxml/waitGameToStart.fxml");
                    } else {
                        ResponseMessage lastMessage = gui.getResponses().remove(gui.getResponses().size() - 1);
                        showPopup(lastMessage.response, lastMessage.response,"ok");
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
        } else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    @FXML
    private void startNew(){
        int gt; //Game Type
        int np; //Number of players

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
                    gui.setScene("fxml/waitGameToStart.fxml");
                } else {
                    showPopup("ERROR", "The creation of the game failed", "ok");
                    popupButton1.setOnAction(e -> popupWindow.close());
                    popupWindow.showAndWait();
                }
            }
        }else {
            showPopup("NICKNAME REQUIRED", "You need to set your nickname first!", "ok");
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
}