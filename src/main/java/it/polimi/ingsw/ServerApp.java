package it.polimi.ingsw;

import it.polimi.ingsw.CONTROLLER.Controller;
import java.net.*;


public class ServerApp
{
    public static void main()
    {
        int porta=4000; //Porta di connessione; numero da modificare
        ServerSocket ssock = new ServerSocket(porta);
        while(true){
            //Attendo connessioni
            Socket sock = ssock.accept();
            new Thread(new Controller(sock)).start();
        }
    }
}
