package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMsgHandler implements Runnable{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private View view;
    private boolean kill=false;
    public ClientMsgHandler(String host, int port){
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
    }

    public void send(MessageType message){
        try {
            out.writeObject(message);
        }catch (Exception e){System.out.println(e.getMessage());}
    }

    public void setView(View view){
        this.view=view;
    }

    @Override
    public void run() {
        while(!kill){
            try {
                MessageType latestMessage = (MessageType) in.readObject();
                view.relay(latestMessage);
                notifyAll();
            }
            catch (Exception e){
                System.out.println("Connection lost");
                kill=true;
            }
        }
    }
}
