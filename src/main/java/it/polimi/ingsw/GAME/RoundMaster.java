package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Represents the collection of all the rounds of a Game. It counts the number of round passed,
 * it contains the current Round's information, the order in which the player are going to play in the next phase of a round.
 * It also creates every Round.
 * @author GC56
 */
public class RoundMaster {
    private int roundCount;
    public Round round;
    private ArrayList<Player> order;

    /**
     * RoundMaster constructor. It constructs the order list and creates the first Round.
     * @param players the list of players that are playing the Game.
     * @requires players!=null
     */
    public RoundMaster(ArrayList<Player> players) {
        this.roundCount = 0;
        this.order = players;
        round = new Round(players);
    }

    /**
     * Starts a new Round. It's called at the end of a Turn, when every player has completed the Action phase, so at most 10 times each Game as the model stands at the moment.
     */
    private void startRound() {
        round = new Round(order);
    }

    /**
     * Changes the current phase of a Round. If the current phase is Planning, then is time to go to the Action phase of the Round.
     * If is Action phase, then is time to construct a new Round. It returns the order in which the players are going to play in the next phase.
     * It is called when every player has correctly finished the current phase.
     * @return the order in which the players are going to play in the new phase.
     * @ensures !this.round.getCurrentPhase.equals(\old(this.round.getCurrentPhase)) && (\forAll int i; i>=0 && i<this.order.size(); this.order.contains(this.order.get(i)))
     */
    public ArrayList<Player> changePhase() {

        if (this.round.getCurrentPhase().equals("Planning")) {
            //If the current Round is in Planning phase, then the phase is set to Action.
            order = round.nextOrder();
            round.setCurrentPhase("Action");
        } else {
            //If the current phase is Action, then a new Turn need to be created and the number of Rounds is increased by one.
            roundCount++;
            order = round.nextOrder();

            startRound();
        }
        return order;
    }

    /**
     * Returns the number of the Round in which the Game is.
     * @return the number of rounds finished.
     */
    public int getRoundCount() {
        return roundCount;
    }

}
