package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.io.IOException;

public interface View {

    void moveMotherNature();

    void gateToIsland();

    void gateToHall();

    void cloudToGate();

    void playCard();

    void update(UpdateMessage update);
}
