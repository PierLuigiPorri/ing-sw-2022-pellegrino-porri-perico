package it.polimi.ingsw.GAME;

public class Card {
    private final int movement;
    private int value;

    public Card(int movement, int value){
        this.movement = movement;
        this.value = value;
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