package it.polimi.ingsw.GAME;

import java.net.Socket;

public class Creation {
    private int nPlayers;
    private int gametype; //0: simplified rules, 1: expert rules
    private int nJoined;
    private String nick1;
    private String nick2=null;
    private String nick3=null;
    private Socket sock1;
    private Socket sock2=null;
    private Socket sock3=null;

    public Creation(int ng, int gt, String nick, Socket sock){
        nPlayers =ng;
        gametype=gt;
        nJoined =1;
        nick1=nick;
        sock1=sock;
    }

    public void setnJoined() {
        this.nJoined++;
    }

    public void setNick2(String nick) {
        this.nick2 = nick;
    }

    public void setNick3(String nick) {
        this.nick3 = nick;
    }

    public int getnPlayers(){
        return nPlayers;
    }

    public int getGametype() {
        return gametype;
    }

    public int getnJoined() {
        return nJoined;
    }

    public String getNick1() {
        return nick1;
    }

    public String getNick2() {
        return nick2;
    }

    public String getNick3() {
        return nick3;
    }

    public Socket getSock1() {
        return sock1;
    }

    public Socket getSock2() {
        return sock2;
    }

    public Socket getSock3() {
        return sock3;
    }

    public void setSock2(Socket sock) {
        this.sock2 = sock;
    }

    public void setSock3(Socket sock) {
        this.sock3 = sock;
    }
}
