package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainMenuController implements View, Runnable{

    ClientMsgHandler msgHandler;
    Object lock;

    public MainMenuController(){}

    public void setMsgHandler(ClientMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public void setLock(Object lock){
        this.lock=lock;
    }

    @FXML
    private TextField nickname;
    @FXML
    private ToggleGroup Players;
    @FXML
    private CheckBox expertGame;
    @FXML
    private ToggleButton twoPlayers, threePlayers;
    @FXML
    private Button newGame, joinGame;
    @FXML
    public PopupControl popupControl;


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
    @FXML
    public void newGame(ActionEvent event) {
        int gt; //Game Type
        int np; //Number of players

        if(expertGame.isSelected()) {
            gt = 1;
        }else gt=0;

        //Number of players request
        if(twoPlayers.isSelected()) {
            np = 2;
        } else np=3;

        try {
            msgHandler.send(new CreationMessage(0, nickname.getText(), gt, np));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        nickname.setDisable(true);
        threePlayers.setDisable(true);
        twoPlayers.setDisable(true);
        expertGame.setDisable(true);
        newGame.setDisable(true);
        joinGame.setDisable(true);

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!msgHandler.getResponses().isEmpty()) {
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            if (lastMessage.allGood) {
                //startGame();
            } else {
                popupControl=new PopupControl();
                //menu();
            }
        }
    }

    @FXML
    public void joinGame(ActionEvent event){
        int choice=0;

        if (choice == 0) {
            try {
                msgHandler.send(new CreationMessage(1, nickname.getText()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 1) {
            int id=0;

            try {
                msgHandler.send(new CreationMessage(2, nickname.getText(), id));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        nickname.setDisable(true);
        threePlayers.setDisable(true);
        twoPlayers.setDisable(true);
        expertGame.setDisable(true);
        newGame.setDisable(true);
        joinGame.setDisable(true);

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (msgHandler.getResponses().isEmpty()) {
            //startGame();
        } else {
            popupControl=new PopupControl();
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            //menu();
        }
    }

    @Override
    public void run() {

    }
}
