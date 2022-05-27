package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.KillMessage;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameManager extends Observable implements Runnable, Observer {

    private final ArrayList<ActionMessage> actionQueue;
    private final ArrayList<ConnectionManager> connectionManagers;
    private boolean kill;

    public GameManager(ConnectionManager cm1, ConnectionManager cm2, ConnectionManager cm3, int players){
        actionQueue=new ArrayList<>();
        connectionManagers=new ArrayList<>();
        connectionManagers.add(cm1); //Index 0
        connectionManagers.add(cm2); //Index 1
        if(players==3){
            connectionManagers.add(cm3); //Index 2
        }
        for (ConnectionManager cm:
                connectionManagers) {
            cm.setGameManager(this);
            cm.setGameHasBeenCreated();
        }
        kill=false;
    }

    @Override
    public void run() {
        while (!kill) {
            synchronized (actionQueue) {
                if (!actionQueue.isEmpty()) {
                    setChanged();
                    notifyObservers(actionQueue.remove(0)); //calls Controller.update(this, arg)
                }
            }
        }
        System.out.println("Saluti dal thread game manager");
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
        for (ConnectionManager cm: connectionManagers) {
            cm.send(update);
        }
        System.out.println("Server sends the update to all clients");
    }

    public void setKill() {
        this.kill = true;
    }

    public void playerDisconnected(String player){
        //Avviso tutti gli altri player che uno si è disconnesso
        for (ConnectionManager cm: connectionManagers) {
            if(player!=cm.getPlayerName()) {
                UpdateMessage upd = new UpdateMessage();
                upd.update.add("Player " + player + " disconnected from the game");
                upd.gameEnded = true;
                cm.send(upd);
            }
        }
        setKill(); //Kill this thread
    }
}
