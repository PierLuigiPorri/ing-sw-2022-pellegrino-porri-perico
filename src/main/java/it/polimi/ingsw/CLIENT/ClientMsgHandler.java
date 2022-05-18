package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMsgHandler implements Runnable{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private View view;
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



    public void newGame(String nick, int gt, int np){
        //TODO:
        //Si occupa di creare un messaggio di tipo newGame con i parametri specificati
        //poi fa out.writeObject(Messaggio); per inviarlo
        //A questo punto si mette in attesa della risposta del server
        //Quando la riceve, la comunica al client

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

    }
}
