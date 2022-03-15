package it.polimi.ingsw.MODEL;

public class MotherNature {
    private Island island;


    public MotherNature(Island island){
        this.island = island;
        island.studenti=null;
        island.madreNatura=true;
    }

    public Island getIsola() {
        return island;
    }

    public void setIsola(Island island) {
        this.island = island;
    }
}
