package it.polimi.ingsw.GAME;

import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.util.Observable;
import java.util.Observer;

public class ModelView extends Observable implements Observer {
    private final UpdateMessage update;

    public ModelView(){
        this.update=new UpdateMessage();
        //TODO:compilare il update di base
    }
    @Override
    public void update(Observable o, Object arg) {
        //TODO:sovrascrivere tutti i campi del UpdateMessage
        setChanged();
        notifyObservers(update);
    }
}
