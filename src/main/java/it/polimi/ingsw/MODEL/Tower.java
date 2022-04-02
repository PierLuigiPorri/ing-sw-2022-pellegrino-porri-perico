package it.polimi.ingsw.MODEL;

public class Tower {
    private Player player;
    private static int influence=1;

    public Tower(Player p){
        this.player=p;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getInfluence() {
        return influence;
    }

    public static void disable(){
        Tower.influence=0;
    }
    public static void enable(){
        Tower.influence=1;
    }

}
