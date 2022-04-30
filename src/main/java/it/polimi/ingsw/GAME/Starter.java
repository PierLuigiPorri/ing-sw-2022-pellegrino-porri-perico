package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.GameException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.net.Socket;

public class Starter implements Runnable{

    private static ArrayList<Creation> partite=new ArrayList<>(); //Elenco di tutte le partite in fase di creazione (e quindi joinabili)
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int g; //0=New Game, 1=Join Game

    public Starter(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (Exception e){
            System.out.println("Stream connection failed");
            //TODO: Kill this thread
        }
        //Wait for New Game or Join Game
        try {
            g = in.readInt();
            //System.out.println("g:"+g);
        }
        catch (Exception e){
            System.out.println("Invalid input");
            //TODO: Kill this thread
        }
        if(g==0){
            NewGame();
            //System.out.println("gt: "+partite.get(0).getGametype());
            //System.out.println("np: "+partite.get(0).getnPlayers());
            //System.out.println("nj: "+partite.get(0).getnJoined());
            //System.out.println("nick: "+partite.get(0).getNick1());
        }
        else if(g==1){
            //System.out.println("So 1");
            int index=0; //Indice della partita che il giocatore vuole joinare
            String nick="AO";
            //TODO:Sync che ti inserisce nella prima partita disponibile

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

    private void NewGame(){
        String nick=null;
        int gt=-1; //Game Type
        int np=-1; //Number of players
        //Wait for nickname
        try {
            nick = in.readObject().toString();
        }
        catch (Exception e){
            System.out.println("Invalid input from client");
            //TODO: Kill this thread
        }
        //Wait for Game Type
        try {
            gt = in.readInt();
        }
        catch (Exception e){
            System.out.println("Invalid input from client");
            //TODO: Kill this thread
        }
        //Wait for Number of players
        try {
            np = in.readInt();
        }
        catch (Exception e){
            System.out.println("Invalid input from client");
            //TODO: Kill this thread
        }
        //Creation phase of the game
        synchronized (partite) {
            partite.add(new Creation(np, gt, nick, clientSocket));
        }
    }
}
