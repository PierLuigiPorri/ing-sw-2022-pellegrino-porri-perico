package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.AckMessage;
import it.polimi.ingsw.MESSAGES.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadQueue implements Runnable {
    private ArrayList<MessageType> queue;
    private ArrayList<AckMessage> ackQueue;
    private boolean kill;
    private MessageType message;
    private ObjectInputStream in;
    private Socket clientSocket;

    public ThreadQueue(Socket sock){
        queue=new ArrayList<>();
        ackQueue=new ArrayList<>();
        kill=false;
        clientSocket=sock;
    }

    public void run(){
        //System.out.println("Thread started");
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            kill=true;
        }
        while(!kill){
            try {
                message = (MessageType) in.readObject();
                if(message.type==0){
                    ackQueue.add((AckMessage) message);
                }
                else {
                    queue.add(message);
                }
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill=true;
            }
        }
    }

    public boolean isKill() {
        return kill;
    }

    public MessageType getLatestMessage() {
            return queue.remove(0);
    }

    public ArrayList<MessageType> getQueue() {
        return queue;
    }

    public ArrayList<AckMessage> getAckQueue() {
        return ackQueue;
    }

    public AckMessage getLatestAck(){
        //TODO: Ricordarsi quando la si usa di controllare che la coda non sia null
        return ackQueue.remove(0);
    }
}
