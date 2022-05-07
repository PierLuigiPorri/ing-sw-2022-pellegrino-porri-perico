package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MsgHandler implements Runnable{
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MessageType latestMessage;
    private MessageReader reader;
    private int kill;
    private Starter start;
    private Controller controller; //Starter will set this field for every MessageHandler involved when Game and Controller are created
    private String playerName; //The nickname of the player associated to this MessageHandler
    //TODO: Quando la partita inizia per davvero vanno settati tutti i playerName dei MessageHandler

    public MsgHandler(Socket socket){
        this.clientSocket=socket;
        this.kill=0;
        this.reader=new MessageReader();
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
                kill=reader.handle(latestMessage);
            }
            catch (Exception e){
                System.out.println("Connection lost");
            }
        }
        System.out.println("RIP");
    }



    public void setController(Controller controller) {
        this.controller = controller;
    }

    public String getPlayerName(){
        return playerName;
    }
}
