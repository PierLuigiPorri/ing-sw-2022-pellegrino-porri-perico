package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * This class manages the exceptions found by the Game class (our Model+Controller in MVC), sending Response messages when necessary
 * @author GC56
 */
public class Controller implements Observer{
    private final Game game;
    private final ArrayList<ConnectionManager> connectionManagers; //Can be used to answer to itself requests

    /**
     * Constructor.
     * @param game The game
     * @param cm1 Connection Manager of player 1
     * @param cm2 Connection Manager of player 2
     * @param cm3 Connection Manager of player 3. null if it's a 2-player game
     * @requires game!=null && cm1!=null && cm2!=null
     */
    public Controller(Game game, ConnectionManager cm1, ConnectionManager cm2, ConnectionManager cm3){
        this.game=game;
        connectionManagers=new ArrayList<>();
        connectionManagers.add(cm1); //Index 0
        connectionManagers.add(cm2); //Index 1
        if(game.getPlayerCount()==3){
            connectionManagers.add(cm3); //Index 2
        }
    }

    /**
     * It calls the class Game to perform the action of moving a student from the Gate to an Island.
     * It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param name the nickname of the player who wants to move a student.
     * @param index the index of the student in the gate
     * @param indexIsland the index of the island in which the students could be moved.
     */
    public void gateToIsland(String name, int index, int indexIsland) {
            try {
                game.gateToIsland(name, index, indexIsland);
            } catch (BoundException | ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * It calls the class Game to perform the action of moving a student from the Gate to the player's Hall.
     * It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param name the nickname of the player who wants to move a student.
     * @param color the color of the student to be moved to player's hall.
     */
    public void gateToHall(String name, String color) {
            try {
                game.gateToHall(name, color);
            } catch (ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * It calls the class Game to perform the action of moving students from a cloud to player's Gate.
     *  It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param player the nickname of the player who wants to take students.
     * @param cIndex the index of the cloud from which the students are taken.
     */
    public void CloudToGate(String player, int cIndex) {
            try {
                game.CloudToGate(player, cIndex);
            } catch (BoundException | ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(player)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * It calls the class Game to perform the action of moving MotherNature from an Island to another one.
     *  It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param name the nickname of the player who wants to move a MotherNature.
     * @param movement the movement given to MotherNature.
     */
    public void moveMotherNature(String name, int movement) {
            try {
                game.moveMotherNature(name, movement);
            } catch (ImpossibleActionException | ConsecutiveIslandException | BoundException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * It calls the class Game to perform the action of playing a card from the player's Hand.
     *  It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param player the nickname of the player who wants to play a card.
     * @param index the index of the card in player's hand.
     */
    public void playCard(String player, int index){
            try {
                game.playCard(player, index);
            } catch (ImpossibleActionException | BoundException e) {
                Objects.requireNonNull(getCorrectCm(player)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * It calls the class Game to perform the action of activating a character card.
     * It checks if everything is correct: if is not sends a Response message to the client who called this method. Otherwise, it calls the Game class to perform it.
     * @param name the nickname of the player who wants to activate a character.
     * @param a The first ArrayList of integer parameters needed for the effects.
     * @param b The Arraylist of String parameters needed for the effects.
     * @param c The second Arraylist of integer parameters, needed for some effects.
     */
    public void activateCharacter(String name, ArrayList<String> a, ArrayList<Integer> b, ArrayList<Integer> c){
            try {
                String pl=a.remove(0);
                int id=b.remove(0);
                game.activateCharacter(pl, id, b, a, c);
            } catch (ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, null, false));
            }
    }

    /**
     * Returns the connection manager of a specified player with nickname as specified
     * @param name the nickname of the player.
     * @return the connection manager of the player.
     */
    private ConnectionManager getCorrectCm(String name){
        for (ConnectionManager cm:
             connectionManagers) {
            if(cm.getPlayerName().equals(name)){
                return cm;
            }
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        ActionMessage am=(ActionMessage) arg;
        switch (am.ActionType){
            case 0://gateToIsland
                gateToIsland(am.strParam.get(0), am.intParam.get(0), am.intParam.get(1));
                break;
            case 1://gateToHall
                gateToHall(am.strParam.get(0), am.strParam.get(1));
                break;
            case 2://CloudToGate
                CloudToGate(am.strParam.get(0), am.intParam.get(0));
                break;
            case 3://moveMotherNature
                moveMotherNature(am.strParam.get(0), am.intParam.get(0));
                break;
            case 4://playCard
                playCard(am.strParam.get(0), am.intParam.get(0));
                break;
            case 5://activateCharacter
                activateCharacter(am.strParam.get(0), am.strParam, am.intParam, am.intParam2);
                break;
        }
    }
}
