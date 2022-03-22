package it.polimi.ingsw.MODEL;

public class IslandType implements StudentSpace{
    private int id;
    private int influence;
    private Player player;
    private Student[] students;
    private boolean motherNature;

    public void IslandType(int id) {
        this.id = id;
        this.player = null;
        this.influence = 0;
        this.students=new Student[1];
    }

    @Override
    public void addStudent(Color color){

    }

    public void setInfluence(int influence) {
        this.influence = influence;
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

    public void placeStudents(Student students) {
        this.students=new Student[this.students.length+1];
        this.students[this.students.length-1]=students;
    }

    public void setMotherNature(boolean presence) {
        this.motherNature = presence;
    }
}