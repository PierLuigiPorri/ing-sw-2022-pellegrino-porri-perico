package it.polimi.ingsw.MODEL;

public class Player {
    public String nickname;
    private Hand hand;
    private Gate gate;
    private Hall hall;
    private int tower_count;
    private int coins;
    public int studentsMoved;
    public static int maxMoves;
    public int teamID=-1;


    public Player(int pcount){
        this.tower_count=6;
        this.gate=new Gate(3, this);
        this.maxMoves=4;
        this.coins=0;
        this.hall=new Hall(this);
        this.hand=new Hand(this);
    }

    public Player(){
        this.tower_count=8;
        this.gate=new Gate(this);
        this.maxMoves=3;
        this.coins=0;
        this.hall=new Hall(this);
        this.hand=new Hand(this);
    }

    public void addCoin(){
        this.coins++;
    }

    public void removeCoin(){
        if(this.coins>0) this.coins--;
    }
}
