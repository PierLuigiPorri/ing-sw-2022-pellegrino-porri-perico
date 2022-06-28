package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;

/**
 * An ActionMessage represents a Client request that takes place during the game
 * @author GC56
 */
public class ActionMessage extends MessageType {
    public final int ActionType;
    public final ArrayList<Integer> intParam;
    public final ArrayList<Integer> intParam2;
    public final ArrayList<String> strParam;

    public ActionMessage(ArrayList<Integer> integ, ArrayList<String> str, ArrayList<Integer> sec, int type){
        super(3);
        this.intParam=integ;
        this.strParam=str;
        this.ActionType=type;
        this.intParam2=sec;
    }
}
