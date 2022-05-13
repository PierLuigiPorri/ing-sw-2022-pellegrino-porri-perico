package it.polimi.ingsw.SERVER;

import java.util.ArrayList;

public class SingletonGames {
    //Tentativo (per ora miseramente fallito) di usare il Singleton

    private static SingletonGames instance=null;
    public ArrayList<Creation> games;

    private SingletonGames(){
        games=new ArrayList<>();
    }

    public static synchronized SingletonGames getGames(){
        if(instance.equals(null))
            instance=new SingletonGames();
        return instance;
    }
}
