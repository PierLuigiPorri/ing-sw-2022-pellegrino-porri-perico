package it.polimi.ingsw.MESSAGES;

public class ResponseMessage extends MessageType{
    public String response;

    public ResponseMessage(String s){
        super(2);
        this.response=s;
    }
}
