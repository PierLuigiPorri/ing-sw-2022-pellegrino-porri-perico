package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.EmptyQueueException;
import it.polimi.ingsw.MESSAGES.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class represent the thread that manages message exchanges between client and server during the game setup and during the game itself
 * @author GC56
 */
public class ConnectionManager implements Runnable{

    private final Starter start;
    private GameManager gameManager; //Assigned by Starter
    private String playerName; //The nickname of the player associated to this MessageHandler
    private final Socket clientSocket;
    private MessageType latestMessage;
    private boolean kill;
    private boolean gameHasBeenCreated;
    private int joinedGameId;
    private AckReceiver ackReceiver;
    //Network I/O
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //Queue
    private final ArrayList<AckMessage> ackQueue;

    /**
     * ConnectionManager constructor.
     * @param sock The IP socket for the client.
     * @author GC56
     */
    public ConnectionManager(Socket sock){
        clientSocket=sock;
        gameHasBeenCreated=false;
        ackQueue=new ArrayList<>();
        start=new Starter(this);
        kill=false;
        joinedGameId=-1;
    }

    @Override
    public void run() {
        try {
            ackReceiver=new AckReceiver(this);
            new Thread(ackReceiver).start();
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
                if(latestMessage.type!=0)
                    System.out.println("Ho ricevuto un messaggio "+latestMessage.type);
                if(latestMessage.type==5){
                    //I received a KillMessage
                    if(joinedGameId!=-1) {
                        if (gameHasBeenCreated) {
                            gameManager.playerDisconnected(playerName);
                            gameHasBeenCreated=false;
                        } else {
                            try {
                                start.killGame(joinedGameId);
                                ackReceiver.setKill();
                                kill=true;
                            } catch (Exception e) {
                                send(new ResponseMessage("The game you tried to kill doesn't exist", false, null, false));
                            }
                        }
                    }
                    else send(new ResponseMessage("Stop trying to kill games you haven't joined please :)", false, null, false));
                }
                else if(latestMessage.type==0){
                    //I received an AckMessage, so I add it to the queue
                    synchronized (ackQueue){
                        ackQueue.add((AckMessage) latestMessage);
                    }
                }
                else{
                    //System.out.println("GHBC: "+gameHasBeenCreated);
                    if(!gameHasBeenCreated){
                        //If I receive CreationMessage, I handle it; if I receive other messages I ignore them
                        if(latestMessage.type==1){
                            System.out.println("Creation message");
                            CreationMessage mex= (CreationMessage) latestMessage;
                            switch (mex.creationid){
                                case 0:
                                    //New Game
                                    try {
                                        joinedGameId = start.newGame(mex.gameType, mex.players, mex.nick);
                                        System.out.println("New Game");
                                        this.send(new ResponseMessage("You successfully created a new game with id: "+joinedGameId, true, joinedGameId, false));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, null, false));
                                    }
                                    break;
                                case 1:
                                    //Join Random Game
                                    try {
                                        joinedGameId = start.joinRandomGame(mex.nick);
                                        //this.send(new ResponseMessage("You successfully joined the game with id: "+joinedGameId, true));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, null, false));
                                    }
                                    break;
                                case 2:
                                    //Join Game with ID
                                    try {
                                        joinedGameId = start.joinGameWithId(mex.gameid, mex.nick);
                                        //this.send(new ResponseMessage("You successfully joined the game with id: "+joinedGameId, true));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, null, false));
                                    }
                                    break;
                                case 3:
                                    //See available games
                                    //TODO
                                    break;
                            }

                        }
                        else System.out.println("Nope");
                    }
                    else {
                        //If I receive ActionMessage, I add it to the queue; if I receive other messages I ignore them
                        if(latestMessage.type==3){
                            gameManager.addAction((ActionMessage) latestMessage);
                        }
                        else System.out.println("Nope during game");
                    }
                }
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill();
                clientDisconnected();
            }
        }
        System.out.println("Saluti dal Connection Manager");
    }

    /**
     * This method clears the ack queue
     * @throws EmptyQueueException if the queue is empty
     * @author GC56
     */
    public void clearAck() throws EmptyQueueException{
        synchronized (ackQueue) {
            if (!ackQueue.isEmpty()) {
                ackQueue.clear();
            } else throw new EmptyQueueException("The queue is empty");
        }
    }

    /**
     * This is the method that sends any MessageType message to the client
     * @param message The message to send
     * @author GC56
     */
    public void send(MessageType message){
        try {
            if(out!=null)
                out.writeObject(message);
        }catch (IOException e){System.out.println(e.getMessage());}
    }

    public void setGameManager(GameManager gm){
        this.gameManager=gm;
    }

    public void setGameHasBeenCreated(boolean ghbc){
        this.gameHasBeenCreated=ghbc;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void kill() {
        this.kill = true;
        ackReceiver.setKill();
    }

    /**
     * The method that gets called if a client disconnects. If necessary, it passes this information to the GameManager
     * @author GC56
     */
    public void clientDisconnected(){
        if(gameHasBeenCreated) {
            gameManager.playerDisconnected(playerName);
            gameHasBeenCreated=false;
        }
        else{
            if(joinedGameId!=-1) {
                try {
                    start.killGame(joinedGameId);
                } catch (Exception e) {
                    send(new ResponseMessage("The game you tried to kill doesn't exist", false, null, false));
                }
            }
            else send(new ResponseMessage("Stop trying to kill games you haven't joined please :)", false, null, false));
        }
        this.kill=true;
    }
}
