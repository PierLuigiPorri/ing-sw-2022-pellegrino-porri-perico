package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

/**
 * Class that stores all information about a specific Abstract (a card that can't contain students) that is in play. Stores cost, index and if it's been used already.
 */
public class AbstractCharacter implements CharacterType{
    private int cost;
    private final int index;
    private boolean used=false;
    private final Effects effects;

    /**
     * Constructor of the class. Sets index, cost and Effects instance for future use.
     * @param index Int parameter between 1-11.
     * @param cost Int parameter between 1-3.
     * @param effects Effects instance to be accessed when applying the effect.
     */
    public AbstractCharacter(int index, int cost, Effects effects){
        this.effects=effects;
        this.cost=cost;
        this.index=index;
    }

    /**
     * Overridden method form the CharacterType interface. Passes the right parameters to the Effects class, calling the applyAbstract method.
     */
    @Override
    public String applyEffect(Player player, ArrayList<Integer> par3, ArrayList<String> parA4, ArrayList<Integer> parC4) throws ImpossibleActionException {
        effectUsed();
        return effects.applyAbstract(this.index, player, par3, parA4);
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public void effectUsed() {
        if(!this.used){
            this.cost++;
            this.used=true;
        }
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public boolean getUsed() {
        return used;
    }


}
