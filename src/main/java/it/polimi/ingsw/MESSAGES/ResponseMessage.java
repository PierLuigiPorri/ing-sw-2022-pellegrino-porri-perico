package it.polimi.ingsw.MESSAGES;

public class ResponseMessage extends MessageType{
    public String response;
    public boolean allGood; //If true the requested action has been performed, if false it hasn't
    public boolean kill;

    public ResponseMessage(String s, boolean allGood, boolean kill){
        super(2);
        this.response=s;
        this.allGood=allGood;
        this.kill=kill;
    }
}
