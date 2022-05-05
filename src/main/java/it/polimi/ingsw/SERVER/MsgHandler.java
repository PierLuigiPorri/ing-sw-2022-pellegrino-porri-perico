package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MsgHandler implements Runnable{
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MessageType latestMessage;
    private int kill;
    private Starter start;
    private Controller controller; //Starter will set this field for every MessageHandler involved when Game and Controller are created

    public MsgHandler(Socket socket){
        this.clientSocket=socket;
        this.kill=0;
    }
    @Override
    public void run() {
        System.out.println("Thread started");
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            kill=1;
        }
        while(kill==0) {
            try {
                latestMessage = (MessageType) in.readObject();
                kill=handle(latestMessage);
            }
            catch (Exception e){
                System.out.println("Connection lost");
            }
        }
        System.out.println("RIP");
    }
    private int handle(MessageType message){
        return 1;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
