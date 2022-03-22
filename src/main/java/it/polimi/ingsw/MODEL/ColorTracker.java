package it.polimi.ingsw.MODEL;

public class ColorTracker {
    protected Color color;
    private Player player;

    public ColorTracker(Color color){
        this.color=color;
        this.player=null;
    }
    public Color getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public void changePlayer(Player player){
        this.player=player;
    }
}
