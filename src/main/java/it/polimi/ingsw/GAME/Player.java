package it.polimi.ingsw.GAME;

public class Player {
    //private final Game game;
    public final String nickname;
    private final Hand hand;
    private final Gate gate;
    private final Hall hall;
    private int tower_count;
    private int coins;
    public int studentsMoved;
    public int maxMoves;

    public Player(int pcount, String string, Game game){
        //this.game=game;
        this.nickname=string;
        if(pcount==2){
            this.tower_count=8;
            this.maxMoves=3;
        }
        else{
            this.tower_count=6;
            this.maxMoves=4;
        }
        this.gate=new Gate(pcount);

        this.hall=new Hall();
        this.hand=new Hand(this);
    }

    public void addCoin(){
        this.coins++;
    }

    public Card playCard(int index){
        Card tmp=this.hand.cards.get(index);
        this.hand.cards.remove(index);
        return tmp;
    }

    public String getNickname() {
        return nickname;
    }

    public Hand getHand() {
        return hand;
    }

    public Gate getGate() {
        return gate;
    }

    public Hall getHall() {
        return hall;
    }

    public int getTower_count() {
        return tower_count;
    }

    public int getCoins() {
        return coins;
    }

    public void removeCoin(int cost){
        if(this.coins>0) this.coins=this.coins-cost;
    }

    public void removeTower(){this.tower_count--;}
}
