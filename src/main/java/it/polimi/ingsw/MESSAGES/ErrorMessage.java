package it.polimi.ingsw.MESSAGES;

public class ErrorMessage extends MessageType{
    public String error;

    public ErrorMessage(String s){
        super(6);
        this.error=s;
    }
}
