package it.polimi.ingsw.MODEL;
import java.util.Random;

public class MotherNature {
    private Island island;
    private Board board;


    public MotherNature(Island island){
        Random rand = new Random();
        this.island = island ;
    }

    public Island getIsola() {
        return this.island;
    }

    public void setIsland(Island island){
        this.island = island;
    }

}
