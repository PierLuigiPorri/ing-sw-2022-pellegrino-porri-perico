package it.polimi.ingsw.MODEL;

public class Game {
    public int playerCount;


    public void roundStart(){}

    public void roundEnd(){}

    public void gameEnd(){}

    public void addStudentToHall(String color, String player){}

    public void addStudentToGate(String color, String player){}

    public void addStudentToIsland(String color, int index){}

    public void addStudentToCloud(String color, int index){}

    public void removeStudentFromCloud(String color, int index){}

    public void removeStudentFromIsland(String color, int index){}

    public void removeStudentFromGate(String color, String player){}

    public void cloudToGate(int index, String player){}

    public void gateToIsland(String color, String player, int index){}

    public void gateToHall(String color, String player){}

    public void moveMotherNature(int movement){}

    public String determineInfluence(int index){}

    public void swapTowers(int index, String player){}

    public void mergeIslands(int index1, int index2){}

    public void playCard(String player, int index){}

    public void activateCharacter(String player, int id){}

    public void turnPass(String player){}

}
