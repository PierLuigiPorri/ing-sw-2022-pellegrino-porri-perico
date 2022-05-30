package it.polimi.ingsw.EXCEPTIONS;

public class NickException extends GameException{
    //Thrown when the nickname is not valid, e.g. when you're player2 and the same nickname has been set before you by player3
    public NickException(String e){
        super(e);
    }
}
