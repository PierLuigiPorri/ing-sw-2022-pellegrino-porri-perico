package it.polimi.ingsw.CONTROLLER;

import java.util.ArrayList;
import java.net.*;

public class Controller implements Runnable{

    private static ArrayList<Creation> partite; //Elenco di tutte le partite in fase di creazione (e quindi joinabili)
    private Socket clientSocket;

    public Controller(Socket socket){
        this.clientSocket=socket;
    }

    @Override
    public void run() {
        int g;//0=New Game, 1=Join Game
        //TODO:New Game o Join Game?
        if(g==0){
            int ng; //Number of players
            int gt; //Game Type
            String nick;
            //TODO: Richiesta ngioc, gt e nick

            //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
            partite.add(new Creation(ng, gt, nick));
        }
        else if(g==1){
            int index; //Indice della partita che il giocatore vuole joinare
            String nick;
            //TODO:Scelta partita da Joinare

            //TODO:Scelta nick con controllo se il nick è già usato nella partita

            //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
            partite.get(index).setNjoinati(); //njoinati++
            if(partite.get(index).getNjoinati()==2){
                partite.get(index).setNick2(nick);
            }
            else if(partite.get(index).getNjoinati()==3){
                partite.get(index).setNick3(nick);
            }
            else if(partite.get(index).getNjoinati()==4){
                partite.get(index).setNick4(nick);
            }

            if(partite.get(index).getNgioc()==partite.get(index).getNjoinati()){
                //Creo la partita chiamando la nostra classe Game
                //e passando gli attributi presenti in partite[index] al costruttore giusto
                //usando gli appositi getter presenti in Creation

                //Se va tutto a buon fine:
                //TODO: Sincronizzazione per fare in modo che partite sia acceduto da 1 thread per volta
                partite.remove(index); //Rimozione della partita da quelle in fase di creazione
            }
        }
    }
}
