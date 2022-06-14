package it.polimi.ingsw.MESSAGES;

public class ResponseMessage extends MessageType{
    public String response;
    public boolean allGood; //If true the requested action has been performed, if false it hasn't
    public Integer gameid;
    public boolean gameEnded;

    public ResponseMessage(String s, boolean allGood, Integer gameid, boolean gameEnded){
        super(2);
        this.response=s;
        this.allGood=allGood;
        this.gameid=gameid;
        this.gameEnded=gameEnded;
    }
}
