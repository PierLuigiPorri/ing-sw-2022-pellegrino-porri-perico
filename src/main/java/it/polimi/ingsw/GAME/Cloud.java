package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Cloud extends StudentSpace{
    private final int MAX;

    public Cloud(int size) {
        MAX=size;
        students=new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public void addStudent(String color) {
        //TODO:controllare a lato controller che student.size()!=MAX -Doot
        students.add(new Student(color));
    }


    @Override
    public void removeStudent(int index){
        students.remove(index);
    }

    public boolean emptyCloud(){
        return this.students.isEmpty();
    }

}
