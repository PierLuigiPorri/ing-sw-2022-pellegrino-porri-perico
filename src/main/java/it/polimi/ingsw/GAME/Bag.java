package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.BagEmptyException;

import java.util.ArrayList;

/**
 * This class represents the game's bag, so it contains 130 students.
 * From this class the students are taken to be putted in the players' gates, on the clouds and, at the beginning, on the islands.
 * @author GC56
 */
public class Bag {
    private ArrayList<Student> students;


    /**
     *  Bag constructor. Initialize the students inside the bag, they will be in a random order.
     *  It is called every time a new game is created.
     * @author GC56
     */
    public Bag() {
        this.students=new ArrayList<>();
        this.students=Game.randomStudGenerator(120);
    }


    /**
     * This method is called every time is needed to pick up a student from the Bag and place it somewhere else.
     * @return the student extracted from the bag  [ \old (bag.students.size()) == bag.students.size() + 1]
     * @throws BagEmptyException if there are no more students in the bag. (students.size()==0)
     * @author GC56
     */
    public Student extractStudent() throws BagEmptyException {
        if(students.size()>0) {
            Student last = this.students.get(this.students.size() - 1);
            this.students.remove(this.students.size() - 1);
            return last;
        }
        else throw new BagEmptyException("Bag empty");
    }

    /*final int MAX=130;

    public int getMAX() {
        return MAX;
    }

    public void addStudent(String color) throws ImpossibleActionException {
        if(this.students.size()<MAX) {
            this.students.add(new Student(color));
        }else throw new ImpossibleActionException("The Bag is full, impossible to add students");
    }*/
}
