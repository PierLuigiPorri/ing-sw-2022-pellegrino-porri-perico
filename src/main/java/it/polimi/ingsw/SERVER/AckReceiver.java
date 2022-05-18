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
                //Thread.sleep(5000);
                AckMessage am=cm.getNextAck();
                System.out.println("ACK ricevuto");
            }catch (Exception e){
                //System.out.println("Ho killato tutto");
            }
        }
    }
}
