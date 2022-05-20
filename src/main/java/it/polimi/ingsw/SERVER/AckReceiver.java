package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.EmptyQueueException;

public class AckReceiver implements Runnable{

    private ConnectionManager cm;
    private boolean kill;

    public AckReceiver(ConnectionManager cm){
        this.cm=cm;
        kill=false;
    }

    @Override
    public void run() {
        while(!kill){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e){}
            try {
                cm.clearAck();
                System.out.println("ACK ricevuti");
            }catch (EmptyQueueException e){
                kill=true;
                System.out.println("Killed");
            }

        }
    }
}
