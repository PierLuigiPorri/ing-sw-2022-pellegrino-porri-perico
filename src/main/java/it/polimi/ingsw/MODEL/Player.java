package it.polimi.ingsw.MODEL;

public class Player {
    private Controller game;
    public final String nickname;
    private final Hand hand;
    private final Gate gate;
    private final Hall hall;
    private int tower_count;
    private Board board;
    private int coins;
    public int studentsMoved;
    public int maxMoves;
    public int teamID=-1;

    public Player(int pcount, String string){
        this.nickname=string;
        this.tower_count=6;
        this.gate=new Gate(3, this);
        this.maxMoves=4;
        if(game.getGameType()==0){ //Regole semplificate attivate
            this.coins=0;
        }
        else{ //Regole esperto attivate
            this.coins=1;
        }
        this.hall=new Hall(this);
        this.hand=new Hand(this);
    }

    public Player(String string, Controller game){
        this.game=game;
        this.nickname=string;
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

    public Card playCard(int index){
        Card tmp=this.hand.cards[index];
        this.hand.cards[index]=null;
        return tmp;
    }

    public Controller getGame() {
        return game;
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

    public Board getBoard() {
        return board;
    }

    public Color[] getColorDominated(Player p2, Player p3, Player p4){
        Color[] color=new Color[5];
        if(this.hall.getRed()==Math.max(Math.max(this.hall.getRed(), p2.hall.getRed()), Math.max(p3.hall.getRed(), p4.hall.getRed())))
            color[0]=Color.RED;
        if(this.hall.getBlue()==Math.max(Math.max(this.hall.getBlue(), p2.hall.getBlue()), Math.max(p3.hall.getBlue(), p4.hall.getBlue())))
            color[1]=Color.BLUE;
        if(this.hall.getYellow()==Math.max(Math.max(this.hall.getYellow(), p2.hall.getYellow()), Math.max(p3.hall.getYellow(), p4.hall.getYellow())))
            color[2]=Color.YELLOW;
        if(this.hall.getGreen()==Math.max(Math.max(this.hall.getGreen(), p2.hall.getGreen()), Math.max(p3.hall.getGreen(), p4.hall.getGreen())))
            color[3]=Color.GREEN;
        if(this.hall.getRed()==Math.max(Math.max(this.hall.getPink(), p2.hall.getPink()), Math.max(p3.hall.getPink(), p4.hall.getPink())))
            color[4]=Color.PINK;
        return color;
    }

    public int getTower_count() {
        return tower_count;
    }

    public int getCoins() {
        return coins;
    }

    public void removeCoin(){
        if(this.coins>0) this.coins--;
    }
}
