package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.Game;

public class Controller implements Runnable{
    private Game partita;
    private int numPlayers;
    private int gameType;
    private String nick1;
    private String nick2;
    private String nick3;
    private String nick4;
    //probabilmente qui ci saranno gli IP/socket

    public void run(){
        //gestione connessioni successive alla prima
        partita=new Game(numPlayers, gameType, nick1, nick2, nick3, nick4); //model
    };
}
