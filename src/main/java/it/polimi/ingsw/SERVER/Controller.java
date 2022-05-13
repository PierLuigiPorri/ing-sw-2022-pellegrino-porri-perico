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
    private ArrayList<VirtualView> messageHandlers; //Potranno essere usati per rispondere alle loro stesse richieste
    private final Object lockGame;

    public Controller(Game game, VirtualView mh1, VirtualView mh2, VirtualView mh3){
        this.game=game;
        messageHandlers=new ArrayList<>();
        messageHandlers.add(mh1); //Index 0
        messageHandlers.add(mh2); //Index 1
        if(game.getPlayerCount()==3){
            messageHandlers.add(mh3); //Index 2
        }
        lockGame=new Object();
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) {
        synchronized (lockGame) {
            try {
                game.gateToIsland(name, index, indexIsland, color);
            } catch (BoundException | ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectMh(name).sendMessage(errore di gate to island)
            }
        }
    }

    public void gateToHall(String name, String color) {
        synchronized (lockGame) {
            try {
                game.gateToHall(name, color);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectMh(name).sendMessage(errore di gate to hall)
            }
        }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
        synchronized (lockGame) {
            try {
                game.CloudToGate(player, color, sIndex, cIndex);
            } catch (BoundException | ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectMh(name).sendMessage(errore di cloud to gate)
            }
        }
    }

    public void moveMotherNature(int movement) {
        synchronized (lockGame) {
            try {
                game.moveMotherNature(movement);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectMh(name).sendMessage(errore di move mother nature)
            }
        }
    }

    public void playCard(String player, int index){
        synchronized (lockGame) {
            try {
                game.playCard(player, index);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                //TODO: getCorrectMh(name).sendMessage(errore di playCard)
            }
        }
    }

    private VirtualView getCorrectMh(String name){
        for (VirtualView mh:
             messageHandlers) {
            if(mh.getPlayerName().equals(name)){
                return mh;
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
            case 1://gateToHall
                gateToHall(am.strParam.get(0), am.strParam.get(1));
            case 2://CloudToGate
                CloudToGate(am.strParam.get(0), am.strParam.get(1), am.intParam.get(0), am.intParam.get(1));
            case 3://moveMotherNature
                moveMotherNature(am.intParam.get(0));
            case 4://playCard
                playCard(am.strParam.get(0), am.intParam.get(0));
        }
    }
}
