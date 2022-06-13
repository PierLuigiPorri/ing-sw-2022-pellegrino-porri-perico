package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;

public interface View {

    void moveMotherNature();

    void gateToIsland();

    void gateToHall();

    void cloudToGate();

    void playCard();
    void activateCharacter();

    void update(UpdateMessage update);

    void setKill();

    void signalUpdate();

    void signalResponse();
}
