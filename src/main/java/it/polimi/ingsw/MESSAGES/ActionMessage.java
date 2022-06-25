package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;

/**
 * An ActionMessage represents a Client request that takes place during the game
 * @author GC56
 */
public class ActionMessage extends MessageType {
    public int ActionType;
    public ArrayList<Integer> intParam, intParam2;
    public ArrayList<String> strParam;

    public ActionMessage(ArrayList<Integer> integ, ArrayList<String> str, ArrayList<Integer> sec, int type){
        super(3);
        this.intParam=integ;
        this.strParam=str;
        this.ActionType=type;
        this.intParam2=sec;
    }
}
