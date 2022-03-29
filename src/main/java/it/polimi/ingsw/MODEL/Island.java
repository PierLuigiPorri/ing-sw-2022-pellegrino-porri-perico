package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class Island implements StudentSpace, TDSpace{
    protected int id;
    protected Tower[] towers;
    protected ArrayList<Student> students;
    protected boolean motherNature;
    protected int islandCount=1;
    public Island next;
    protected int TD=0; //0:No tessera divieto; 1:Tessera divieto presente


    public Island(int index){
        this.id=index;
        this.students=null;
        this.motherNature=false;
        this.next=null;
        this.towers=new Tower[islandCount];
    }

    public int getStudent_Influence(Color[] colors){
        int count=0;
        for (Color c:colors) {
            for (Student s: students) {
                if(s.getColor().equals(c))
                    count=count+s.getInfluence();
            }
        }
        return count;
    }

    @Override
    public void addStudent(Color color){

    }

    public void removeStudent(Color color){
        int i=0;
        while(!this.students.get(i).getColor().equals(color))
            i++;
        this.students.remove(i);
    }

    @Override
    public void addTD() {
        TD++;
    }

    @Override
    public void removeTD() {
        TD--;
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