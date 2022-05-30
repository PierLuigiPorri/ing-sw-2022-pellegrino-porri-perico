package it.polimi.ingsw.EXCEPTIONS;

public class GameException extends Exception{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public GameException(String e){
        super(ANSI_RED+e+ANSI_RESET);
    }
}
