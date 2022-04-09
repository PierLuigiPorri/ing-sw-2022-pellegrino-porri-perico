package it.polimi.ingsw.GAME;

public class Wizard extends ColorTracker{

    public Wizard(String color) {
        super(color);
    }

    public void changePlayer(Player player){
        this.setPlayer(player);
    }

}


