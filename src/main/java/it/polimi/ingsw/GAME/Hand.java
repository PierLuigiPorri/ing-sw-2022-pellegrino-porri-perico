package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Hand {
    public ArrayList<Card> cards; //TODO: Questa roba dev'essere un'Array List

//TODO: impostare il corretto valore di MOVIMENTO alle varie carte.
    public Hand(){
        cards=new ArrayList<>();
        cards.add(new Card(0,0)); //CARD[0] serve in classe ROUND.
        cards.add(new Card(1, 1));
        cards.add(new Card(1, 2));
        cards.add(new Card(1, 3));
        cards.add(new Card(1, 4));
        cards.add(new Card(1, 5));
        cards.add(new Card(1, 6));
        cards.add(new Card(1, 7));
        cards.add(new Card(1, 8));
        cards.add(new Card(1, 9));
        cards.add(new Card(1, 10));
        }
}
