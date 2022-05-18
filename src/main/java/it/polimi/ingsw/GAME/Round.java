package it.polimi.ingsw.GAME;

import java.util.ArrayList;
import java.util.Comparator;

public class Round {
    private final ArrayList<Player> player;
    private ArrayList<Player> order;
    private String currentPhase;


    public Round(ArrayList<Player> players){
        order=new ArrayList<>();
        this.player=new ArrayList<>();
        int i=0;
        while(i<players.size()) {
            this.player.add(players.get(i));
            i++;
        }

        this.currentPhase="Planning";
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public ArrayList<Player> nextAction(int[] index) {
        if (player.size() == 3) {
            this.order.addAll(this.player);
            order.sort(Comparator.comparingInt(o -> o.getLastCardPlayed().getValue()));
        }
        else {
            Card x, y;
            x = player.get(0).getLastCardPlayed();
            y = player.get(1).getLastCardPlayed();
            compare(x, y);
        }
        return this.order;
    }

    public ArrayList<Player> nextPlanning(int[] index) { //index:array che contiene "VALORE" delle carte giocate da tutti i giocatori
        if (player.size() == 3) {
            this.order.addAll(this.player);
            order.sort(Comparator.comparingInt(o -> o.getLastCardPlayed().getValue()));
        }
        else{
            Card x, y;
            x = player.get(0).playCard(index[0]);
            y = player.get(1).playCard(index[1]);
            compare(x, y);
        }
        return this.order;
    }

    private void compare(Card x, Card y) {
        int i = Math.max(x.getValue(), y.getValue());
        if (i == x.getValue())
            this.order.add(player.get(0));
        else this.order.add(player.get(1));
        if(this.order.contains(player.get(0)))
            this.order.add(player.get(1));
        else this.order.add(player.get(0));
    }
}
