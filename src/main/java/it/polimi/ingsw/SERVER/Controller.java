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

public class Controller implements Observer{
    private final Game game;
    private final ArrayList<ConnectionManager> connectionManagers; //Potranno essere usati per rispondere alle loro stesse richieste
    public Controller(Game game, ConnectionManager cm1, ConnectionManager cm2, ConnectionManager cm3){
        this.game=game;
        connectionManagers=new ArrayList<>();
        connectionManagers.add(cm1); //Index 0
        connectionManagers.add(cm2); //Index 1
        if(game.getPlayerCount()==3){
            connectionManagers.add(cm3); //Index 2
        }
    }

    public void gateToIsland(String name, int index, int indexIsland) {
            try {
                game.gateToIsland(name, index, indexIsland);
            } catch (BoundException | ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

    public void gateToHall(String name, String color) {
            try {
                game.gateToHall(name, color);
            } catch (ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
            try {
                game.CloudToGate(player, color, sIndex, cIndex);
            } catch (BoundException | ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(player)).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

    public void moveMotherNature(String name, int movement) {
            try {
                game.moveMotherNature(name, movement);
            } catch (ImpossibleActionException | ConsecutiveIslandException | BoundException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

    public void playCard(String player, int index){
            try {
                game.playCard(player, index);
            } catch (ImpossibleActionException | BoundException e) {
                Objects.requireNonNull(getCorrectCm(player)).send(new ResponseMessage(e.getMessage(),false, false));
                (Objects.requireNonNull(getCorrectCm(player))).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

    public void activateCharacter(String name, ArrayList<String> a, ArrayList<Integer> b, ArrayList<Integer> c){
            try {
                String pl=a.get(0);
                a.remove(0);
                String parA2=null;
                if(!a.isEmpty()){
                    parA2=a.get(0);
                    a.remove(0);
                }
                int id=b.get(0);
                b.remove(0);
                int parAC1= 0;
                if(!b.isEmpty()){
                    parAC1=b.get(0);
                    b.remove(0);
                }
                int parC2=0;
                if(!b.isEmpty()){
                    parC2=b.get(0);
                    b.remove(0);
                }
                game.activateCharacter(pl, id, parAC1, parA2, b, a, parC2, c);
            } catch (ImpossibleActionException e) {
                Objects.requireNonNull(getCorrectCm(name)).send(new ResponseMessage(e.getMessage(),false, false));
            }
    }

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
                CloudToGate(am.strParam.get(0), am.strParam.get(1), am.intParam.get(0), am.intParam.get(1));
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
