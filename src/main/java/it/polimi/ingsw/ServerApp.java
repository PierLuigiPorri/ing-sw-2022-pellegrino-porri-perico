package it.polimi.ingsw;

import it.polimi.ingsw.GAME.Starter;
import java.net.*;
import java.net.ServerSocket;


public class ServerApp
{
    public static void main(String args[])
    {
        int port=4000; //Connection port
        ServerSocket ssock=null;
        Socket sock=null;
        try {
            ssock = new ServerSocket(port);
        }
        catch (Exception e){
            System.out.println("Server Socket creation failed");
        }
        while(true){
            //Waiting for client connections
            try {
                sock = ssock.accept();
            }
            catch (Exception e){
                System.out.println("Client connection failed");
            }
            new Thread(new Starter(sock)).start();
        }
    }
}
