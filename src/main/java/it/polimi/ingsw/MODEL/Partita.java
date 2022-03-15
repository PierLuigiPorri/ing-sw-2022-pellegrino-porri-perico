package it.polimi.ingsw.MODEL;

public class Partita {
    public Player player1, player2, player3, player4;
    public Nuvola nuvola1, nuvola2;
    public MadreNatura madreNatura;
    public Carta[] carta1, carta2, carta3, carta4;
    public Ingresso ingresso1, ingresso2, ingresso3, ingresso4;
    public Personaggio[] personaggi;
    public Professore[] professori;
    public Sacchetto sacchetto;
    public Sala sala1, sala2, sala3, sala4;
    public Studente[] studenti;
    public Torre torre1, torre2, torre3;
    public Isola[] isole;

    public Partita(Player player1, Player player2, Player player3, Player player4) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.nuvola1 = new Nuvola();
        this.nuvola2 = new Nuvola();

        this.madreNatura = new MadreNatura();
        this.ingresso1 = new Ingresso(player1);
        this.ingresso2 = new Ingresso(player2);
        this.ingresso3 = new Ingresso(player3);
        for(int i=0; i<4; i++)
            this.personaggi[i] = new Personaggio();
        for(int i=0; i<5; i++)
            this.professori[i] = new Professore(i);
        this.sacchetto = new Sacchetto();
        this.sala1 = new Sala();
        this.sala2 = new Sala();
        this.sala3 = new Sala();
        for(int i=0; i<131; i++)
            this.studenti[i] = new Studente();
        this.torre1 = new Torre();
        this.torre2 = new Torre();
        this.torre3 = new Torre();
        for(int i=0; i<13; i++)
            this.isole[i] = new Isola(i);
    }
}
