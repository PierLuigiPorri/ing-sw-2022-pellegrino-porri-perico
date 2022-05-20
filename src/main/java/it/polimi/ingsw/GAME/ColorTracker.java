package it.polimi.ingsw.GAME;

public class ColorTracker {
    private final String color;
    private Player player;
    private int influence=1;

    public ColorTracker(String color){
        this.color=color;
        this.player=null;
    }

    public void setPlayer(Player player){
        this.player=player;
    }

    public int getInfluence() {
        return influence;
    }

    public String getColor() {
        return color;
    }

    public Player getPlayer() {
        return player;
    }

    public void disableInfluence(){
        influence=0;
    } //TODO: BISOGNA FARE IN MODO CHE QUESTO METODO DURI SOLO UN TURNO!
                                                    //lo faccio io nell'effetto della carta che chiamerà questo metodo. -Doot
    public void restoreInfluence(){
        influence=1;
    }
}
