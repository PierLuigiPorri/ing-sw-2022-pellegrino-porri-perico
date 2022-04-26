package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.GameException;

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
        System.out.println("Thread started");
        int g=0;//0=New Game, 1=Join Game
        //TODO: New Game or Join Game?
        if(g==0){
            int np=0; //Number of players
            int gt=0; //Game Type
            String nick="AO";
            //TODO: Nickname request
            //TODO: Game Type request
            //TODO: Request of number of players

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
}
