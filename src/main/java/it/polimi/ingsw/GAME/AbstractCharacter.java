package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;


public class AbstractCharacter implements CharacterType{
    private int cost;
    private final int index;
    private boolean used=false;
    private final Effects effects;

    public AbstractCharacter(int index, int cost, Effects effects){
        this.effects=effects;
        this.cost=cost;
        this.index=index;
    }

    @Override
    public String applyEffect(Player player, ArrayList<Integer> par3, ArrayList<String> parA4, ArrayList<Integer> parC4) throws ImpossibleActionException {
        effectUsed();
        return effects.apply(this.index, player, par3, parA4);
    }

    @Override
    public void effectUsed() {
        if(!this.used){
            this.cost++;
            this.used=true;
        }
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public boolean getUsed() {
        return used;
    }


}
