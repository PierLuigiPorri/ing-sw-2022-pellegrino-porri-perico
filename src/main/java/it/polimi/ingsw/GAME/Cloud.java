package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Cloud extends StudentSpace{
    private final int MAX;

    public Cloud(int size) {
        MAX=size;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public void addStudent(ColorTracker color){
        //TODO:controllare a lato controller che student.size()!=MAX -Doot
        int i=0;
        while (students.get(i)!=null && i<=MAX-1)
            i++;
        students.add(new Student(color));
    }
    public void removeStudent(int index){
        students.remove(index);
    }

    public void emptyCloud(){
        students=null;
    }

}
