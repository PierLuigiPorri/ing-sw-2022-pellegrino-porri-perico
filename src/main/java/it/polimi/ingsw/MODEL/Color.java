package it.polimi.ingsw.MODEL;

public class Color {
    protected Colors color;
    private Player player;

    public Color(Colors color){
        this.color=color;
        this.player=null;
    }
    public Colors getColore() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public void changePlayer(Player player){
        this.player=player;
    }
}


