package it.polimi.ingsw.MODEL;

public class Card {
    private int movement;
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
}
