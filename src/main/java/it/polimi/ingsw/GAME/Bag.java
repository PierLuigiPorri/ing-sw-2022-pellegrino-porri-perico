package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Student> students;
    final int MAX=130;

    public Bag() {
        this.students=new ArrayList<>();
        this.students=Game.randomStudGenerator(120);
    }

    public void addStudent(String color) throws ImpossibleActionException {
        if(this.students.size()<MAX) {
            this.students.add(new Student(color));
        }else throw new ImpossibleActionException("The Bag is full, impossible to add students");
    }

    public Student extractStudent() {
        Student last = this.students.get(this.students.size() - 1);
        this.students.remove(this.students.size() - 1);
        return last;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getMAX() {
        return MAX;
    }

    public int getSize() {
        return this.students.size();
    }

}
