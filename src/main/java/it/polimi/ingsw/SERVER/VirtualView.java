package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class VirtualView extends Observable implements Runnable, Observer {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MessageType latestMessage;
    private int kill;
    private Starter start;
    private Controller controller; //Starter will set this field for every MessageHandler involved when Game and Controller are created
    private String playerName; //The nickname of the player associated to this MessageHandler
    private int gameCreated;

    public VirtualView(Socket socket){
        this.clientSocket=socket;
        this.kill=0;
        this.gameCreated=0;
        //this.reader=new MessageReader();
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
                handle(latestMessage);
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill=1;
            }
        }
        //TODO: tell Controller to kill the game and notify everyone
        System.out.println("RIP");
    }

    private void handle(MessageType message){
        if(message.type==0){
            //AckMessage
            //TODO: new Thread Countdown(this); se scade chiama msgHandler.setKill(1)
        }
        else if(message.type==1){
            //CreationMessage
            if(gameCreated==0){
                CreationMessage cm = (CreationMessage) message;
            }
        }
        else if(message.type==3){
            //ActionMessage
            if(gameCreated==1){
                ActionMessage am = (ActionMessage) message;
                setChanged();
                notifyObservers(am);
            }
        }
        else {
            //TODO: Errore
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setGameCreated() {
        this.gameCreated = 1;
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateMessage update=(UpdateMessage) arg;
        //TODO inoltrare il messaggio al client
        try {
            out.writeObject(update);
        }catch (Exception e){System.out.println(e.getMessage());}
    }
}
