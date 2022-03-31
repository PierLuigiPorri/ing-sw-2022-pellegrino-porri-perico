package it.polimi.ingsw.MODEL;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board {
    public ArrayList<Cloud> clouds;
    public final Circularlist islands;


    public Board(int pcount){
        clouds= new ArrayList<>();
        for(int i=0; i<3; i++){
            clouds.add(new Cloud(4));
        }
        islands=new Circularlist();
        for(int i=1; i<13;i++){
            islands.add(new Island(i));
        }
        islands.getIsland(1).setMotherNature(true);
        Island p=islands.getIsland(2);

        //Scelta random di uno studente per isola (tranne che in isola 6) fra 10 studenti(2R, 2G, 2B, 2Y, 2P)
        ArrayList<Student> firstSt=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.RED));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.BLUE));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.PINK));
        }
        Collections.shuffle(firstSt);
        while(!p.equals(islands.tail)) {
            if (p.getId() != 6) {
                p.students.add(firstSt.get(firstSt.size()-1));
                firstSt.remove(firstSt.size()-1);
            }
            p = p.next;
        }
    }

    public Board(){
        clouds=new ArrayList<>();
        for(int i=0; i<2; i++){
            clouds.add(new Cloud(3));
        }
        islands=new Circularlist();
        for(int i=0; i<12;i++){
            islands.add(new Island(i));
        }
        islands.getIsland(1).setMotherNature(true);

        Island p=islands.getIsland(2);

        //Scelta random di uno studente per isola (tranne che in isola 6) fra 10 studenti(2R, 2G, 2B, 2Y, 2P)
        ArrayList<Student> firstSt=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.RED));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.BLUE));
        }
        for (int i = 0; i < 2; i++) {
            firstSt.add(new Student(Color.PINK));
        }
        Collections.shuffle(firstSt);
        while(!p.equals(islands.tail)) {
            if (p.getId() != 6) {
                p.students.add(firstSt.get(firstSt.size()-1));
                firstSt.remove(firstSt.size()-1);
            }
            p = p.next;
        }
    }


}


