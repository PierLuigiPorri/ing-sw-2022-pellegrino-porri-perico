package it.polimi.ingsw.CLIENT;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.FutureTask;

public class CLI implements View{

    private final PrintStream out;
    private Thread threadInput;

    private FutureTask<String> futureTask;

    public CLI(){
        out=System.out;
    }

    public void initCLI(){
        //futureTask= new FutureTask<>( INPUT );
        threadInput=new Thread(futureTask);
        threadInput.start();

    }


    public void moveMotherNature() {
        int islandId;

    }

    public void changePhase() {

    }

    public void gateToIsland() {

    }

    public void gateToHall() {

    }

    public void cloudToGate() {

    }

    public void playCard() {

    }

}
