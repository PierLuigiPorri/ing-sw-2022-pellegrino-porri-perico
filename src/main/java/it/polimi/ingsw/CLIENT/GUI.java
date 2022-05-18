package it.polimi.ingsw.CLIENT;

public class GUI implements View{
    private ClientMsgHandler msgHandler;

    public GUI(ClientMsgHandler msgHandler){
        this.msgHandler=msgHandler;
    }
    public void moveMotherNature() {}

    public void changePhase(){}

    public void gateToIsland(){}

    public void gateToHall(){}

    public void cloudToGate(){}

    public void playCard(){}
}
