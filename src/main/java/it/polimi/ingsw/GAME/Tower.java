package it.polimi.ingsw.GAME;

/**
 * Represents the pawn Tower, which can be placed on an island. It has one and only one player who owns it and an influence value.
 * @author GC56
 */
public class Tower {
    private Player player;
    private static int influence=1;

    /**
     * Tower constructor. It sets the Player who owns the tower.
     * @param p the Player who owns the Tower.
     * @requires p!=null.
     * @ensures this.player.equals(p)
     * @author GC56
     */
    public Tower(Player p){
        this.player=p;
    }

    /**
     * returns the Player who owns the Tower.
     * @return the Player who owns the Tower.
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * It changes the owner of the tower, by setting it with the specified Player.
     * It is used when a Player conquer an island owned by another Player, so the Player who conquer get all the towers of the one who was the owner before.
     * @param player the new Player who owns the Tower.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * returns the value influence of the Tower, used to calculate the influence on an island.
     * @return the value influence of the Tower.
     */
    public static int getInfluence() {
        return influence;
    }

    /**
     * Sets the value influence to 0. It is mainly used by Characters.
     * @ensures this.influence==0
     */
    public static void disable(){
        Tower.influence=0;
    }

    /**
     * Sets the value influence to 1. It is mainly used to restore the value at the end of a turn i which has been used the method disable().
     * @ensures this.influence==1
     */
    public static void enable(){
        Tower.influence=1;
    }

}
