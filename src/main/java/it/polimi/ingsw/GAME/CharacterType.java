package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

public interface CharacterType {
    void applyEffect(Player player) throws ImpossibleActionException;
    void effectUsed();
    int getCost();
    int getIndex();
}
