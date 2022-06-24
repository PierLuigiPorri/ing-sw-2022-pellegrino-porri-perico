package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Cloud is an object in which a certain number (players number + 1) of students are placed on.
 * This class will have multiple instances and will be in common between all players.
 * @author GC56.
 */
public class Cloud extends StudentSpace{

    /**
     * Cloud constructor. It constructs the list of students.
     * It is called more than one time each game, depending on the number of players.
     * @author GC56
     */
    public Cloud() {
        students=new ArrayList<>();
    }

    /**
     * @return list of students which are in the cloud.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public void addStudent(String color) {
        students.add(new Student(color));
    }


    @Override
    public void removeStudent(int index){
        students.remove(index);
    }

    /**
     * Method mainly used for testing.
     * @return true if and only if this.students.isEmpty()
     */
    public boolean emptyCloud(){
        return this.students.isEmpty();
    }

}
