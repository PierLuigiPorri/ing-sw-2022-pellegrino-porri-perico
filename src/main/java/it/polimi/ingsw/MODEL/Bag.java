package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    private ArrayList<Student> students;
    final int MAX=130;
    private final Game game;

    public Bag(Game game) {
        this.game=game;
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(Color.RED));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(Color.BLUE));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(Color.YELLOW));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(Color.GREEN));
        }
        for (int i = 0; i < 24; i++) {
            this.students.add(new Student(Color.PINK));
        }
        Collections.shuffle(this.students);
    }

    public void addStudent(Color color) {
        //TODO:assert students.size()<MAX, non dovrebbe succedere ma non si sa mai
        this.students.add(new Student(color));
        Collections.shuffle(this.students);
    }

    public Student extractStudent(){
        //TODO:assert che !students.isNull()
        Student last=this.students.get(this.students.size()-1);
        this.students.remove(this.students.size()-1);
        return last;
    }


    public int getSize() {
        return this.students.size();
    }

    public Game getGame() {
        return game;
    }

}
