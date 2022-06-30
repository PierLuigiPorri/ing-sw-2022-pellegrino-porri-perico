package it.polimi.ingsw.GAME;

/**
 * Represents the so-called "Professors". It also maintains the information about the influence of a color.
 * Contains the color of the professor, the Player who owns the professor and the influence of the color (some characters can change the influence of a color).
 * This class is always instantiated multiple times, (5 times each game, as the model stands at the moment)
 * @author GC56
 */
public class ColorTracker {
    private final String color;
    private Player player;
    private int influence = 1;

    /**
     * ColorTracker constructor. It sets the color and the default player who own it (null).
     * this.color.equals(color)
     * @param color the color to be set.
     * (color ! = null)
     * @author GC56
     */
    public ColorTracker(String color) {
        this.color = color;
        this.player = null;
    }

    /**
     * sets an owner (player) to the ColorTracker
     * @param player the player to set to the ColorTracker
     * player!=null
     * @author GC56
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the influence of the color of the ColorTracker.
     */
    public int getInfluence() {
        return influence;
    }


    /**
     * @return the player who owns the ColorTracker
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * sets the influence of a color to 0. This method is most of the time called by a character who disable the influence of a color for a turn.
     * (this.influence==0;)
     */
    public void disableInfluence() {
        influence = 0;
    }

    /**
     * sets the influence of a color to 1. This method is most of the time called at the end of a turn in which the influence of a color was set to 0.
     * (this.influence==1)
     */
    public void restoreInfluence() {
        influence = 1;
    }
}
