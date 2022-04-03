package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    private ArrayList<Student> students;
    final int MAX=130;
    private final Game game;

    public Bag(Game game) {
        this.game=game;
        this.students=new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(game.red));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(game.blue));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(game.yellow));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(game.green));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(game.pink));
        }
        Collections.shuffle(this.students);
    }

    public void addStudent(ColorTracker color) throws ImpossibleActionException {
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
