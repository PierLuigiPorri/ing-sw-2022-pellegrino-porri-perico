package it.polimi.ingsw.GAME;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The main purpose of the class is to manage the order in which the players are going to play.
 * It also contains the information about the current phase.
 */
public class Round {
    private final ArrayList<Player> player;
    private ArrayList<Player> order;
    private String currentPhase;

    /**
     * Round constructor. It constructs the list Order, in which will be identified the order of the next phase.
     * It sets the current phase to Planning phase, since when a round is created is always in this one.
     * The method is called at most 10 times each Game.
     * @param players the list of players that are playing.
     * @requires players!=null && (players.size()==2 || players.size()==3)
     * @ensures this.order.equals(players) && this.currentPhase.equals("Planning")
     * @author GC56
     */
    public Round(ArrayList<Player> players){
        order=new ArrayList<>();
        this.player=new ArrayList<>();
        int i=0;
        while(i<players.size()) {
            this.player.add(players.get(i));
            i++;
        }

        this.currentPhase="Planning";
    }

    /**
     * Returns the current phase of this Round.
     * @return the current phase of this Round.
     * @author GC56
     */
    public String getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the specified phase to this Turn's phase.
     * @param currentPhase the phase to be assigned to the Turn.
     * @requires currentPhase!=null
     * @ensures this.currentPhase.equals(currentPhase)
     * @author GC56
     */
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * This method returns the order in which the players are going to play.
     * It's usually called twice per Round to determine the next phase's order.
     * @ensures (\forAll int i; i>=0 && i<this.player.size(); this.order.contains(this.player.get(i)))
     * @author GC56
     */
    public ArrayList<Player> nextOrder() {
        //sorting the players list by the value of their last card played.
        this.order.addAll(this.player);
        order.sort(Comparator.comparingInt(o -> o.getLastCardPlayed().getValue()));
        return this.order;
    }

}
