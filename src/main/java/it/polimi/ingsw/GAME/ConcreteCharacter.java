package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Class that stores all information about a specific Concrete (a card that can contain students), or a card that can contain
 * Prohibition Tokens, that is in play. Stores cost, index and if it's been used already.
 * @author GC56
 */
public class ConcreteCharacter extends StudentSpace implements CharacterType, TDSpace{
    private int cost;
    private final int index;
    private boolean used=false;
    private int TD=4;
    private int MAX;
    private final Effects effects;

    /**
     * Constructor class. Sets index, cost, Effects instance to be used later, and calls the initializeConcrete method of Effects.
     * @param index Int parameter between 0-10.
     *@param cost Int parameter between 1-3.
     *@param effects Effects instance to be accessed when applying the effect.
     */
    public ConcreteCharacter(int index, int cost, Effects effects){
        this.effects=effects;
        this.cost=cost;
        this.index=index;
        effects.initializeConcrete(index, this);
    }


    /**
     * Overridden method form the CharacterType interface. Passes the right parameters to the Effects class, calling the applyAbstract method.
     */
    @Override
    public String applyEffect(Player player, ArrayList<Integer> par3, ArrayList<String> parA4, ArrayList<Integer> parC4) {
        effectUsed();
        return effects.applyConcrete(this.index, player, this, par3, parC4);
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public void effectUsed() {
        if(!this.used){
            this.cost++;
            this.used=true;
        }
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     * Overridden method from the CharacterType interface.
     */
    @Override
    public boolean getUsed() {
        return used;
    }

    /**
     * Overridden method from the StudentSpace class, which contains the actual ArrayList of Students.
     */
    @Override
    public void addStudent(String color) {
        this.students.add(new Student(color));
    }

    /**
     * Overridden method from the StudentSpace class, which contains the actual ArrayList of Students.
     */
    @Override
    public void removeStudent(int index) {
        this.students.remove(index);
    }

    /**
     * Overridden method from the StudentSpace class, which contains the actual ArrayList of Students.
     */
    public ArrayList<Student> getStudents(){
        return this.students;
    }

    /**
     * Sets the max value, which limits the size of the Students Arraylist.
     * @param val Int number, either 4 or 6.
     */
    public void setMAX(int val){
        this.MAX=val;
    }

    public int getMAX(){
        return this.MAX;
    }

    /**
     * Overridden method from the TDSpace interface.
     */
    @Override
    public void addTD(){
        if(this.index==4 && this.TD<4) this.TD++;
    }

    /**
     * Overridden method from the TDSpace interface.
     */
    @Override
    public void removeTD(){
        if(this.index==4&& this.TD>0) this.TD--;
    }


    public int getTD(){
        return this.TD;
    }
}
