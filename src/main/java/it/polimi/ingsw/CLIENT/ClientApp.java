package it.polimi.ingsw.CLIENT;

import java.util.Scanner;

public class ClientApp {

    private static ClientMsgHandler msgHandler;
    private static View view;

    public static void main(String[] args){
        msgHandler=new ClientMsgHandler("127.0.0.1", 4000); //Connection setup with this IP and Port numbers
        //TODO: CLI or GUI? Facciamo un altro main?
        view=new CLI(msgHandler);
    }
    

    private static void newGame(){
        String nick = null;
        int gt; //Game Type
        int np; //Number of players
        //Nickname request
        //Game Type request
        gt=getCorrectInput("Digit 0 to use simplified rules or 1 to use expert rules", 0, 1);
        //Number of players request
        np=getCorrectInput("Digit 2 for a two-player game or 3 for a three-player game", 2, 3);
        msgHandler.newGame(nick, gt, np); //TODO:Probabilmente qua va fatto un try catch o comunque va gestita la risposta affermativa o negativa
        System.out.println("Waiting for other players and for the creation of the game");
    }

    private static void joinGame(){

    }

    public static int getCorrectInput(String request, int a, int b){
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

    
}
