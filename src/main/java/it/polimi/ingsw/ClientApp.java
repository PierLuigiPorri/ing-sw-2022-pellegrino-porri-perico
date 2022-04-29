package it.polimi.ingsw;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientApp {
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String args[]){
        try {
            socket = new Socket("127.0.0.1", 4000);
            System.out.println("Connected!");
        }
        catch(Exception e){
            System.out.println("Connection failed");
            System.exit(0);
        }
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        System.out.println("New Game or Join Game?");

    }
}
