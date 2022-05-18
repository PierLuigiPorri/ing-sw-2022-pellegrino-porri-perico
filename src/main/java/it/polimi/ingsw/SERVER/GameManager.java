package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameManager extends Observable implements Runnable, Observer {

    private final ArrayList<ActionMessage> actionQueue;
    private final ArrayList<ConnectionManager> connectionManagers;
    //private final int nPlayers;

    public GameManager(ConnectionManager cm1, ConnectionManager cm2, ConnectionManager cm3, int players){
        actionQueue=new ArrayList<>();
        connectionManagers=new ArrayList<>();
        connectionManagers.add(cm1); //Index 0
        connectionManagers.add(cm2); //Index 1
        //nPlayers=players;
        if(players==3){
            connectionManagers.add(cm3); //Index 2
        }
        for (ConnectionManager cm:
                connectionManagers) {
            cm.setGameManager(this);
            cm.setGameHasBeenCreated();
        }
    }

    @Override
    public void run() {
        while (true){
            synchronized (actionQueue){
                if (!actionQueue.isEmpty()) {
                    setChanged();
                    notifyObservers(actionQueue.remove(0)); //calls Controller.update(this, arg)
                }
            }
        }
    }

    public void addAction(ActionMessage message){
        synchronized (actionQueue){
            actionQueue.add(message);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateMessage update=(UpdateMessage) arg;
        //Sends the update to all clients
        for (ConnectionManager cm:
                connectionManagers) {
            cm.send(update);
        }
    }
}
