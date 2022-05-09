package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.NickException;
import it.polimi.ingsw.EXCEPTIONS.NoGamesException;
import it.polimi.ingsw.EXCEPTIONS.NoSuchGameException;
import it.polimi.ingsw.GAME.Game;

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
            //TODO: Tradurre questo for each in un while
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
            //TODO: Tradurre questo for each in un while
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

    public void joinGame(int id, String nick, MsgHandler mh) throws NickException {
        //Attenzione: l'ID non viene direttamente dal Client, bensì da msgHandler
        Creation temp=null;
        synchronized (games) {
            //TODO: Tradurre questo for each in un while
            for (Creation game :
                    games) {
                if (game.getId()==id) {
                    if (game.getnReady()==1){
                        if(!nick.equals(game.getNick1())){
                            game.setNick2(nick);
                            game.setMh2(mh);
                            game.setnReady(); //nReady++;
                        }
                        else throw new NickException("This nickname is already used in the game");
                    }
                    else if(game.getnReady()==2){
                        if(!nick.equals(game.getNick1()) && !nick.equals(game.getNick2())){
                            game.setNick3(nick);
                            game.setMh3(mh);
                            game.setnReady(); //nReady++;
                            temp=game;
                            //TODO: Rimuovere da games
                        }
                        else throw new NickException("This nickname is already used in the game");
                    }
                }
            }
        }
        if(!temp.equals(null)){
            Game g=new Game(temp.getnPlayers(), temp.getGametype(), temp.getNick1(), temp.getMh1(), temp.getNick2(), temp.getMh2(), temp.getNick3(), temp.getMh3());
        }
    }
}
