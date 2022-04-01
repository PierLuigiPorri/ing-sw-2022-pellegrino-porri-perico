package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.AdditionException;

public class Controller {
    public Game game;

    public Controller(Game game){
        this.game=game;
    }

    public void Start(){
        game.start();
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
        } catch (AdditionException c){
            System.out.println(c.getMessage());
        }
    }

    public void gateToIsland(String name, int index, int indexIsland, String color){
        game.gateToIsland(name, index, indexIsland, color);
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex){ //TODO
        game.CloudToGate(player, color, sIndex, cIndex);
        //TODO: assert p.getGate().students.size()<p.getGate().MAX-2, ma con un try/catch
    }

    ///

    public void moveMotherNature(int movement){
        game.moveMotherNature(movement);
    }

    public int determineInfluence(String player, int index){
        return game.determineInfluence(player, index);
    }

    public void swapTowers(int index, String playerTO){
        game.swapTowers(index, playerTO);
    }

    public void mergeIslands(int index1, int index2){
        game.mergeIslands(index1, index2);
    }

    public void playCard(String player, int index){
        game.playCard(player, index);
    }
}
