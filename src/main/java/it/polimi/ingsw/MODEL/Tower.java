package it.polimi.ingsw.MODEL;

public class Tower {
    private Player player;
    private Island island;
    private int influence=1;

    public Tower(Player p){
        this.player=p;
        this.island=null;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Island getIsland() {
        return island;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getInfluence() {
        return influence;
    }
}
