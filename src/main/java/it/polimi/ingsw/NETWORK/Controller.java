package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.Game;

import java.net.Socket;
import java.util.ArrayList;

public class Controller{
    private Game game;
    private ArrayList<MessageHandler> messageHandlers; //Potranno essere usati per rispondere alle loro stesse richieste


    public Controller(Game game, MessageHandler mh1, MessageHandler mh2, MessageHandler mh3){
        this.game=game;
        messageHandlers=new ArrayList<>();
        messageHandlers.add(mh1); //Index 0
        messageHandlers.add(mh2); //Index 1
        if(game.getPlayerCount()==3){
            messageHandlers.add(mh3); //Index 2
        }
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
