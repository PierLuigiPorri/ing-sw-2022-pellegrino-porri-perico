package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.CreationMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainMenuController implements View, Runnable{

    ClientMsgHandler msgHandler;
    final Object lock;

    public MainMenuController(ClientMsgHandler msgHandler, Object lock){
        this.msgHandler=msgHandler;
        this.lock=lock;
    }

    @FXML
    public TextField nickname;
    public ToggleGroup Players;
    public CheckBox expertGame;
    public ToggleButton twoPlayers, threePlayers;
    public Button newGame, joinGame;


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

    public void newGame() {
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
                //menu();
            }
        }
    }

    @Override
    public void run() {

    }
}
