package it.polimi.ingsw.SERVER;

import java.net.*;

/**
 * The server application.
 * @author GC56
 */
public class ServerApp
{
    private static int port=50000; //Connection port
    private static int connectedClients=0;

    /**
     * Server starts up and waits for client connections on port 50000.
     * It creates and runs a connection manager thread for every client that connects.
     * @author GC56
     */
    public static void main(String[] args)
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
                new Thread(new ConnectionManager(sock)).start();
            }
            catch (Exception e){
                System.out.println("Client connection failed");
            }
        }
    }
}
