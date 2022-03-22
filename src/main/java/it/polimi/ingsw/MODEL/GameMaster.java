package it.polimi.ingsw.MODEL;

public class GameMaster {
    public Player player1, player2, player3, player4;
    public Cloud cloud1, cloud2, cloud3, cloud4;
    public MotherNature motherNature;
    public Card[] card1, card2, card3, card4;
    public Gate gate1, gate2, gate3, gate4;
    public Character[] characters;
    public Bag bag;
    public Hall hall1, hall2, hall3, hall4;
    public Student[] students;
    public Tower[] towers1, towers2, towers3;
    public static IslandType[] islandTypes;
    public ColorTracker Green, Red, Blue, Yellow, Pink;
    public int four_players;

    public GameMaster(int player_count) {
        Green= new ColorTracker(Color.GREEN);
        Red= new ColorTracker(Color.RED);
        Blue= new ColorTracker(Color.BLUE);
        Yellow= new ColorTracker(Color.YELLOW);
        Pink= new ColorTracker(Color.PINK);
        if(player_count==2){
            this.player1 = new Player();
            this.player2 = new Player();
            this.cloud1 = new Cloud();
            this.cloud2 = new Cloud();

            this.motherNature = new MotherNature();
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            /*for(int i=0; i<131; i++)
                this.students[i] = new Student();    ----sta roba conviene metterla direttamente in bag*/
            for(int i=0; i<13; i++)
                this.islandTypes[i] = new IslandType(i);
        }
        else if(player_count==3){
            this.player1 = player1;
            this.player2 = player2;
            this.player3 = player3;
            int pflag=1;
            this.cloud1 = new Cloud(pflag);
            this.cloud2 = new Cloud(pflag);
            this.cloud3 = new Cloud(pflag);

            this.motherNature = new MotherNature();
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            for(int i=0; i<13; i++)
                this.islandTypes[i] = new IslandType(i);
        }
        else{
            this.four_players=1;
            this.player1 = player1;
            this.player2 = player2;
            this.player3 = player3;
            this.player4 = player4;
            this.cloud1 = new Cloud();
            this.cloud2 = new Cloud();
            this.cloud3 = new Cloud();
            this.cloud4 = new Cloud();

            this.motherNature = new MotherNature();
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            for(int i=0; i<13; i++)
                this.islandTypes[i] = new IslandType(i);
        }
    }
    public static int setMotherNature(int index){
        islandTypes[index].setMotherNature(true);
        return index;
    }
}