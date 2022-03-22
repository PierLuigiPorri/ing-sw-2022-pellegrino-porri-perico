package it.polimi.ingsw.MODEL;
import java.util.Random;

public class MotherNature {
    private int island;

    public MotherNature(){
        Random rand = new Random();
        this.island= GameMaster.setMotherNature(rand.nextInt(12));
    }

    public int getIsola() {
        return island;
    }

    public void setIsola(int island) {
        this.island = island;
    }
}
