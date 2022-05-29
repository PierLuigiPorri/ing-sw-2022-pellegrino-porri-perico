package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.UpdateMessage;

public class GUI implements View, Runnable{
    private ClientMsgHandler msgHandler;

    public GUI(ClientMsgHandler msgHandler){
        this.msgHandler=msgHandler;
    }

    public void moveMotherNature() {}

    public void gateToIsland(){}

    public void gateToHall(){}

    public void cloudToGate(){}

    public void playCard(){}

    @Override
    public void update(UpdateMessage update) {

    }

    @Override
    public void run() {

    }
}
