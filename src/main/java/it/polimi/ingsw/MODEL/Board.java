package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private Cloud[] clouds;
    private LinkedList<IslandType> islands;



    public Board(int pcount){
        this.clouds=new Cloud[3];
        int flag=1;
        for(Cloud c:clouds){
            c=new Cloud(flag);
        }
        islands=new LinkedList<IslandType>();
        for(int i=0; i<12;i++){
            islands.add(new Island(i));
        }
        islands.get(11).               //sta roba non va. Va costruita una lista circolare ad hoc. Lo faccio dopo.
    }

    public Board(){
        this.clouds=new
    }
}
