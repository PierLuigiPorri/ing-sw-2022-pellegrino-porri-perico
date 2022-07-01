package it.polimi.ingsw.SERVER;

/**
 * This class represents a game while in the creation phase.
 * @author GC56
 */
public class Creation {
    private final int id;
    private final int nPlayers;
    private final int gametype; //0: simplified rules, 1: expert rules
    private int nJoined;
    private final String nick1;
    private String nick2=null;
    private String nick3=null;
    private final ConnectionManager cm1;
    private ConnectionManager cm2;
    private ConnectionManager cm3;

    /**
     * Constructor
     * @param id The ID of the game
     * @param np Number of Players
     * @param gt GameType: 0 -> normal game, 1 -> expert game
     * @param nick Nickname of the first player
     * @param cm ConnectionManager of the first player
     * @author GC56
     */
    public Creation(int id, int np, int gt, String nick, ConnectionManager cm){
        nPlayers=np;
        gametype=gt;
        nJoined=1;
        nick1=nick;
        cm1=cm;
        this.id=id;
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

    public void setCm2(ConnectionManager cm2) {
        this.cm2 = cm2;
    }

    public void setCm3(ConnectionManager cm3) {
        this.cm3 = cm3;
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

    public String getNick1() {
        return nick1;
    }

    public String getNick2() {
        return nick2;
    }

    public String getNick3() {
        return nick3;
    }

    public ConnectionManager getCm1() {
        return cm1;
    }

    public ConnectionManager getCm2() {
        return cm2;
    }

    public ConnectionManager getCm3() {
        return cm3;
    }
}
