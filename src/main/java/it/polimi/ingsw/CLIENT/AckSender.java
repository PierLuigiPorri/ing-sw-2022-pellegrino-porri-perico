package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.AckMessage;

public class AckSender implements Runnable{

    private ClientMsgHandler mh;
    private int timeout; //millis
    private boolean kill;

    public AckSender(ClientMsgHandler mh, int timeout){
        this.timeout=timeout;
        this.mh=mh;
        kill=false;
    }

    @Override
    public void run() {
        while(!kill) {
            mh.send(new AckMessage());
            //System.out.println("ACK mandato");
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Saluti dall'ackSender");
    }

    public void setKill() {
        this.kill = true;
    }
}
