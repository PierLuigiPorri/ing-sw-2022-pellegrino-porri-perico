package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public abstract class IslandType implements StudentSpace{
    protected int id;
    protected int influence;
    protected Player player;
    protected ArrayList<Student> students;
    protected boolean motherNature;
    protected int islandCount=1;


    @Override
    public void addStudent(Color color){
        this.students.add(new Student(color));
    }

    public void removeStudent(Color color){
        int i=0;
        while(!this.students.get(i).getColor().equals(color))
            i++;
        this.students.remove(i);
    }

    public int getId() {
        return id;
    }

    public int getInfluence() {
        return influence;
    }

    public void setMotherNature(boolean presence) {
        this.motherNature = presence;
    }

    public int getIslandCount(){return islandCount;};
}