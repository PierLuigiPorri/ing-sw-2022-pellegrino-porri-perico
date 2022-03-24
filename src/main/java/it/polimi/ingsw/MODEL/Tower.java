package it.polimi.ingsw.MODEL;

public class Tower implements Pawn{
    private Player player;
    private IslandType islandType;
    private int influenza;

    @Override
    public void moveIntoIsland(int i){
        //da levare, tower non dovrebbe avere i metodi che la spostano ma dobrebbe essere spostata dagli altri
        //(ovvero in questo caso verrà direttamente creata in loco dall'isola) --Doot
    }
    public Player getPlayer(){
        return this.player;
    }
}
