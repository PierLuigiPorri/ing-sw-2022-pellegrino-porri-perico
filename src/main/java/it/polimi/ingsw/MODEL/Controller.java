package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

public class Controller {
    public Game game;

    public Controller(Game game){
        this.game=game;
    }

    public void Start() {
        try {
            game.start();
        }catch (GameException e){
            System.out.println(e.getMessage());
        }
    }

    public Player[] changePhase(){
        return game.changePhase();
    }

    public Player gameEnd(){
        return game.gameEnd();
    }

    public void bagToCloud(int index) {
        try {
            game.bagToCloud(index);
        } catch (BoundException c){
            System.out.println(c.getMessage());
        }
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) {
        try {
            game.gateToIsland(name, index, indexIsland, color);
        }catch (BoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
        try {
            game.CloudToGate(player, color, sIndex, cIndex);
        }catch (BoundException e){
            System.out.println(e.getMessage());
        }
    }

    ///

    public void moveMotherNature(int movement) {
        try {
            game.moveMotherNature(movement);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }



    public void mergeIslands(int index1, int index2) {
        try {
            game.mergeIslands(index1, index2);
        }catch (ConsecutiveIslandException e){
            System.out.println(e.getMessage());
        }
    }

    public void playCard(String player, int index){
        try{
            game.playCard(player, index);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }
}
