package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;


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
    public void applyEffect(Player player) throws ImpossibleActionException {
        effects.apply(this.index, player);
        effectUsed();
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


}
