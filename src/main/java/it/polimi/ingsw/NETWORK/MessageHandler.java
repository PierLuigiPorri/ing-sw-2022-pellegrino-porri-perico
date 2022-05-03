package it.polimi.ingsw.NETWORK;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageHandler implements Runnable{
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Message latestMessage;
    private int kill;
    private Starter start;
    private Controller controller; //Starter will set this field for every MessageHandler involved when Game and Controller are created

    public MessageHandler(Socket socket){
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
                latestMessage= (Message) in.readObject();
                kill=handle(latestMessage);
            }
            catch (Exception e){
                System.out.println("Connection lost");
            }
        }
        System.out.println("RIP");
    }
    private int handle(Message message){
        if(message.getKill()==0) {
            //Handling of the message
            return 0;
        }
        else{
            return -1;
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
