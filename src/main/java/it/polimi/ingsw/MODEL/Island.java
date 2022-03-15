package it.polimi.ingsw.MODEL;

public class Island {
    private int id;
    private int influence;
    public Player player;
    public Student students;
    public boolean motherNature;

    public Island(int id) {
        this.id = id;
        this.player = null;
        this.influence = 0;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public int getInfluenza() {
        return influence;
    }

    public Player getPlayer() {
        return player;
    }
}
