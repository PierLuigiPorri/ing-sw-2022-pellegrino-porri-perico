package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;

public class ActionMessage extends MessageType {
    int id;                             //id of the action taken
    ArrayList<Integer> intParameters;  //necessary int parameters
    ArrayList<String> stringParameters; //necessary String parameters

    public ActionMessage(int id, ArrayList<Integer> integ, ArrayList<String> str){
        this.id=id;
        this.intParameters=integ;
        this.stringParameters=str;
    }
}
