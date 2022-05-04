package it.polimi.ingsw.NETWORK;

import java.util.ArrayList;

public class ActionMessage implements MessageType {
    int id;                             //id of the action taken
    ArrayList<Integer> intParameters;  //necessary int parameters
    ArrayList<String> stringParameters; //necessary String parameters

    public ActionMessage(int id, ArrayList<Integer> integ, ArrayList<String> str){
        this.id=id;
        this.intParameters=integ;
        this.stringParameters=str;
    }
}
