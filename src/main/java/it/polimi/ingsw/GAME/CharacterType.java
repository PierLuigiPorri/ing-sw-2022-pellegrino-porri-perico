package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public interface CharacterType {
    String applyEffect(Player player, ArrayList<Integer> intpar, ArrayList<String> strpar, ArrayList<Integer> intpar2) throws ImpossibleActionException;
    void effectUsed();
    int getCost();
    int getIndex();
    boolean getUsed();
}
