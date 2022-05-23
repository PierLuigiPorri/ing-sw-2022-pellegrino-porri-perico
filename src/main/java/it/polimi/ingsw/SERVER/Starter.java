package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.EXCEPTIONS.NickException;
import it.polimi.ingsw.EXCEPTIONS.NoSuchGameException;
import it.polimi.ingsw.GAME.Game;

import java.util.ArrayList;

public class Starter{

    private static final ArrayList<Creation> games =new ArrayList<>(); //List of all joinable games
    private static Integer currID=0;

    private ConnectionManager cm;

    public Starter(ConnectionManager cm){
        this.cm=cm;
    }

    public int newGame(int gt, int np, String nick) throws ImpossibleActionException{
        //Creation phase of the game
        synchronized (games) {
            if((np==2 || np==3) && (gt==0 || gt==1)){
                games.add(new Creation(currID, np, gt, nick, cm));
                currID++;
                return currID-1;
            }
            else throw new ImpossibleActionException("Illegal action");
        }
    }

    public int joinGameWithId(int id, String nick) throws NoSuchGameException, NickException{
        synchronized (games){
            int i=0;
            while(i<games.size()){
                if(id==games.get(i).getId()){
                    //Ho trovato la partita con questo ID
                    if(games.get(i).getnJoined() < games.get(i).getnPlayers()){
                        //Ci sono posti liberi
                        int np=games.get(i).getnPlayers();
                        if(np==2){
                            if(!games.get(i).getNick1().equals(nick)) {
                                //Add the 2nd player and start the game
                                games.get(i).setnJoined(); //nJoined++
                                games.get(i).setNick2(nick);
                                games.get(i).setCm2(cm);
                                return startGame(games.remove(i));
                            }
                            else throw new NickException("Your nickname is already in use in the selected game");
                        }
                        else if(np==3){
                            if(games.get(i).getnJoined()==1){
                                if(!games.get(i).getNick1().equals(nick)) {
                                    //Add the 2nd player
                                    games.get(i).setnJoined(); //nJoined++
                                    games.get(i).setNick2(nick);
                                    games.get(i).setCm2(cm);
                                }
                                else throw new NickException("Your nickname is already in use in the selected game");

                            }
                            else {
                                if(!games.get(i).getNick1().equals(nick) && !games.get(i).getNick2().equals(nick)) {
                                    //Add the 3rd player and start the game
                                    games.get(i).setnJoined(); //nJoined++
                                    games.get(i).setNick3(nick);
                                    games.get(i).setCm3(cm);
                                    return startGame(games.remove(i));
                                }
                                else throw new NickException("Your nickname is already in use in the selected game");
                            }
                        }
                    }
                    else throw new NoSuchGameException("There are no joinable games with this ID");
                }
                else{
                    i++;
                }
            }
            throw new NoSuchGameException("There are no joinable games with this ID");
        }
    }

    private int startGame(Creation temp){
        //Returns the ID of the created game
        GameManager gm=new GameManager(temp.getCm1(), temp.getCm2(), temp.getCm3(), temp.getnPlayers());
        Game g = new Game(temp.getnPlayers(), temp.getGametype(), temp.getNick1(), temp.getNick2(), temp.getNick3(), gm);
        Controller c=new Controller(g, temp.getCm1(), temp.getCm2(), temp.getCm3());
        g.getModelView().addObserver(gm); //La virtual view osserva il modello
        gm.addObserver(c); //Il controller osserverà la virtual view
        //Setting player nicknames in every Connection Manager
        temp.getCm1().setPlayerName(temp.getNick1());
        temp.getCm2().setPlayerName(temp.getNick2());
        if(temp.getnPlayers()==3)
            temp.getCm3().setPlayerName(temp.getNick3());
        new Thread(gm).start(); //Inizio a processare gli actionMessage
        return temp.getId();
    }

    public int joinRandomGame(String nick) throws NoSuchGameException{
        //Returns the ID of the joined game. In case of problems, it returns -1
        System.out.println("Ue");
        synchronized (games){
            int i=0;
            while(i<games.size()){
                if(games.get(i).getnJoined()==games.get(i).getnPlayers()){
                    //La partita è piena, vado alla prossima
                    i++;
                }
                else{
                    //Ci sono ancora posti liberi in questa partita
                    int np=games.get(i).getnPlayers();
                    if(np==2){
                        if(!games.get(i).getNick1().equals(nick)) {
                            //Add the 2nd player and start the game
                            games.get(i).setnJoined(); //nJoined++
                            games.get(i).setNick2(nick);
                            games.get(i).setCm2(cm);
                            return startGame(games.remove(i));
                        }
                        else i++;
                    }
                    else if(np==3){
                        if(games.get(i).getnJoined()==1){
                            if(!games.get(i).getNick1().equals(nick)) {
                                //Add the 2nd player
                                games.get(i).setnJoined(); //nJoined++
                                games.get(i).setNick2(nick);
                                games.get(i).setCm2(cm);
                                return games.get(i).getId();
                            }
                            else i++;

                        }
                        else {
                            if(!games.get(i).getNick1().equals(nick) && !games.get(i).getNick2().equals(nick)) {
                                //Add the 3rd player and start the game
                                games.get(i).setnJoined(); //nJoined++
                                games.get(i).setNick3(nick);
                                games.get(i).setCm3(cm);
                                return startGame(games.remove(i));
                            }
                            else i++;
                        }
                    }
                }
            }
        }
        throw new NoSuchGameException("There are no joinable games, at least with your current nickname");
    }

    public void killGame(int id){
        //TODO: kill the game during the creation phase by removing it from games after having sent a message to all clients
    }

}
