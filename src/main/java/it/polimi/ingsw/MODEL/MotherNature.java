package it.polimi.ingsw.MODEL;
import java.util.Random;

public class MotherNature implements Pawn{
    private int island;

    public MotherNature(){
        Random rand = new Random();
        this.island= rand.nextInt(12);
    }

    public int getIsola() {
        return island;
    }

    public void setIsola(int addition) {
        this.island = this.island+addition;
    } //QUANDO ISLAND SUPERA 11, VA FATTA TORNARE A ZERO

    @Override
    public void moveIntoIsland(int i){

    }
}
