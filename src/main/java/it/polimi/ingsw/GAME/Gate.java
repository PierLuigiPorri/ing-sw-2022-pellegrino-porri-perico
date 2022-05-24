package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Gate extends StudentSpace{
    public int MAX;

    public Gate(int pcount) {
        if(pcount==2){
            this.MAX=7;
        }
        else {
            this.MAX = 9;
        }
        this.students=new ArrayList<>();
    }

    public void addInitialStud(Student student){
        this.students.add(student);
    }

    public int getMAX() {
        return MAX;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public void removeStudent(int index) {
        //TODO:ricordiamoci di fare il controllo che il colore da rimuovere ci sia effettivamente, al lato controller
        students.remove(index);
    }

    public ArrayList<String> getColorsInGate(){
        ArrayList<String> tmp= new ArrayList<>();
        for (Student student : students) {
            tmp.add(student.getColor());
        }
        return tmp;
    }

    @Override
    public void addStudent(String color) {
        //TODO:controllare a lato controller che student.size()!=MAX -Doot
        students.add(new Student(color));
    }
}
