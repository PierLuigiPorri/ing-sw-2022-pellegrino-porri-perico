package it.polimi.ingsw;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String args[]){
        Socket socket=null;
        ObjectInputStream in=null;
        ObjectOutputStream out=null;
        try {
            socket = new Socket("127.0.0.1", 4000);
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
        //New Game or Join Game?
        try {
            out.writeInt(getCorrectInput("Digit 0 to start a New Game or 1 to Join a game", 0, 1));
            out.flush();
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }

    }

    private static int getCorrectInput(String request, int a, int b){
        //This method gets correct input from the client of 2 possible integer values: a, b while asking the "request"
        Boolean inv=true; //Input Not Valid
        String input=null;
        Scanner s=new Scanner(System.in);
        while(inv) {
            System.out.println(request);
            try {
                input = s.nextLine();
                if(Integer.parseInt(input)==a || Integer.parseInt(input)==b) {
                    inv = false;
                }
                else{
                    System.out.println("Input is not valid");
                }
            }
            catch (Exception e){
                System.out.println("Input is not valid");
            }
        }
        return Integer.parseInt(input);
    }
}
