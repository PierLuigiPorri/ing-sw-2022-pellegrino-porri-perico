package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.AckMessage;

public class AckReceiver implements Runnable{

    private ConnectionManager cm;

    public AckReceiver(ConnectionManager cm){
        this.cm=cm;
    }

    @Override
    public void run() {
        while(true){
            try {
                AckMessage am=cm.getNextAck();
                //TODO: Resetta il timer
            }catch (Exception e){}
        }
    }
}
