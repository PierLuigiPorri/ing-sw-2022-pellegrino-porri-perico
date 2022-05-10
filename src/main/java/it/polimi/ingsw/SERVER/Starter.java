package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.NickException;
import it.polimi.ingsw.EXCEPTIONS.NoSuchGameException;
import it.polimi.ingsw.GAME.Game;

import java.util.ArrayList;

public class Starter{

    private static final ArrayList<Creation> games =new ArrayList<>(); //List of all joinable games
    private static Integer currID=0;
    //public Game createdGame; //Here for testing

    public int newGame(int gt, int np, String nick, MsgHandler mh){
        //Creation phase of the game
        synchronized (games) {
            synchronized (currID) {
                games.add(new Creation(currID, np, gt, nick, mh));
                currID++;
                return currID-1;
            }
        }
    }

    public int joinRandomGame(){
        //Returns the ID of the joined game. In case of problems, it returns -1
        synchronized (games){
            int i=0;
            while(i<games.size()){
                if(games.get(i).getnJoined()==games.get(i).getnPlayers()){
                    //La partita è piena, vado alla prossima
                    i++;
                }
                else{
                    //Ci sono ancora posti liberi in questa partita
                    games.get(i).setnJoined(); //nJoined++
                    return games.get(i).getId();
                }
            }
        }
        //Se sono arrivato fino a qui significa che non c'è nessuna partita con posti liberi
        return -1;
    }
    public int joinGameWithId(int id){
        //Returns the ID of the joined game. In case of problems, it returns -1
        synchronized (games) {
            int i=0;
            while(i<games.size()){
                if(id==games.get(i).getId()){
                    if(games.get(i).getnJoined() < games.get(i).getnPlayers()){
                        games.get(i).setnJoined(); //nJoined++
                        return id;
                    }
                    else return -1;
                }
                else{
                    i++;
                }
            }
        }
        //Se sono arrivato fino a qui significa che non c'è nessuna partita con questo ID
        return -1;
    }

    public void joinGame(int id, String nick, MsgHandler mh) throws NickException, NoSuchGameException {
        //Attenzione: l'ID non viene direttamente dal Client, bensì da msgHandler
        //Questo impedisce all'errore NoSuchGame di verificarsi
        Creation temp=null;
        synchronized (games) {
            int i=0;
            int found=0; //Used as a bool
            while(i<games.size() && found==0){
                if(id==games.get(i).getId()){
                    found=1;
                    if (games.get(i).getnReady()==1){
                        if(!nick.equals(games.get(i).getNick1())){
                            games.get(i).setNick2(nick);
                            games.get(i).setMh2(mh);
                            games.get(i).setnReady(); //nReady++;
                        }
                        else throw new NickException("This nickname is already used in the game");
                    }
                    else if(games.get(i).getnReady()==2){
                        if(!nick.equals(games.get(i).getNick1()) && !nick.equals(games.get(i).getNick2())){
                            games.get(i).setNick3(nick);
                            games.get(i).setMh3(mh);
                            games.get(i).setnReady(); //nReady++;
                        }
                        else throw new NickException("This nickname is already used in the game");
                    }

                    if(games.get(i).getnReady()==games.get(i).getnPlayers()){
                        temp=games.get(i);
                        games.remove(i);
                    }
                }
                else{
                    i++;
                }
            }
        }
        //if(temp!=null){
        Game g=new Game(temp.getnPlayers(), temp.getGametype(), temp.getNick1(), temp.getMh1(), temp.getNick2(), temp.getMh2(), temp.getNick3(), temp.getMh3());
        //}
        //else throw new NoSuchGameException("There's no game with this ID");
    }
}
