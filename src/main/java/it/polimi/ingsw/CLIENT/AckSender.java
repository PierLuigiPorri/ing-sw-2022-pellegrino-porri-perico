package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.AckMessage;

/**
 * This class represents the thread that sends AckMessages to the server
 * @author GC56
 */
public class AckSender implements Runnable{

    private final ClientMsgHandler mh;
    private final int timeout; //millis
    private boolean kill;

    /**
     * Constructor method.
     * @param mh The ClientMessageHandler
     * @param timeout Timeout between ACKs in milliseconds
     * @author GC56
     */
    public AckSender(ClientMsgHandler mh, int timeout){
        this.timeout=timeout;
        this.mh=mh;
        kill=false;
    }

    @Override
    public void run() {
        while(!kill) {
            mh.send(new AckMessage());
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                kill=true;
            }
        }
    }

}
