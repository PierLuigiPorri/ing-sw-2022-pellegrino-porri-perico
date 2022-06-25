package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class manages the game after it actually began. There's one GameManager for every game.
 * @author GC56
 */
public class GameManager extends Observable implements Runnable, Observer {

    private final ArrayList<ActionMessage> actionQueue;
    private final ArrayList<ConnectionManager> connectionManagers;
    private boolean kill;

    /**
     * Constructor.
     * @param cm1 ConnectionManager of the first player
     * @param cm2 ConnectionManager of the second player
     * @param cm3 ConnectionManager of the third player. If it's a two player game, null
     * @param players Number of Players
     * @requires players==2 || players==3
     * @author GC56
     */
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
            cm.setGameHasBeenCreated(true);
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

    /**
     * Adds an Action message to the Action message queue
     * @param message The Action message to add to the queue
     * @author GC56
     */
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
        if(update.gameEnded){
            for (ConnectionManager cm: connectionManagers) {
                cm.setGameHasBeenCreated(false);
            }
            this.kill=true; //Kill this thread
        }
    }

    /**
     * This method gets called when one player disconnects from the game. It terminates the game after sending a messagge to all connected clients
     * @param player The nickname of the player that disconnected
     * @author GC56
     */
    public void playerDisconnected(String player){
        //Avviso tutti gli altri player che uno si è disconnesso
        for (ConnectionManager cm: connectionManagers) {
            if(!player.equals(cm.getPlayerName())) {
                UpdateMessage upd = new UpdateMessage();
                upd.update.add("Player " + player + " disconnected from the game");
                upd.gameEnded = true;
                cm.send(upd);
                cm.setGameHasBeenCreated(false);
            }
        }
        this.kill=true; //Kill this thread
    }
}
