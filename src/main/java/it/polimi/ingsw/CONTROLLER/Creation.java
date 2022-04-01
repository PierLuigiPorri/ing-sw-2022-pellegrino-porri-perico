package it.polimi.ingsw.CONTROLLER;

public class Creation {
    private int ngioc;
    private int gametype; //0: regole semplificate, 1: regole esperto
    private int njoinati;
    private String nick1;
    private String nick2=null;
    private String nick3=null;
    //private String nick4=null;

    public Creation(int ng, int gt, String nick){
        ngioc=ng;
        gametype=gt;
        njoinati=1;
        nick1=nick;
    }

    public void setNjoinati() {
        this.njoinati++;
    }

    public void setNick2(String nick) {
        this.nick2 = nick;
    }

    public void setNick3(String nick) {
        this.nick3 = nick;
    }

    public void setNick4(String nick) {
        this.nick4 = nick;
    }

    public int getNgioc(){
        return ngioc;
    }

    public int getGametype() {
        return gametype;
    }

    public int getNjoinati() {
        return njoinati;
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

    public String getNick4() {
        return nick4;
    }
}
