package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.AckMessage;

public class AckSender implements Runnable{

    private ClientMsgHandler mh;
    private int timeout; //millis

    public AckSender(ClientMsgHandler mh, int timeout){
        this.timeout=timeout;
        this.mh=mh;
    }

    @Override
    public void run() {
        while(true) {
            mh.send(new AckMessage());
            System.out.println("ACK mandato");
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
            }
        }
        //while(true){}
    }
}
