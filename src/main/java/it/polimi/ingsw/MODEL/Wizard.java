package it.polimi.ingsw.MODEL;

public class Wizard extends ColorTracker{

    public Wizard(Color color) {
        super(color);
    }

    public void changePlayer(Player player){
        this.setPlayer(player);
    }

}


