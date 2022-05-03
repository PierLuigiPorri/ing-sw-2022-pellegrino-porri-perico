package it.polimi.ingsw.NETWORK;

import java.net.Socket;

public class Creation {
    private int id;
    private int nPlayers;
    private int gametype; //0: simplified rules, 1: expert rules
    private int nJoined;
    private String nick1;
    private String nick2=null;
    private String nick3=null;
    private int actuallyJoined;

    public Creation(int id, int ng, int gt, String nick){
        nPlayers=ng;
        gametype=gt;
        nJoined=1;
        nick1=nick;
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
}
