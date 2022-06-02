package it.polimi.ingsw.CLIENT;

import java.util.Objects;

public class ClientApp {

    private final ClientMsgHandler msgHandler;
    private final AckSender ackSender;

    public static void main(String[] args){
        ClientApp clientApp=new ClientApp(args);
    }

    public ClientApp(String[] CLIargs) {
        Object lock = new Object();
        msgHandler = new ClientMsgHandler("127.0.0.1", 4000, lock); //Connection setup with this IP and Port numbers
        ackSender = new AckSender(msgHandler, 5000);
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        //if(CLIargs.length!=0 && Objects.equals(CLIargs[0], "AAAAAAAA")){
            new Thread(()-> {
                MainMenuController menuController = new MainMenuController();
                menuController.setMsgHandler(msgHandler);
                menuController.setLock(lock);
                GUIAPP.main(CLIargs);
            }).start();
        //}
        //else{
        //    new Thread(new CLI(msgHandler, lock)).start();
        //}
    }
}
