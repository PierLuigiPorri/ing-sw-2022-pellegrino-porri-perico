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


    public void setValue(int value) {
        this.value = value;
    }
}