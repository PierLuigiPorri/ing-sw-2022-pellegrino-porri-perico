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
    private AckReceiver ackReceiver;
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
                            gameManager.setKill();
                        } else {
                            try {
                                start.killGame(joinedGameId);
                                ackReceiver.setKill();
                                kill=true;
                            } catch (Exception e) {
                                send(new ResponseMessage("The game you tried to kill doesn't exist", false, false));
                            }
                        }
                    }
                    else send(new ResponseMessage("Stop trying to kill games you haven't joined please :)", false, false));
                }
                else if(latestMessage.type==0){
                    //I received an AckMessage, so I add it to the queue
                    synchronized (ackQueue){
                        ackQueue.add((AckMessage) latestMessage);
                    }
                }
                else{
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
                                        this.send(new ResponseMessage("You successfully created a new game with id: "+joinedGameId, true, false));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, false));
                                    }
                                    break;
                                case 1:
                                    //Join Random Game
                                    try {
                                        joinedGameId = start.joinRandomGame(mex.nick);
                                        //this.send(new ResponseMessage("You successfully joined the game with id: "+joinedGameId, true));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, false));
                                    }
                                    break;
                                case 2:
                                    //Join Game with ID
                                    try {
                                        joinedGameId = start.joinGameWithId(mex.gameid, mex.nick);
                                        //this.send(new ResponseMessage("You successfully joined the game with id: "+joinedGameId, true));
                                    }catch (Exception e){
                                        this.send(new ResponseMessage(e.getMessage(), false, false));
                                    }
                                    break;
                                case 3:
                                    //See available games
                                    //TODO
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
                kill();
                clientDisconnected();
            }
        }
        System.out.println("Saluti dal Connection Manager");
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
            if(out!=null)
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

    public void kill() {
        this.kill = true;
        ackReceiver.setKill();
    }

    public void clientDisconnected(){
        gameManager.playerDisconnected(playerName);
        this.kill=true;
    }
}
