package it.polimi.ingsw.GAME;

/**
 * This interface represents every class that can be considered as a space that can contain prohibition cards.
 */
public interface TDSpace {
    /**
     * It adds a prohibition card to the TDSpace. It is mainly used by characters.
     */
    void addTD();

    /**
     * It removes a prohibition card from the TDSpace. It is used at the end of a turn in which a prohibition card has been added to a TDSpace.
     */
    void removeTD();
}
