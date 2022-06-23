package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Gate class represents the space in which the students the player can move are placed.
 * It contains the attribute students and the attribute MAX, which sets the maximum number of students that can be in the Gate.
 * It extends StudentSpace since is an object in which you can add or remove students.
 * @author GC56
 */
public class Gate extends StudentSpace {
    public int MAX;

    /**
     * Gate constructor. It constructs the list of students and sets the attribute MAX based on pcount.
     * @param pcount players number.
     * @requires (pcount==2 || pcount ==3)
     * @author GC56
     */
    public Gate(int pcount) {
        if (pcount == 2) {
            this.MAX = 7;
        } else {
            this.MAX = 9;
        }
        this.students = new ArrayList<>();
    }


    /**
     * @return the value of attribute MAX.
     */
    public int getMAX() {
        return MAX;
    }

    /**
     * @return the list students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public void addStudent(String color) {
        students.add(new Student(color));
    }

    @Override
    public void removeStudent(int index) {
        students.remove(index);
    }

    /**
     * This method is used to know which colors are in the Player's Gate.
     * It is mainly used to check if an action which requires a color is correct, comparing the color with the colors in Gate.
     * For example, to move a student from the Gate to the Hall, the color of the student the Player wants to move is required, so if getColorsInGate.contains(color) then the action can be performed.
     * @return the colors of the students in the Gate, in a list.
     * @author Pier Luigi Porri
     */
    public ArrayList<String> getColorsInGate() {
        ArrayList<String> tmp = new ArrayList<>();
        //saving every students' color.
        for (Student student : students) {
            tmp.add(student.getColor());
        }
        return tmp;
    }
}
