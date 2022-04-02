package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class Island extends StudentSpace implements TDSpace{
    protected int id;
    protected ArrayList<Tower> towers;
    protected boolean motherNature;
    protected int islandCount=1;
    public Island next;
    protected boolean TD=false; //0:No tessera divieto; 1:Tessera divieto presente

    public Island(int index){
        this.id=index;
        this.motherNature=false;
        this.next=null;
        this.towers=new ArrayList<>();
    }


    @Override
    public void addStudent(ColorTracker color){
        this.students.add(new Student(color));
    }

    @Override
    public void removeStudent(int index){
       this.students.remove(index);
    }

    @Override
    public void addTD() {
        TD=true;
    }

    @Override
    public void removeTD() {
        TD=false;
    }

    public int getId() {
        return id;
    }

    public void setMotherNature(boolean presence) {
        this.motherNature = presence;
    }

    public int getIslandCount(){return islandCount;}

    public Player getPlayer(){
        return this.towers.get(0).getPlayer();
    }

    public void addTower(Player p){
        if(this.towers.isEmpty()){
            this.towers.add(new Tower(p));
        }
    }


}