package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private Cloud[] clouds;
    private Islands islands;


    public Board(int pcount){
        this.clouds=new Cloud[3];
        int flag=1;
        for(Cloud c:clouds){
            c=new Cloud(flag);
        }
        islands=new Islands();
        for(int i=1; i<13;i++){
            islands.add(new Island(i));
        }
        islands.getIsland(1).setMotherNature(true);
        IslandType p=islands.head;
        do{
            if(p.getId()!=6){                       //non mettiamo niente sull'isola opposta a MN, come da regole.
                /*TODO: qui va generato uno studente random tra 10 pescati all'inizio.
                mi serve l'implementazione di bag per farlo, e non so come farlo da solo. -Doot*/
                p.addStudent(//qui va messo il colore dello studente generato.);
            }
        }while(p!= islands.tail);
    }

    public Board(){
        this.clouds=new Cloud[2];
        for(Cloud c:clouds){
            c=new Cloud();
        }
        islands=new Islands();
        for(int i=0; i<12;i++){
            islands.add(new Island(i));
        }
        islands.getIsland(1).setMotherNature(true);
        IslandType p=islands.head;
        do{
            if(p.getId()!=6){                       //non mettiamo niente sull'isola opposta a MN, come da regole.
                /*TODO: qui va generato uno studente random tra 10 pescati all'inizio.
                mi serve l'implementazione di bag per farlo, e non so come farlo da solo. -Doot*/
                p.addStudent(//qui va messo il colore dello studente generato.);
            }
        }while(p!= islands.tail);
    }
}
