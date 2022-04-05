package it.polimi.ingsw.GAME;

public class Card {
    private final int movement;
    private int value;

    public Card(int movimento, int valore){
        this.movement = movimento;
        this.value = valore;
    }

    public int getValue() {
        return value;
    }

    public int getMovement() {
        return movement;
    }

    public Card compareTo(Card c1){
        if(this.value< c1.value)
            return this;
        else return c1;
    }

    public void setValue(int value) {
        this.value = value;
    }
}