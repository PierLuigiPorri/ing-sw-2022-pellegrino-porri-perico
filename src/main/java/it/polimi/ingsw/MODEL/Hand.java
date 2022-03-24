package it.polimi.ingsw.MODEL;

public class Hand {
    Player player;
    Card[] cards;


    public Hand(Player player){
        this.player=player;
        cards=new Card[10];
        for(int i=0; i<10; i++){
            //qui bisogna manualmente mettere le carte nel vettore. Se sono in ordine per movimento o valore, si può fare
            //con un ciclo for. Altrimenti va fatto una a una a mano.
        }
    }
}
