package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.NETWORK.Creation;

import java.util.ArrayList;

public class Starter{

    private static ArrayList<Creation> games =new ArrayList<>(); //List of all joinable games
    private static Integer currID=0;

    /*@Override
    public void run() {

        //Wait for New Game or Join Game
        try {
            g = in.readInt();
        }
        catch (Exception e){
            System.out.println("Invalid input");
            //TODO: Kill this thread
        }
        if(g==0){
            NewGame();
        }
        else if(g==1){
            //System.out.println("So 1");
            int index=0; //Indice della partita che il giocatore vuole joinare
            String nick="AO";

            //TODO:Scelta nick con controllo se il nick è già usato nella partita

            synchronized (games) {
                games.get(index).setnJoined(); //njoinati++
                if (games.get(index).getnJoined() == 2) {
                    games.get(index).setNick2(nick);
                    games.get(index).setSock2(clientSocket);
                } else if (games.get(index).getnJoined() == 3) {
                    games.get(index).setNick3(nick);
                    games.get(index).setSock3(clientSocket);
                }

                if (games.get(index).getnPlayers() == games.get(index).getnJoined()) {
                    //Creo la partita chiamando la nostra classe Game
                    //e passando gli attributi presenti in partite[index] al costruttore giusto
                    //usando gli appositi getter presenti in Creation
                    try {
                        Game game = new Game(games.get(index).getnPlayers(), games.get(index).getGametype(), games.get(index).getNick1(), games.get(index).getSock1(), games.get(index).getNick2(), games.get(index).getSock2(), games.get(index).getNick3(), games.get(index).getSock3());
                        //Se va tutto a buon fine:
                        games.remove(index); //Rimozione della partita da quelle in fase di creazione
                    } catch (GameException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }*/

    public void newGame(int gt, int np, String nick){
        //Creation phase of the game
        synchronized (games) {
            games.add(new Creation(currID, np, gt, nick));
            currID++;
        }
    }
    public void joinGame(int id){
        synchronized (games) {
            //
            /*games.get(index).setnJoined(); //njoinati++
            if (games.get(index).getnJoined() == 2) {
                games.get(index).setNick2(nick);
                games.get(index).setSock2(clientSocket);
            } else if (games.get(index).getnJoined() == 3) {
                games.get(index).setNick3(nick);
                games.get(index).setSock3(clientSocket);
            }

            if (games.get(index).getnPlayers() == games.get(index).getnJoined()) {
                //Creo la partita chiamando la nostra classe Game
                //e passando gli attributi presenti in partite[index] al costruttore giusto
                //usando gli appositi getter presenti in Creation
                try {
                    Game game = new Game(games.get(index).getnPlayers(), games.get(index).getGametype(), games.get(index).getNick1(), games.get(index).getSock1(), games.get(index).getNick2(), games.get(index).getSock2(), games.get(index).getNick3(), games.get(index).getSock3());
                    //Se va tutto a buon fine:
                    games.remove(index); //Rimozione della partita da quelle in fase di creazione
                } catch (GameException e) {
                    System.out.println(e.getMessage());
                }*/
            }
    }
}
