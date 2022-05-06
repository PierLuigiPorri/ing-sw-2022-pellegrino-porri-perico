package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.NoGamesException;
import it.polimi.ingsw.EXCEPTIONS.NoSuchGameException;

import java.util.ArrayList;

public class Starter{

    private static ArrayList<Creation> games =new ArrayList<>(); //List of all joinable games
    private static Integer currID=0;

    public void newGame(int gt, int np, String nick, MsgHandler mh){
        //Creation phase of the game
        synchronized (games) {
            synchronized (currID) {
                games.add(new Creation(currID, np, gt, nick, mh));
                currID++;
            }
        }
    }

    public int joinRandomGame() throws NoGamesException {
        //Returns the ID of the joined game
        synchronized (games){
            for (Creation game:
                    games) {
                if(game.getnJoined()<game.getnPlayers()){
                    //Se ci sono ancora posti liberi in questa partita
                    game.setnJoined(); //nJoined++
                    return game.getId();
                }
            }
        }
        //Se sono arrivato fino a qui significa che non c'è nessuna partita con posti liberi
        throw new NoGamesException("Currently there are no joinable games");
    }
    public void joinGameWithId(int id) throws NoSuchGameException {
        synchronized (games) {
            for (Creation game :
                    games) {
                if (game.getId()==id && game.getnJoined() < game.getnPlayers()) {
                    //Se ci sono ancora posti liberi in questa partita
                    game.setnJoined(); //nJoined++
                }
            }
        }
        //Se sono arrivato fino a qui significa che non c'è nessuna partita con questo ID con posti liberi
        throw new NoSuchGameException("There are no joinable games with this ID");
    }

    public void joinGame(int id, int nick, MsgHandler mh){

    }
}
