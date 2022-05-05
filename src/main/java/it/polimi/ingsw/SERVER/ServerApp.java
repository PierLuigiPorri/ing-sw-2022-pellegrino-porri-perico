package it.polimi.ingsw.SERVER;

import java.net.*;
import java.net.ServerSocket;


public class ServerApp
{
    private static int port=4000; //Connection port
    private static int connectedClients=0;

    public static void main(String args[])
    {
        ServerSocket ssock=null;
        Socket sock=null;
        try {
            ssock = new ServerSocket(port);
            System.out.println("Server Socket creation successful");
        }
        catch (Exception e){
            System.out.println("Server Socket creation failed");
            System.exit(0);
        }
        while(true){
            //Waiting for client connections
            try {
                sock = ssock.accept();
                connectedClients++;
                System.out.println("Client connection "+connectedClients+" accepted");
            }
            catch (Exception e){
                System.out.println("Client connection failed");
            }
            new Thread(new MsgHandler(sock)).start();
        }
    }
}
