package it.polimi.ingsw.GAME;

/**
 * Represents the user who plays the game. Every player has a nickname, a hand of cards, a gate with movable students, a hall with unmovable students, his tower, its last card played from hand and the coins owned.
 * In a game, this class has always multiple instances since the game is playable by 2 or 3 players.
 * Most of its attributes depends on game type (expert rules or not) and players number.
 * @author GC56
 */
public class Player {
    public final String nickname;
    private final Hand hand;
    private final Gate gate;
    private final Hall hall;
    private int tower_count;
    private int coins=0;
    public int studentsMoved;
    public int maxMoves;
    private Card lastCardPlayed;

    /**
     * Player constructor. It constructs the Gate, the Hall and the Hand of a player. Also, depending on players number, sets the number of Towers a Player owns
     * @param pCount the number of Players in the game.
     * @param string the nickname of the Player.
     * @param game the Game in which the player is playing
     * @requires pCount==2 || pCount==3 && string!=null and game!=null
     * @author GC56
     */
    public Player(int pCount, String string, Game game){
        this.nickname=string;
        if(pCount==2){
            this.tower_count=8;
        }
        else{
            this.tower_count=6;
        }
        this.maxMoves=pCount+1;
        this.gate=new Gate(pCount);
        this.hall=new Hall();
        this.hand=new Hand();
        if(game.getGameType()==1)
            addCoin();
    }

    /**
     * Add a coin to this.
     * ensures this.coins-1=\old(this.coins)
     */
    public void addCoin(){
        this.coins++;
    }

    /**
     * Plays a card with the specified index. The index specify the position of the Card in the list of cards in Hand, so it does not always correspond to the attribute Value of a Card.
     * requires index>=0 && index<=10.
     * ensures (!\old(this.hand.getCards.get(index)).equals(this.hand.getCards.get(index)) && (!this.hand.getCard.contains(\old(this.hand.getCards.get(index)))) && (this.lastCardPlayed.equals(\old(this.hand.getCards.get(index))))
     * @param index the index, in Hand, of the card to play.
     * @return the Card played.
     * @author GC56
     */
    public Card playCard(int index){
        Card tmp=this.hand.getCards().get(index);
        this.lastCardPlayed=this.hand.getCards().remove(index);
        return tmp;
    }

    /**
     * returns the Card which is the last card played by this.
     * @return the Card which is the last card played by this.
     */
    public Card getLastCardPlayed() {
        return lastCardPlayed;
    }

    /**
     * returns the nickname of this Player
     * @return the nickname of Player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * returns the hand of this Player
     * @return the hand of Player
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * returns the gate of this Player
     * @return the gate of Player
     */
    public Gate getGate() {
        return gate;
    }


    /**
     * returns the hall of this Player
     * @return the hall of Player
     */
    public Hall getHall() {
        return hall;
    }


    /**
     * returns the number of towers of this Player
     * @return the number of towers of Player
     */
    public int getTower_count() {
        return tower_count;
    }

    /**
     * returns the number of coins owned by this Player
     * @return the number of coins owned by Player
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Reduce the number of coins owned by Player by the specified number.
     * requires (cost<this.coins).
     * ensures (this.coins == \old(this.coins)-cost)
     * @param cost the number of coins to be decreased from coins.
     */
    public void removeCoin(int cost){
        if(this.coins-cost>=0) this.coins=this.coins-cost;
    }

    /**
     * Removes a tower from the list towers.
     * ensures (\old(this.towers.size())==this.towers.size()+1)
     */
    public void removeTower(){this.tower_count--;}

    /**
     * Adds a tower to the list towers.
     * ensures (\old(this.towers.size())==this.towers.size()-1)
     */
    public void addTower(){this.tower_count++;}
}
