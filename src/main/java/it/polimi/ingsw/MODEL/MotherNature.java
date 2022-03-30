package it.polimi.ingsw.MODEL;
import java.util.Random;

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
