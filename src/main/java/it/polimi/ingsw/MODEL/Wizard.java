package it.polimi.ingsw.MODEL;

public class Wizard {
    protected Color color;
    private Player player;

    public void changePlayer(Player player){
        this.player=player;
    }

    public Color getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

}


