package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public class Gate extends StudentSpace{
    public Player player;
    public int MAX;
    public ArrayList<Student> students;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        this.students=new ArrayList<>();
        for(int i=0; i<9; i++){
            try {
                this.students.add(player.getGame().getBg().extractStudent());
            }catch (ImpossibleActionException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        this.students=new ArrayList<>();
        for(int i=0; i<7; i++) {
            try {
                this.students.add(player.getGame().getBg().extractStudent());
            }catch (ImpossibleActionException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void removeStudent(int index) {
        //TODO:ricordiamoci di fare il controllo che il colore da rimuovere ci sia effettivamente, al lato controller
        students.remove(index);
    }


    public Player getPlayer() {
        return player;
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
