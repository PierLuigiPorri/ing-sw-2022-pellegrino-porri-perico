package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application implements Runnable, View {

    public static void main(String[] args) {
        launch(args);
    }

    ClientMsgHandler msgHandler;

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
