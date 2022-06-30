package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Represents the space in which MotherNature can move and the one in which can be added students and towers.
 * As an island, it contains its id, the list of towers and of students on it, the information about the presence of MotherNature and prohibition card, its following island.
 * It extends StudentSpace since is an object in which you can add and remove students.
 * It implements TDSpace since prohibition card can be placed on islands.
 * @author GC56
 */
public class Island extends StudentSpace implements TDSpace{
    protected int id;
    protected ArrayList<Tower> towers;
    protected boolean motherNature;
    protected int islandCount=1;
    public Island next;
    private final ArrayList<Student> students;
    public boolean TD=false; //0: No prohibition card; 1:yes prohibition card


    /**
     * Island constructor. it constructs the list of students and towers, set the id to the specified one with parameter index.
     * As the model stands at the moment, the index has to be between 1 and 12 (extremity included).
     * @param index the id of the island to be created.
     * @author GC56
     */
    public Island(int index){
        this.id=index;
        this.motherNature=false;
        this.next=null;
        this.towers=new ArrayList<>();
        this.students=new ArrayList<>();
    }


    @Override
    public void addStudent(String color){
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

    /**
     * Returns the id of the island.
     * @return the id of the island.
     */
    public int getId() {
        return id;
    }

    /**
     * Set to presence the attribute MotherNature. It is called whenever MotherNature move from an island to another.
     * So it is called, for example, to set to false the presence of MotherNature on the island from which She moves, and set to true in the island in which She gets.
     * @param presence value of the attribute MotherNature to be set.
     */
    public void setMotherNature(boolean presence) {
        this.motherNature = presence;
    }

    /**
     * Says the number of island that compose this one.
     * @return the number of island that compose this.
     */
    public int getIslandCount(){return islandCount;}

    /**
     * Says the player who owns the towers on this. There is always at most one player who owns the tower on an island.
     * @return the player who owns the towers on this or null, if there are no towers.
     */
    public Player getPlayer() {
        if (this.towers.isEmpty())
            return null;
        else
            return this.towers.get(0).getPlayer();
    }

    /**
     * add to the list of towers, a tower of the specified player. There can be at most islandCount towers on an island.
     * requires p!=null.
     * ensures (\old(towers.size())==towers.size()-1 && \forAll (int i; i>=0 && i<this.towers.size(); this.get(i).getPlayer.equals(p))
     * @param p the Player who owns the tower to be added to the Island.
     * @author GC56
     */
    public void addTower(Player p){
        if(this.towers.isEmpty()){
            this.towers.add(new Tower(p));
        }
    }

    /**
     * returns the students on this.
     * @return the list of students on this.
     */
    public ArrayList<Student> getStudents(){
        return students;
    }

    /**
     * returns the towers on this.
     * @return the list of towers on this.
     */
    public ArrayList<Tower> getTowers() {
        return towers;
    }
}