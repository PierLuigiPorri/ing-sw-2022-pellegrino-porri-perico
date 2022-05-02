package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.net.Socket;
import java.util.ArrayList;

public class Controller implements Runnable{
    public Game game;

    @Override
    public void run() {
        /*while(true){
            //Gestione della comunicazione con il client
            //Chiamerà i suoi stessi metodi che rappresentano le azioni che il Player può aver richiesto

            //System.out.println("Partita creata!");
        }*/
        //Game userà il metodo send di questa classe per chiedere cose al giocatore (es: quale colore disattivare per il calcolo dell'influenza)
    }

    public Controller(Game game){
        this.game=game;
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) {
        try {
            game.gateToIsland(name, index, indexIsland, color);
        }catch (BoundException | ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }

    public void gateToHall(String name, String color) {
        try {
            game.gateToHall(name, color);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
        try {
            game.CloudToGate(player, color, sIndex, cIndex);
        }catch (BoundException | ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }

    public void moveMotherNature(int movement) {
        try {
            game.moveMotherNature(movement);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }

    public void playCard(String player, int index){
        try{
            game.playCard(player, index);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }
}
