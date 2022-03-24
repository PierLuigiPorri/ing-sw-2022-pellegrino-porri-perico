package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public class Island extends IslandType{
    private Tower tower;

    public Island(int index){
        this.id = id;
        this.player = null;
        this.influence = 0;
        this.students=new ArrayList<Student>();
    }

    public Player getPlayer(){
        return this.tower.getPlayer();
    }


}
