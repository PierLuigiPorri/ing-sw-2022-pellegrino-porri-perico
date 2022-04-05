package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public class Gate extends StudentSpace{
    //public Player player;
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

    public void removeStudent(int index) {
        //TODO:ricordiamoci di fare il controllo che il colore da rimuovere ci sia effettivamente, al lato controller
        students.remove(index);
    }

    @Override
    public void addStudent(ColorTracker color) {
        //TODO:controllare a lato controller che student.size()!=MAX -Doot
        int i=0;
        while (students.get(i)!=null && i<=MAX-1)
            i++;
        students.add(new Student(color));
    }
}
