package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.GameException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.net.Socket;

public class Starter implements Runnable{

    private static ArrayList<Creation> partite=new ArrayList<>(); //Elenco di tutte le partite in fase di creazione (e quindi joinabili)
    private Socket clientSocket;

    public Starter(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        int g;//0=New Game, 1=Join Game
        PrintWriter out=null;
        BufferedReader in=null;
        Boolean inv=true; //Input Not Valid
        String input=null;

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception e){
            System.out.println("Connection failed");
        }
        //New Game or Join Game?
        g=getCorrectInput(out, in, "Digit 0 to start a new game or 1 to join a game and press enter", 0, 1);
        if(g==0){
            int np; //Number of players
            int gt; //Game Type
            String nick;
            //Nickname request
            while(inv){
                out.println("Enter your nickname");
                try {
                    input = in.readLine();
                    inv=false;
                }
                catch (Exception e){
                    out.println("Input is not valid");
                }
            }
            inv=true; //Reset of inv
            nick=input;
            //Game Type request
            gt=getCorrectInput(out, in, "Digit 0 to select normal game or 1 to select expert game and press enter", 0, 1);
            //Request of number of players
            np=getCorrectInput(out, in, "Digit 2 to select a two-player game or 3 for a three-player game and press enter", 2, 3);

            //Creation phase of the game
            synchronized (partite) {
                partite.add(new Creation(np, gt, nick, clientSocket));
            }
            System.out.println("In attesa degli altri giocatori e della creazione della partita");

        }
        else if(g==1){
            int index=0; //Indice della partita che il giocatore vuole joinare
            String nick="AO";
            //TODO:Scelta partita da Joinare, anche qui dovrà essere sync se no l'indice potrebbe cambiare

            //TODO:Scelta nick con controllo se il nick è già usato nella partita

            synchronized (partite) {
                partite.get(index).setnJoined(); //njoinati++
                if (partite.get(index).getnJoined() == 2) {
                    partite.get(index).setNick2(nick);
                    partite.get(index).setSock2(clientSocket);
                } else if (partite.get(index).getnJoined() == 3) {
                    partite.get(index).setNick3(nick);
                    partite.get(index).setSock3(clientSocket);
                }

                if (partite.get(index).getnPlayers() == partite.get(index).getnJoined()) {
                    //Creo la partita chiamando la nostra classe Game
                    //e passando gli attributi presenti in partite[index] al costruttore giusto
                    //usando gli appositi getter presenti in Creation
                    try {
                        Game game = new Game(partite.get(index).getnPlayers(), partite.get(index).getGametype(), partite.get(index).getNick1(), partite.get(index).getSock1(), partite.get(index).getNick2(), partite.get(index).getSock2(), partite.get(index).getNick3(), partite.get(index).getSock3());
                        //Se va tutto a buon fine:
                        partite.remove(index); //Rimozione della partita da quelle in fase di creazione
                    } catch (GameException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
    private int getCorrectInput(PrintWriter out, BufferedReader in, String request, int a, int b){
        //This method gets correct input from the client of 2 possible integer values: a, b while asking the "request"
        Boolean inv=true; //Input Not Valid
        String input=null;
        while(inv) {
            out.println(request);
            try {
                input = in.readLine();
                if(Integer.parseInt(input)==a || Integer.parseInt(input)==b) {
                    inv = false;
                }
                else{
                    out.println("Input is not valid");
                }
            }
            catch (Exception e){
                out.println("Input is not valid");
            }
        }
        return Integer.parseInt(input);
    }
}
