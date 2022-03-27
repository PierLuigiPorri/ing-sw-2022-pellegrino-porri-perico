package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class Island implements StudentSpace{
    protected int id;
    protected Tower[] towers;
    protected ArrayList<Student> students;
    protected boolean motherNature;
    protected int islandCount=1;
    public Island next;


    public Island(int index){
        this.id=index;
        this.students=null;
        this.motherNature=false;
        this.next=null;
        this.towers=new Tower[islandCount];
    }

    @Override
    public void addStudent(Color color){

    }

    @Override
    public void removeStudent(Color color){
        int i=0;
        while(!this.students.get(i).getColor().equals(color))
            i++;
        this.students.remove(i);
    }

    public int getId() {
        return id;
    }

    public void setMotherNature(boolean presence) {
        this.motherNature = presence;
    }

    public int getIslandCount(){return islandCount;}

    public Player getPlayer(){
        return this.towers[0].getPlayer();
    }
}