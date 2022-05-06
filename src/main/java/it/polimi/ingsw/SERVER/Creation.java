package it.polimi.ingsw.SERVER;

public class Creation {
    private int id;
    private int nPlayers;
    private int gametype; //0: simplified rules, 1: expert rules
    private int nJoined;
    private int nReady;
    private String nick1;
    private String nick2=null;
    private String nick3=null;
    private MsgHandler mh1;
    private MsgHandler mh2;
    private MsgHandler mh3;

    public Creation(int id, int ng, int gt, String nick, MsgHandler mh){
        nPlayers=ng;
        gametype=gt;
        nJoined=1;
        nick1=nick;
        nReady=1;
        mh1=mh;
    }

    public void setnJoined() {
        this.nJoined++;
    }

    public void setnReady (){
        this.nReady++;
    }
    public void setNick2(String nick) {
        this.nick2 = nick;
    }

    public void setNick3(String nick) {
        this.nick3 = nick;
    }

    public void setMh2(MsgHandler mh2) {
        this.mh2 = mh2;
    }

    public void setMh3(MsgHandler mh3) {
        this.mh3 = mh3;
    }

    public int getId() {
        return id;
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

    public int getnReady() {
        return nReady;
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
