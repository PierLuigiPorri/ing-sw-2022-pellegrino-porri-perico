package it.polimi.ingsw.CLIENT;

import java.util.Scanner;

public class ClientApp {

    private static ClientMsgHandler msgHandler;

    public static void main(String args[]){
        msgHandler=new ClientMsgHandler("127.0.0.1", 4000); //Connection setup with this IP and Port numbers
        //TODO: CLI or GUI?
        cli();
    }

    private static void cli(){
        int g; //0=New Game, 1=Join Game
        //New Game or Join Game?
        g=getCorrectInput("Digit 0 to start a New Game or 1 to Join a game", 0, 1);
        if(g==0){
            newGame();
        }
        else if(g==1){
            joinGame();
        }
    }

    private static void newGame(){
        String nick;
        int gt; //Game Type
        int np; //Number of players
        //Nickname request
        nick=getValidString("Enter your nickname");
        //Game Type request
        gt=getCorrectInput("Digit 0 to use simplified rules or 1 to use expert rules", 0, 1);
        //Number of players request
        np=getCorrectInput("Digit 2 for a two-player game or 3 for a three-player game", 2, 3);
        msgHandler.newGame(nick, gt, np); //TODO:Probabilmente qua va fatto un try catch o comunque va gestita la risposta affermativa o negativa
        System.out.println("Waiting for other players and for the creation of the game");
    }

    private static void joinGame(){

    }

    private static int getCorrectInput(String request, int a, int b){
        //This method gets correct input from the client of 2 possible integer values: a, b while asking the "request"
        boolean inv=true; //Input Not Valid
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
        boolean inv=true; //Input Not Valid
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
}
