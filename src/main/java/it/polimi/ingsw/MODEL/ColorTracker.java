package it.polimi.ingsw.MODEL;

public class ColorTracker {
    protected Color color;
    private Player player;
    private int influence=1;

    public ColorTracker(Color color){
        this.color=color;
        this.player=null;
    }

    public int getInfluence() {
        return influence;
    }

    public Color getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public void disableInfluence(){
        influence=0;
    } //TODO: BISOGNA FARE IN MODO CHE QUESTO METODO DURI SOLO UN TURNO!
}
