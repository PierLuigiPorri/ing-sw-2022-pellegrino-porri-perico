package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class SuperIsland extends Island {

    public SuperIsland(int count){
        super(0);
        this.islandCount=count;
        this.students=new ArrayList<Student>();
        this.towers=new Tower[islandCount];
    }
}
