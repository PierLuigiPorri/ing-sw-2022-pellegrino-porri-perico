package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    private ArrayList<Student> students;
    final int MAX=130;

    public Bag() {
        this.students=new ArrayList<>();
        this.students=Game.randomStudGenerator(120);
    }

    public void addStudent(String color) throws ImpossibleActionException {
        if(this.students.size()<MAX) {
            //TODO:assert students.size()<MAX, non dovrebbe succedere ma non si sa mai
            this.students.add(new Student(color));
            //Collections.shuffle(this.students);
        }else throw new ImpossibleActionException("The Bag is full, impossible to add students");
    }

    public Student extractStudent() throws ImpossibleActionException {
        //TODO:assert che !students.isNull()
        if(!this.students.isEmpty()) {
            Student last = this.students.get(this.students.size() - 1);
            this.students.remove(this.students.size() - 1);
            return last;
        }else throw new ImpossibleActionException("The bag is empty!");
    }


    public int getSize() {
        return this.students.size();
    }

}
