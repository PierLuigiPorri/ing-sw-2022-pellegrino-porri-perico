package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public interface CharacterType {
    void applyEffect(Player player, int parAC1, String parA2, ArrayList<Integer> parAC3, ArrayList<String> parA4, int parC2, ArrayList<Integer> parC4) throws ImpossibleActionException;
    void effectUsed();
    int getCost();
    int getIndex();
}
