package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Hand {
    public ArrayList<Card> cards;

    public Hand(){
        cards=new ArrayList<>();
        cards.add(new Card(1, 1));
        cards.add(new Card(1, 2));
        cards.add(new Card(2, 3));
        cards.add(new Card(2, 4));
        cards.add(new Card(3, 5));
        cards.add(new Card(3, 6));
        cards.add(new Card(4, 7));
        cards.add(new Card(4, 8));
        cards.add(new Card(5, 9));
        cards.add(new Card(5, 10));
        }
}
