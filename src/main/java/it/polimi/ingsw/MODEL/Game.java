package it.polimi.ingsw.MODEL;

public class Game {
    public Player player1, player2, player3, player4;
    public Cloud cloud1, cloud2, cloud3, cloud4;
    public MotherNature motherNature;
    public Cards[] cards1, cards2, cards3, cards4;
    public Gate gate1, gate2, gate3, gate4;
    public Character[] characters;
    public Bag bag;
    public Hall hall1, hall2, hall3, hall4;
    public Student[] students;
    public Tower[] towers1, towers2, towers3;
    public Island[] islands;
    public Wizard Green, Red, Blue, Yellow, Pink;
    public int four_players;

    public Game(int player_count) {
        Green= new Wizard(Colors.GREEN);
        Red= new Wizard(Colors.RED);
        Blue= new Wizard(Colors.BLUE);
        Yellow= new Wizard(Colors.YELLOW);
        Pink= new Wizard(Colors.PINK);
        if(player_count==2){
            this.player1 = new Player();
            this.player2 = new Player();
            this.cloud1 = new Cloud();
            this.cloud2 = new Cloud();

            this.motherNature = new MotherNature();
            this.gate1 = new Gate(player1);
            this.gate2 = new Gate(player2);
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            this.hall1 = new Hall();
            this.hall2 = new Hall();
            /*for(int i=0; i<131; i++)
                this.students[i] = new Student();    ----sta roba conviene metterla direttamente in bag*/
            for(int i=0; i<13; i++)
                this.islands[i] = new Island(i);
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
            this.gate1 = new Gate(player1);
            this.gate2 = new Gate(player2);
            this.gate3 = new Gate(player3);
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            this.hall1 = new Hall();
            this.hall2 = new Hall();
            this.hall3 = new Hall();
            for(int i=0; i<13; i++)
                this.islands[i] = new Island(i);
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
            this.gate1 = new Gate(player1);
            this.gate2 = new Gate(player2);
            this.gate3 = new Gate(player3);
            this.gate4 = new Gate(player4);
            for(int i=0; i<4; i++)
                this.characters[i] = new Character();
            this.bag = new Bag();
            this.hall1 = new Hall();
            this.hall2 = new Hall();
            this.hall3 = new Hall();
            this.hall4 = new Hall();
            for(int i=0; i<13; i++)
                this.islands[i] = new Island(i);
        }
    }
    public static int setMotherNature(int index){
        islands[index].setMotherNature(true);
        return index;
    }
}