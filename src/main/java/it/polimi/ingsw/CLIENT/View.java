package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;

/**
 * Interface implemented by the main View classes, CLI and GUI.
 * @author GC56
 */
public interface View {

    /**
     * Sends the message to the server with the request to move MotherNature, and the needed parameters.
     */
    void moveMotherNature();

    /**
     * Sends the message to the server with the request to move a student from the Gate to an Island, and the needed parameters.
     */
    void gateToIsland();

    /**
     * Sends the message to the server with the request to move a Student from the Gate to the Hall, and the needed parameters.
     */
    void gateToHall();

    /**
     * Sends the message to the server with the request to move the Students on a Cloud to the Gate, and the needed parameters.
     */
    void cloudToGate();

    /**
     * Sends the message to the server with the request to play a Card, and the needed parameters.
     */
    void playCard();

    /**
     * Sends the message to the server with the request to activate the effect of a Character, and the needed parameters.
     */
    void activateCharacter();

    /**
     * Updates the stored UpdateMessage with the new one. Called internally after an update arrives.
     */
    void update(UpdateMessage update);


    /**
     * Called by the ClientMsgHandler class when an update arrives. Applies the update and stores it.
     */
    void signalUpdate();

    /**
     * Called by the ClientMsgHandler class when a Response arrives. Handles the response.
     */
    void signalResponse();
}
