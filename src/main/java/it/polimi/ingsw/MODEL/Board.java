package it.polimi.ingsw.MODEL;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
        Island p=islands.head;

        //Scelta random di uno studente per isola (tranne che in isola 6) fra 10 studenti(2R, 2G, 2B, 2Y, 2P)
        int x;
        Student[] stud, studen;
        Random rand;

        studen=new Student[10];
        int length= studen.length-1;
        stud=create();
        for (int i=0; i<10; i++){
            rand = new Random();
            x=rand.nextInt(length);
            studen[i] = stud[x];
            stud[x]=stud[length];
            length--;
        }
        int k=9;
        do{
            if(p.getId()!=6){
                p.students.add(studen[k]);
                k--;
            }
            p=p.next;
        }while(!p.equals(islands.tail));
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

        Island p=islands.head;
        int x;
        Student[] stud, studen;
        Random rand;

        studen=new Student[10];
        int length= studen.length-1;
        stud=create();
        for (int i=0; i<10; i++){
            rand = new Random();
            x=rand.nextInt(length);
            studen[i] = stud[x];
            stud[x]=stud[length];
            length--;
        }

        int k=9;
        do{
            if(p.getId()!=6){
                p.students.add(studen[k]);
                k--;
            }
            p=p.next;
        }while(!p.equals(islands.tail));
    }



    @NotNull
    private Student[] create(){
        int count =0;
        Student[] stud=new Student[10];
        for (int i = 0; i < 2; i++) {
            stud[count] = new Student(Color.RED);
            count++;
        }
        for (int i = 0; i < 2; i++) {
            stud[count] = new Student(Color.GREEN);
            count++;
        }
        for (int i = 0; i < 2; i++) {
            stud[count] = new Student(Color.YELLOW);
            count++;
        }
        for (int i = 0; i < 2; i++) {
            stud[count] = new Student(Color.BLUE);
            count++;
        }
        for (int i = 0; i < 2; i++) {
            stud[count] = new Student(Color.PINK);
            count++;
        }
        return stud;
    }
}


