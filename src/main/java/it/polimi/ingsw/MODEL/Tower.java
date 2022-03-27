package it.polimi.ingsw.MODEL;

public class Tower implements Pawn{
    private Player player;
    private Island island;
    private final int influenza=1;

    public Tower(Player p){
        this.player=p;
        this.island=null;
    }
    @Override
    public void moveIntoIsland(int i){
        this.island.id=i;
        //da levare, tower non dovrebbe avere i metodi che la spostano ma dobrebbe essere spostata dagli altri
        //(ovvero in questo caso verrà direttamente creata in loco dall'isola) --Doot
    }
    public Player getPlayer(){
        return this.player;
    }
}
