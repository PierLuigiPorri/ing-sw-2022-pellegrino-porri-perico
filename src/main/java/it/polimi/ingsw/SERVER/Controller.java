package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.Game;

import java.util.ArrayList;

public class Controller{
    private final Game game;
    private ArrayList<MsgHandler> messageHandlers; //Potranno essere usati per rispondere alle loro stesse richieste


    public Controller(Game game, MsgHandler mh1, MsgHandler mh2, MsgHandler mh3){
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
            //TODO: getCorrectMh(name).sendMessage(errore di gate to island)
        }
    }

    public void gateToHall(String name, String color) {
        try {
            game.gateToHall(name, color);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
            //TODO: getCorrectMh(name).sendMessage(errore di gate to hall)
        }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) {
        try {
            game.CloudToGate(player, color, sIndex, cIndex);
        }catch (BoundException | ImpossibleActionException e){
            System.out.println(e.getMessage());
            //TODO: getCorrectMh(name).sendMessage(errore di cloud to gate)
        }
    }

    public void moveMotherNature(int movement) {
        try {
            game.moveMotherNature(movement);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
            //TODO: getCorrectMh(name).sendMessage(errore di move mother nature)
        }
    }

    public void playCard(String player, int index){
        try{
            game.playCard(player, index);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
            //TODO: getCorrectMh(name).sendMessage(errore di playCard)
        }
    }

    private MsgHandler getCorrectMh(String name){
        for (MsgHandler mh:
             messageHandlers) {
            if(mh.getPlayerName().equals(name)){
                return mh;
            }
        }
        return null;
    }
}
