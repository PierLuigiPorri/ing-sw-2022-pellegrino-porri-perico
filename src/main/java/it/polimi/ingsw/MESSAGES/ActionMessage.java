package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;

public class ActionMessage extends MessageType {
    public int ActionType;
    public ArrayList<Integer> intParam;
    public ArrayList<String> strParam;

    public ActionMessage(ArrayList<Integer> integ, ArrayList<String> str){
        super(3);
        this.intParam=integ;
        this.strParam=str;
    }
}
