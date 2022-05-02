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
        int g; //0=New Game, 1=Join Game
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
        g=getCorrectInput("Digit 0 to start a New Game or 1 to Join a game", 0, 1);
        try {
            out.writeInt(g);
            out.flush();
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        if(g==0){
            newGame(out);
        }
        else if(g==1){

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

    private static String getValidString(String request){
        //This method gets a non empty reply String while asking the "request"
        Boolean inv=true; //Input Not Valid
        String input="";
        Scanner s=new Scanner(System.in);
        while(inv) {
            System.out.println(request);
            try {
                input = s.nextLine();
                if(!input.isEmpty()) {
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
        return input;
    }

    private static void newGame(ObjectOutputStream out){
        String nick;
        int gt; //Game Type
        int np; //Number of players
        //Nickname request
        nick=getValidString("Enter your nickname");
        try {
            out.writeObject(nick);
            out.flush();
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        //Game Type request
        gt=getCorrectInput("Digit 0 to use simplified rules or 1 to use expert rules", 0, 1);
        try {
            out.writeInt(gt);
            out.flush();
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        //Number of players request
        np=getCorrectInput("Digit 2 for a two-player game or 3 for a three-player game", 2, 3);
        try {
            out.writeInt(np);
            out.flush();
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        System.out.println("In attesa degli altri giocatori e della creazione della partita");
    }
}
