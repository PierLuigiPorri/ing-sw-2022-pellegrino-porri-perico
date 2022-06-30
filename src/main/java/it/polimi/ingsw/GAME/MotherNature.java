package it.polimi.ingsw.GAME;

/**
 * This class represents the pawn MotherNature, which can move into island.
 * Because of that it contains the attribute island: it says in which island MotherNature is.
 * @author GC56
 */
public class MotherNature {
    private Island island;

    /**
     * MotherNature constructor. It sets the island in which MotherNature is when the game is created.
     * ensures (this.island.equals(island)).
     * @param island the initial island on which MotherNature is going to be.
     * requires (island!=null)
     * @author GC56
     */
    public MotherNature(Island island){
        this.island = island ;
    }

    /**
     * @return the attribute island.
     */
    public Island getIsland() {
        return this.island;
    }

    /**
     * Set (this.island) to the parameter.
     * ensures (this.island.equals(island))
     * @param island island on which MotherNature will be placed.
     * requires (island!=null)
     * @author GC56
     */
    public void setIsland(Island island){
        this.island = island;
    }

}
