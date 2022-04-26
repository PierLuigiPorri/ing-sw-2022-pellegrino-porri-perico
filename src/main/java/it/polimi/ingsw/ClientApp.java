package it.polimi.ingsw;

import java.net.Socket;

public class ClientApp {
    public static void main(){
        try {
            Socket socket = new Socket("127.0.0.1", 4000);
            System.out.println("Connected!");
        }
        catch(Exception e){
            System.out.println("Connection failed");
        }
        while(true){
            //Ricevi dal server "Che azione vuoi fare?"
            /*if(azione==...){
                TcpSend(String Azione, String Player...
            }
            else if(...){

            }*/
        }
    }
}
