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
                Thread.sleep(60000);
            } catch (InterruptedException e){}
            try {
                cm.clearAck();
                System.out.println("ACK ricevuti da "+cm.toString());
            }catch (EmptyQueueException e){
                kill=true;
                cm.clientDisconnected();
            }
        }
        System.out.println("AckReceiver killed");
    }

    public void setKill() {
        this.kill = true;
    }
}
