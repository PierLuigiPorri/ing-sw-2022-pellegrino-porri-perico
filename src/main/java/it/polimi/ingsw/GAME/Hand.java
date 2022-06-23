package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * This class represents the hand of a player. It contains at most 10 Cards.
 * @author Pier Luigi Porri.
 */
public class Hand {
    private ArrayList<Card> cards;

    /**
     * Hand constructor. It is called once a game, at the beginning.
     * It builds all the ten cards owned by a player.
     * @author Pier Luigi Porri.
     */
    public Hand() {
        cards = new ArrayList<>();
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

    /**
     * @return the cards attribute.
     * @author Pier Luigi Porri.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}
