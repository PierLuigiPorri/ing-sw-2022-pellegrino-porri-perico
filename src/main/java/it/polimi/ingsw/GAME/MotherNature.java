package it.polimi.ingsw.GAME;

public class MotherNature {
    private Island island;


    public MotherNature(Island island){
        this.island = island ;
    }

    public Island getIsola() {
        return this.island;
    }

    public void setIsland(Island island){
        this.island = island;
    }

}