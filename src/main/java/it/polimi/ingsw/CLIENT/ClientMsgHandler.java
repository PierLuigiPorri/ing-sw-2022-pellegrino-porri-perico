package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMsgHandler implements Runnable{
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private View view;
    private boolean kill;
    private final Object lock;
    private AckSender ackSender;

    private ArrayList<UpdateMessage> updates;
    private final ArrayList<ResponseMessage> responses;

    public ClientMsgHandler(String host, int port, Object lock){
        try {
            socket = new Socket(host, port);
            System.out.println("Connected!");
        }
        catch(Exception e){
            System.out.println("Connection failed");
            System.exit(0);
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        this.kill=false;
        this.lock=lock;
        this.updates =new ArrayList<>();
        this.responses=new ArrayList<>();
    }

    public void send(MessageType message){
        try {
            out.writeObject(message);
            //System.out.println(message.type);
        }catch (Exception e){System.out.println("Message send failed");}
    }

    public void setView(View view){
        this.view=view;
    }

    @Override
    public void run() {
        while(!kill){
            try {
                MessageType latestMessage = (MessageType) in.readObject();
                System.out.println("Ho ricevuto un messaggio "+latestMessage.type);
                synchronized (lock) {
                    sort(latestMessage);
                }
                try {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill=true;
            }
        }
        System.out.println("Saluti dal Client Msg Handler");
    }

    public void sort(MessageType message){
        if(message.type==4) {
            this.updates.add((UpdateMessage) message);
            System.out.println(ANSI_CYAN+"Something's happened in the meantime! Hit Refresh!"+ANSI_RESET);
        }
        else {
            ResponseMessage rMex=(ResponseMessage) message;
            this.responses.add(rMex);
            if(rMex.kill) {
                this.kill = true;
                ackSender.setKill();
                view.setKill();
            }
        }
    }

    public ArrayList<UpdateMessage> getUpdates(){
        return this.updates;
    }

    public ArrayList<ResponseMessage> getResponses(){
        return this.responses;
    }

    public void clearMessages(){
        this.updates.clear();
        this.responses.clear();
    }

    public void setAckSender(AckSender ackSender){
        this.ackSender=ackSender;
    }
}
