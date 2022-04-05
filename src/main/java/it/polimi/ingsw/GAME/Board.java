package it.polimi.ingsw.GAME;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    public ArrayList<Cloud> clouds;
    public final Circularlist islands;
    private Game game;


    public Board(int pcount, Game game){

        this.game=game;
        clouds= new ArrayList<>();
        for(int i=0; i<=pcount; i++){
            clouds.add(new Cloud(pcount+1));
        }
        islands=new Circularlist();
        for(int i=1; i<=12;i++){
            islands.add(new Island(i));
        }
        islands.getIsland(1).setMotherNature(true);
        Island p=islands.getIsland(2);

        //Scelta random di uno studente per isola (tranne che in isola 6) fra 10 studenti(2R, 2G, 2B, 2Y, 2P)
        ArrayList<Student> firstSt=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(game.red));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(game.green));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(game.yellow));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(game.blue));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(game.pink));
        }
        Collections.shuffle(firstSt);
        while(!p.equals(islands.tail)) {
            if (p.getId() != 6) {
                p.getStudents().add(firstSt.get(firstSt.size()-1));
                firstSt.remove(firstSt.size()-1);
            }
            p = p.next;
        }
    }
}


