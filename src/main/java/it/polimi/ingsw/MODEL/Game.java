package it.polimi.ingsw.MODEL;

public class Game {
    public Player player1, player2, player3, player4;
    public Cloud cloud1, cloud2;
    public MotherNature motherNature;
    public Cards[] cards1, cards2, cards3, cards4;
    public Gate gate1, gate2, gate3, gate4;
    public Character[] characters;
    public Professore[] wizards;
    public Bag bag;
    public Hall hall1, hall2, hall3, hall4;
    public Student[] students;
    public Tower[] towers1, towers2, towers3;
    public Island[] islands;
    public Color Green, Red, Blue, Yellow, Pink;

    public Game(int player_count) {
        Green= new Color(GREEN);
        Red= new Color(RED);
        Blue= new Color(BLUE);
        Yellow= new Color(YELLOW);
        Pink= new Color(PINK);
        if(player_count==2){
            this.player1 = new Player();
            this.player2 = new Player();
            this.cloud1 = new Cloud();
            this.cloud2 = new Cloud();

            this.motherNature = new MotherNature();
            this.ingresso1 = new Gate(player1);
            this.ingresso2 = new Gate(player2);
            this.ingresso3 = new Gate(player3);
            for(int i=0; i<4; i++)
                this.personaggi[i] = new Character();
            for(int i=0; i<5; i++)
                this.professori[i] = new Professore(i);
            this.sacchetto = new Bag();
            this.sala1 = new Hall();
            this.sala2 = new Hall();
            this.sala3 = new Hall();
            for(int i=0; i<131; i++)
                this.studenti[i] = new Student();
            this.torre1 = new Tower();
            this.torre2 = new Tower();
            this.torre3 = new Tower();
            for(int i=0; i<13; i++)
                this.isole[i] = new Island(i);
        }
        else if(player_count==3){
            this.player1 = player1;
            this.player2 = player2;
            this.player3 = player3;
            this.player4 = player4;
            int flag=1;
            this.cloud1 = new Cloud(flag);
            this.cloud2 = new Cloud(flag);

            this.madreNatura = new MotherNature();
            this.ingresso1 = new Gate(player1);
            this.ingresso2 = new Gate(player2);
            this.ingresso3 = new Gate(player3);
            for(int i=0; i<4; i++)
                this.personaggi[i] = new Character();
            for(int i=0; i<5; i++)
                this.professori[i] = new Professore(i);
            this.sacchetto = new Bag();
            this.sala1 = new Hall();
            this.sala2 = new Hall();
            this.sala3 = new Hall();
            for(int i=0; i<131; i++)
                this.studenti[i] = new Student();
            this.torre1 = new Tower();
            this.torre2 = new Tower();
            this.torre3 = new Tower();
            for(int i=0; i<13; i++)
                this.isole[i] = new Island(i);
        }
        else{
            this.player1 = player1;
            this.player2 = player2;
            this.player3 = player3;
            this.player4 = player4;
            this.cloud1 = new Cloud();
            this.cloud2 = new Cloud();

            this.madreNatura = new MotherNature();
            this.ingresso1 = new Gate(player1);
            this.ingresso2 = new Gate(player2);
            this.ingresso3 = new Gate(player3);
            for(int i=0; i<4; i++)
                this.personaggi[i] = new Character();
            for(int i=0; i<5; i++)
                this.professori[i] = new Professore(i);
            this.sacchetto = new Bag();
            this.sala1 = new Hall();
            this.sala2 = new Hall();
            this.sala3 = new Hall();
            for(int i=0; i<131; i++)
                this.studenti[i] = new Student();
            this.torre1 = new Tower();
            this.torre2 = new Tower();
            this.torre3 = new Tower();
            for(int i=0; i<13; i++)
                this.isole[i] = new Island(i);
        }
    }
}

    public Game(Player player1, Player player2, Player player 3){

    }
