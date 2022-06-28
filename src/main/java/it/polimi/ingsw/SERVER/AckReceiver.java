package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.EmptyQueueException;

/**
 * This class represents the thread that checks if ack messages are received regularly from the client.
 * If that's not the case, the client is considered to have disconnected, so all game-ending procedures are initiated
 * @author GC56
 */
public class AckReceiver implements Runnable{

    private final ConnectionManager cm;
    private boolean kill;

    /**
     * Constructor.
     * @param cm The ConnectionManager of the client
     * @author GC56
     */
    public AckReceiver(ConnectionManager cm){
        this.cm=cm;
        kill=false;
    }

    @Override
    public void run() {
        while(!kill){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e){
                kill=true;
                cm.clientDisconnected();
            }
            try {
                cm.clearAck();
            }catch (EmptyQueueException e){
                kill=true;
                cm.clientDisconnected();
            }
        }
    }

    public void setKill() {
        this.kill = true;
    }
}
