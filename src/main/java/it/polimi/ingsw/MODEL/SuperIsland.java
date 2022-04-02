package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class SuperIsland extends Island {

    public SuperIsland(int count){
        super(0);
        this.islandCount=count;
        this.towers=new ArrayList<>();
    }
}
