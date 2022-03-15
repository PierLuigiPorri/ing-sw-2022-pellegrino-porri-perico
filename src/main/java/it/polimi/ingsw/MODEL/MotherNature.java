package it.polimi.ingsw.MODEL;
import java.util.Random;

public class MotherNature {
    private int island;

    public MotherNature(){
        Random rand = new Random();
        this.island=Game.setMotherNature(rand.nextInt(12));
        island.students=null;
        island.motherNature=true;
    }

    public Island getIsola() {
        return island;
    }

    public void setIsola(Island island) {
        this.island = island;
    }
}
