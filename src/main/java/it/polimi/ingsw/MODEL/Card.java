package it.polimi.ingsw.MODEL;

public class Card {
    private int movement;
    private int value;
    public Player player;

    public Card(int movimento, int valore, Player player){
        this.movement = movimento;
        this.value = valore;
        this.player = player;
    }

    public int getValue() {
        return value;
    }

    public int getMovement() {
        return movement;
    }
}
