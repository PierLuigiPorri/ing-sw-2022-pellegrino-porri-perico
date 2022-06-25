package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

/**
 * Interface that defines methods used by Abstract and Concrete Characters. Implemented by AbstractCharacter and ConcreteCharacter.
 * @author GC56
 */
public interface CharacterType {
    /**
     * Applies the effect of the card. Called by the CharacterSelector class.
     */
    String applyEffect(Player player, ArrayList<Integer> intpar, ArrayList<String> strpar, ArrayList<Integer> intpar2) throws ImpossibleActionException;

    /**
     * Sets the boolean parameter used in the card to true, indicating that the card has already been used at least once.
     */
    void effectUsed();

    /**
     * Returns the cost of the card.
     * @return Integer value between 1-3.
     */
    int getCost();

    /**
     * Returns the index of the card.
     * @return Integer value between 0-11.
     */
    int getIndex();

    /**
     * Returns a boolean value indicating if the effect of the card has already been used once.
     * @return True if the effect has already been used, false otherwise.
     */
    boolean getUsed();
}
