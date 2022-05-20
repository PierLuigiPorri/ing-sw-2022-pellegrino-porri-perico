package it.polimi.ingsw.CLIENT;

import java.util.Objects;

public class ClientApp {

    private final ClientMsgHandler msgHandler;
    private final View view;

    public static void main(String[] args){
        ClientApp clientApp=new ClientApp(args);
    }

    public ClientApp(String[] CLIargs){
        msgHandler=new ClientMsgHandler("127.0.0.1", 4000); //Connection setup with this IP and Port numbers
        if(CLIargs.length!=0 && Objects.equals(CLIargs[0], "AAAAAAAA")){
            view=new GUI(msgHandler);
        }
        else{
            view=new CLI(msgHandler);
        }
        msgHandler.setView(view);
        new Thread(new AckSender(msgHandler,5000)).start();
        new Thread(msgHandler).start();
    }
    
}
