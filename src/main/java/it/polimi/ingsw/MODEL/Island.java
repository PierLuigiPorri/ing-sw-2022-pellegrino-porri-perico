package it.polimi.ingsw.MODEL;

public class Island {
    private int id;
    private int influence;
    private Player player;
    private Student students;
    private boolean motherNature;

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

    public void setStudents(Student students) {
        this.students = students;
    }

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }
}
