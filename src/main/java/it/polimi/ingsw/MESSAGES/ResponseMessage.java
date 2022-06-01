package it.polimi.ingsw.MESSAGES;

public class ResponseMessage extends MessageType{
    public String response;
    public boolean allGood; //If true the requested action has been performed, if false it hasn't

    public ResponseMessage(String s, boolean allGood){
        super(2);
        this.response=s;
        this.allGood=allGood;
    }
}
