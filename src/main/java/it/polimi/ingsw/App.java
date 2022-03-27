package it.polimi.ingsw;

import it.polimi.ingsw.CONTROLLER.Controller;

public class App
{
    public static void main( String[] args )
    {
        //New Game o Join Game?

    }
    public void newGame(){ //probabilmente prenderà come param l'IP del giocatore o qualcosa di simile
        Controller control= new Controller();//eventualmente IP passato come param
        Thread t=new Thread(control);
        t.start();
    }
}
