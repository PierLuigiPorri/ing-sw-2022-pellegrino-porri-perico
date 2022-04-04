package it.polimi.ingsw.MODEL;

public class Hand {
    Player player;
    Card[] cards; //TODO: Questa roba dev'essere un'Array List

//TODO: impostare il corretto valore di MOVIMENTO alle varie carte.
    public Hand(Player player){
        this.player=player;
        cards=new Card[11];
        cards[0]=new Card(0,0); //CARD[0] serve in classe ROUND.
        cards[1]=new Card(1, 1);
        cards[2]=new Card(1, 2);
        cards[3]=new Card(1, 3);
        cards[4]=new Card(1, 4);
        cards[5]=new Card(1, 5);
        cards[6]=new Card(1, 6);
        cards[7]=new Card(1, 7);
        cards[8]=new Card(1, 8);
        cards[9]=new Card(1, 9);
        cards[10]=new Card(1, 10);
        }
}
