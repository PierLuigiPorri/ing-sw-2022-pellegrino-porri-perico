package it.polimi.ingsw.GAME;

import it.polimi.ingsw.GAME.Creation;
import it.polimi.ingsw.GAME.Game;

import java.util.ArrayList;
import java.net.Socket;

public class Starter implements Runnable{

    private static ArrayList<Creation> partite; //Elenco di tutte le partite in fase di creazione (e quindi joinabili)
    private Socket clientSocket;

    public Starter(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        int g=0;//0=New Game, 1=Join Game
        //TODO:New Game o Join Game?
        if(g==0){
            int ng=0; //Number of players
            int gt=0; //Game Type
            String nick="AO";
            //TODO: Richiesta ngioc, gt e nick

            //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
            partite.add(new Creation(ng, gt, nick, clientSocket));
            //TODO:Va messo in attesa il thread

        }
        else if(g==1){
            int index=0; //Indice della partita che il giocatore vuole joinare
            String nick="AO";
            //TODO:Scelta partita da Joinare

            //TODO:Scelta nick con controllo se il nick è già usato nella partita

            //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
            partite.get(index).setNjoinati(); //njoinati++
            if(partite.get(index).getNjoinati()==2){
                partite.get(index).setNick2(nick);
                partite.get(index).setSock2(clientSocket);
            }
            else if(partite.get(index).getNjoinati()==3){
                partite.get(index).setNick3(nick);
                partite.get(index).setSock3(clientSocket);
            }

            if(partite.get(index).getNgioc()==partite.get(index).getNjoinati()){
                //Creo la partita chiamando la nostra classe Game
                //e passando gli attributi presenti in partite[index] al costruttore giusto
                //usando gli appositi getter presenti in Creation
                Game game=new Game(partite.get(index).getNgioc(), partite.get(index).getGametype(), partite.get(index).getNick1(), partite.get(index).getSock1(), partite.get(index).getNick2(), partite.get(index).getSock2(), partite.get(index).getNick3(), partite.get(index).getSock3());

                //Se va tutto a buon fine:
                //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
                partite.remove(index); //Rimozione della partita da quelle in fase di creazione
            }
        }
    }
}
