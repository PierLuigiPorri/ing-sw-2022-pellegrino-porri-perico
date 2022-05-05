package it.polimi.ingsw.CLIENT;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMsgHandler {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
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
        //e poi fa out.writeObject(Messaggio); per inviarlo
        //Inoltre va gestito il meccanismo di ricezione delle risposte
    }
}
