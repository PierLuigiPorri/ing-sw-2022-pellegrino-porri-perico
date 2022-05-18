package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.MESSAGES.ActionMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer{
    private final Game game;
    private ArrayList<ConnectionManager> connectionManagers; //Potranno essere usati per rispondere alle loro stesse richieste

    public Controller(Game game, ConnectionManager cm1, ConnectionManager cm2, ConnectionManager cm3){
        this.game=game;
        connectionManagers=new ArrayList<>();
        connectionManagers.add(cm1); //Index 0
        connectionManagers.add(cm2); //Index 1
        if(game.getPlayerCount()==3){
            connectionManagers.add(cm3); //Index 2
        }
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) {
            try {
                game.gateToIsland(name, index, indexIsland, color);
            } catch (BoundException | ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectCm(name).sendMessage(errore di gate to island)
            }
    }

    public void gateToHall(String name, String color) {
            try {
                game.gateToHall(name, color);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectCm(name).sendMessage(errore di gate to hall)
            }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
            try {
                game.CloudToGate(player, color, sIndex, cIndex);
            } catch (BoundException | ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectCm(name).sendMessage(errore di cloud to gate)
            }
    }

    public void moveMotherNature(int movement) {
            try {
                game.moveMotherNature(movement);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectCm(name).sendMessage(errore di move mother nature)
            }
    }

    public void playCard(String player, int index){
            try {
                game.playCard(player, index);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectCm(name).sendMessage(errore di playCard)
            }
    }

    public void activateCharacter(ArrayList<String> a, ArrayList<Integer> b, ArrayList<Integer> c){
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
                System.out.println(e.getMessage());
            }
    }

    public void changePhase(){
            game.changePhase();
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
        //TODO:qui ci va lo switch case delle azioni
        ActionMessage am=(ActionMessage) arg;
        switch (am.ActionType){
            case 0://gateToIsland
                gateToIsland(am.strParam.get(0), am.intParam.get(0), am.intParam.get(1), am.strParam.get(1));
                break;
            case 1://gateToHall
                gateToHall(am.strParam.get(0), am.strParam.get(1));
                break;
            case 2://CloudToGate
                CloudToGate(am.strParam.get(0), am.strParam.get(1), am.intParam.get(0), am.intParam.get(1));
                break;
            case 3://moveMotherNature
                moveMotherNature(am.intParam.get(0));
                break;
            case 4://playCard
                playCard(am.strParam.get(0), am.intParam.get(0));
                break;
            case 5://activateCharacter
                activateCharacter(am.strParam, am.intParam, am.intParam2);
                break;
            case 6://changePhase
                changePhase();
                break;
        }
    }
}
