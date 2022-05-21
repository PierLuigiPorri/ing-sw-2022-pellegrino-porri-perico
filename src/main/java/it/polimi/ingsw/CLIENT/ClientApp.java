package it.polimi.ingsw.CLIENT;

import java.util.Objects;

public class ClientApp {

    private final ClientMsgHandler msgHandler;

    public static void main(String[] args){
        ClientApp clientApp=new ClientApp(args);
    }

    public ClientApp(String[] CLIargs){
        Object lock=new Object();
        msgHandler=new ClientMsgHandler("127.0.0.1", 4000, lock); //Connection setup with this IP and Port numbers
        new Thread(new AckSender(msgHandler,5000)).start();
        new Thread(msgHandler).start();
        if(CLIargs.length!=0 && Objects.equals(CLIargs[0], "AAAAAAAA")){
            new Thread(new GUI(msgHandler)).start();
        }
        else{
            new Thread(new CLI(msgHandler, lock)).start();
        }
    }
    
}
