package it.polimi.ingsw.MODEL;

public class Cards {
    private int movimento;
    private int valore;
    public Player player;

    public Cards(int movimento, int valore, Player player){
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
