package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.MessageType;

import java.io.IOException;

public interface View {

    void moveMotherNature();

    void changePhase();

    void gateToIsland();

    void gateToHall();

    void cloudToGate();

    void playCard();

    void relay(MessageType msg);

}
