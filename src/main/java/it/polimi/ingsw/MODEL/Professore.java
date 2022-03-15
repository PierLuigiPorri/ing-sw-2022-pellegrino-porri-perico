package it.polimi.ingsw.MODEL;

public class Professore {
    public Colore colore;
    public Player player;

    public Professore(int i){
        if (i==0)
            colore=Colore.RED;
        if (i==1)
            colore=Colore.GREEN;
        if (i==2)
            colore=Colore.BLU;
        if (i==3)
            colore=Colore.PINK;
        if (i==1)
            colore=Colore.YELLOW;
        this.player=null;
    }
}
