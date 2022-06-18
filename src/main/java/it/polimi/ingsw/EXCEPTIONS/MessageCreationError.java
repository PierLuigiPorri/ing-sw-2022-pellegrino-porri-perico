package it.polimi.ingsw.EXCEPTIONS;

public class MessageCreationError extends Exception{
    //Thrown when somebody tries to build a message that isn't allowed
    public MessageCreationError(String e){
        super(e);
    }
}
