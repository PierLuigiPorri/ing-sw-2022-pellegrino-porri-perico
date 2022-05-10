package it.polimi.ingsw.SERVER;

import it.polimi.ingsw.MESSAGES.*;

public class MessageReader {
    public int handle(MessageType message){
        System.out.println("greve");
        return 0;
    }

    public int handle(AckMessage message){
        return 1;
        //TODO:sto coso dovrebbe resettare il countdown, che però va tenuto in un altro thread in qualche modo
    }

    public int handle(ActionMessage message){
        return 1;
    }

    private int handle(CreationMessage message){
        return 1;
    }
}
