package it.polimi.ingsw.MODEL;

public class Carta {
    private int movimento;
    private int valore;
    public Player player;

    public Carta(int movimento, int valore, Player player){
        this.movimento = movimento;
        this.valore = valore;
        this.player = player;
    }

    public int getValore() {
        return valore;
    }

    public int getMovimento() {
        return movimento;
    }
}
