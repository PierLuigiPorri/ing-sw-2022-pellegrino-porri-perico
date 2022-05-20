package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.EmptyQueueException;
import it.polimi.ingsw.MESSAGES.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionManager implements Runnable{

    private final Starter start;
    private GameManager gameManager; //Assigned by Starter
    private String playerName; //The nickname of the player associated to this MessageHandler
    private final Socket clientSocket;
    private MessageType latestMessage;
    private boolean kill;
    private boolean gameHasBeenCreated;
    private int joinedGameId;
    //Network I/O
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //Queue
    private final ArrayList<AckMessage> ackQueue;

    public ConnectionManager(Socket sock){
        clientSocket=sock;
        gameHasBeenCreated=false;
        ackQueue=new ArrayList<>();
        start=new Starter(this);
    }

    @Override
    public void run() {
        new Thread(new AckReceiver(this)).start();
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream()); //Importante che sia prima di in
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            kill=true;
        }
        while(!kill){
            try {
                latestMessage = (MessageType) in.readObject();
                if(latestMessage.type==5){
                    //I received a KillMessage
                    kill=true;
                    if(!gameHasBeenCreated){
                        //TODO: killare tutto
                    }
                    else {
                        start.killGame(joinedGameId);
                    }
                }
                else if(latestMessage.type==0){
                    //I received an AckMessage, so I add it to the queue
                    synchronized (ackQueue){
                        ackQueue.add((AckMessage) latestMessage);
                    }
                }
                else{
                    if(!gameHasBeenCreated){
                        //If I receive CreationMessage, I add it to the queue; if I receive other messages I ignore them
                        if(latestMessage.type==1){
                            CreationMessage mex= (CreationMessage) latestMessage;
                            switch (mex.creationid){
                                case 0:
                                    //New Game
                                    joinedGameId=start.newGame(mex.gameType, mex.players, mex.nick);
                                    this.send(new ResponseMessage("You successfully created a new game with id: "+joinedGameId));
                                    break;
                                case 1:
                                    //Join Random Game

                                    break;
                                case 2:
                                    //Join Game with ID
                                    break;
                                case 3:
                                    //See available games
                                    break;
                            }

                        }
                    }
                    else {
                        //If I receive ActionMessage, I add it to the queue; if I receive other messages I ignore them
                        if(latestMessage.type==3){
                            gameManager.addAction((ActionMessage) latestMessage);
                        }
                    }
                }
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill=true;
            }
        }
    }

    public void clearAck() throws EmptyQueueException{
        synchronized (ackQueue) {
            if (!ackQueue.isEmpty()) {
                ackQueue.clear();
            } else throw new EmptyQueueException("The queue is empty");
        }
    }

    public void send(MessageType message){
        try {
            out.writeObject(message);
        }catch (IOException e){System.out.println(e.getMessage());}
    }

    public void setGameManager(GameManager gm){
        this.gameManager=gm;
    }

    public void setGameHasBeenCreated(){
        this.gameHasBeenCreated=true;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }
}
