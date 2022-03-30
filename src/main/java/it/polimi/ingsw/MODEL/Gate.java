package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class Gate extends StudentSpace{
    public Player player;
    int MAX;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        for(int i=0; i<9; i++){
            this.students.add(player.getGame().getBg().extractStudent());
        }
    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        for(int i=0; i<7; i++){
            this.students.add(player.getGame().getBg().extractStudent());
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
    public void addStudent(Color color) {
        //TODO:controllare a lato controller che student.size()!=MAX -Doot
        int i=0;
        while (students.get(i)!=null && i<=MAX-1)
            i++;
        students.add(new Student(color));
    }
}
