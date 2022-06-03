package it.polimi.ingsw.CLIENT;


public class CLIAPP {

    public static void main(String[] args){
        CLIAPP CLIAPP =new CLIAPP();
    }

    public CLIAPP(){
        Object lock = new Object();
        ClientMsgHandler msgHandler = new ClientMsgHandler("127.0.0.1", 4000, lock); //Connection setup with this IP and Port numbers
        AckSender ackSender = new AckSender(msgHandler, 5000);
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        new Thread(new CLI(msgHandler, lock)).start();
    }
}
