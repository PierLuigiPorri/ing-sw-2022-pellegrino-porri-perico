package it.polimi.ingsw.MODEL;

public class Player {
    private String nickname;
    private Cards[] hand;
    private Gate gate;
    private Hall hall;
    private int tower_count;
    private int coins;


    public Player(){
        hall= new Hall(this);
        this.coins=0;
    }

    public void addCoin(){
        this.coins++;
    }


}
