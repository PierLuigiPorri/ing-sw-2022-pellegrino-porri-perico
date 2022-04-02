package it.polimi.ingsw;

import it.polimi.ingsw.CONTROLLER.Starter;
import java.net.*;
import java.net.ServerSocket;


public class ServerApp
{
    public static void main()
    {
        int porta=4000; //Porta di connessione; numero da modificare
        ServerSocket ssock=null;
        Socket sock=null;
        try {
            ssock = new ServerSocket(porta);
        }
        catch (Exception e){

        }
        while(true){
            //Attendo connessioni
            try {
                sock = ssock.accept();
            }
            catch (Exception e){

            }
            new Thread(new Starter(sock)).start();
        }
    }
}
