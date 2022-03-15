package it.polimi.ingsw.MODEL;

public class Island {
    public int id;
    public int influenza;
    public Player player;
    public Student studenti;
    public boolean madreNatura;

    public Island(int id) {
        this.id = id;
        this.player = null;
        this.influenza = 0;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public int getInfluenza() {
        return influenza;
    }

    public Player getPlayer() {
        return player;
    }
}
