package it.polimi.ingsw.GAME;

public class Player {
    public final String nickname;
    private final Hand hand;
    private final Gate gate;
    private final Hall hall;
    private int tower_count;
    private int coins;
    public int studentsMoved;
    public int maxMoves;
    private Card lastCardPlayed;

    public Player(int pcount, String string, Game game){
        this.nickname=string;
        if(pcount==2){
            this.tower_count=8;
        }
        else{
            this.tower_count=6;
        }
        this.maxMoves=pcount+1;
        this.gate=new Gate(pcount);
        this.hall=new Hall();
        this.hand=new Hand();
    }

    public void addCoin(){
        this.coins++;
    }

    public Card playCard(int index){
        Card tmp=this.hand.cards.get(index);
        this.lastCardPlayed=this.hand.cards.remove(index);
        return tmp;
    }

    public Card getLastCardPlayed() {
        return lastCardPlayed;
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
