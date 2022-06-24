package it.polimi.ingsw.GAME;

import java.util.ArrayList;

/**
 * Represents an Island composed by more than one Island. This class is instantiated after a merge between two Islands.
 * It has the same behavior of a simple Island, but in game's logic is different because of the number of towers it can contain.
 * @author GC56
 */
public class SuperIsland extends Island {

    /**
     * SuperIsland constructor. It constructs a normal Island, with a new list of towers and sets the number of island from which it is composed by the specified parameter.
     * For example, if two normal islands merge, than the number of islands that compose the new SuperIsland is 2; if a SuperIsland merge with an Island, than the number of Islands that compose the new SuperIsland is major than two.
     * It is called only when two islands merge.
     * @param count the number of islands from which the SuperIsland will be composed.
     * @author GC56
     */
    public SuperIsland(int count){
        super(0);
        this.islandCount=count;
        this.towers=new ArrayList<>();
    }
}
