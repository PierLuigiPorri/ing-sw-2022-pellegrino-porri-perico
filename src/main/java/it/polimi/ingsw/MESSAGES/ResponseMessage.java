package it.polimi.ingsw.MESSAGES;

public class ResponseMessage extends MessageType{

    public int ok; //0: ok, 1: fail
    public int gameid;
    public String illegalNick1;
    public String illegalNick2;

    public ResponseMessage(){
        super(2);
    }
}
