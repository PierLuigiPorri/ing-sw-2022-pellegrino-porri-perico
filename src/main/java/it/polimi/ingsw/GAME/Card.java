package it.polimi.ingsw.GAME;

/**
 * This class represents the object Card, which is contained in each player's Hand class. Every player has, at most, 10 objects Card.
 * Card contains the value of the card itself (unique for each Card) and the maximum movement to give to MotherNature.
 * @author Pier Luigi Porri
 */
public class Card {
    private final int movement;
    private final int value;

    /**
     * Card constructor. It is called once a game for each card, for each player in the game (when the game is created).
     * Set the attributes movement and value of the card.
     * @param movement the card movement. (movement >= 1 && movement <= 5)
     * @param value the card value. (value >= 1 && value <= 10)
     * @author Pier Luigi Porri.
     */
    public Card(int movement, int value){
        this.movement = movement;
        this.value = value;
    }

    /**
     * @return the value attribute of the Card.
     * @author Pier Luigi Porri.
     */
    public int getValue() {
        return value;
    }


    /**
     * @return the movement attribute of the Card.
     * @author Pier Luigi Porri.
     */
    public int getMovement() {
        return movement;
    }


    /*public void setValue(int value) {
        this.value = value;
    }*/

}