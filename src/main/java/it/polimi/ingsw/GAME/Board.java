package it.polimi.ingsw.GAME;


import java.util.ArrayList;

/**
 * This class represents the game's board, which is the same for all the players.
 * So it contains the islands and the clouds.
 * @author GC56
 */
public class Board {
    public final ArrayList<Cloud> clouds;
    public final CircularList islands;

    /**
     * Board constructor. It is called once a game, when the game is created.
     * This method, depending on how many players are playing, builds the islands, the clouds and set MotherNature on an Island.
     * @param pcount number of players in the game. (pcount == 2 || pcount == 3)
     * @author GC56
     */
    public Board(int pcount){

        //clouds building.
        clouds= new ArrayList<>();
        for(int i=0; i<pcount; i++){
            clouds.add(new Cloud());
        }

        //islands building.
        islands=new CircularList();
        for(int i=1; i<=12;i++){
            islands.add(new Island(i));
        }

        //setting MotherNature on the first island.
        //Mother Nature is always set, at the beginning, on island 1 because, since the island are represented in a circular way, every island can be considered as island 1.
        //It just depends on the point of view.

        islands.getIsland(1).setMotherNature(true);

        Island p=islands.getIsland(2);
        //Random choose of a student for each Island (not in Island 6) between 10 students (2 red, 2 green, 2 blue, 2 yellow, 2 pink).
        ArrayList<Student> firstSt;
        firstSt=Game.randomStudGenerator(10);

        //going on placing random students on islands. (not on island 6 because of game rules)
        while(!p.equals(islands.tail)) {
            if (p.getId() != 6) {
                p.getStudents().add(firstSt.remove(firstSt.size()-1));
            }
            p = p.next;
        }
        p.getStudents().add(firstSt.remove(firstSt.size()-1));
    }
}


