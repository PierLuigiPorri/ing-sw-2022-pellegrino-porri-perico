package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * This class is the father of every class which can be considered a space in which you can both add and remove students.
 * @author GC56
 */
public abstract class StudentSpace{
    protected ArrayList<Student> students;

    /**
     * Add a student to the list students. It is used every time the player move a student to a StudentSpace.
     * requires (color!=null).
     * ensures (\old(this.students.size())==this.students.size()-1 ).
     * @param color the color of the student to be added to students.
     * @author GC56
     */
    public abstract void addStudent(String color);
    /**
     * Removes a student from the list students. It is used every time the player move a student from a StudentSpace.
     * requires (index>=0 && index<=students.size()-1).
     * ensures (\old(this.students.size())==this.students.size()+1 )
     * @param index the index of the student to be removed from students.
     * @author GC56
     */
    public abstract void removeStudent(int index);
}
