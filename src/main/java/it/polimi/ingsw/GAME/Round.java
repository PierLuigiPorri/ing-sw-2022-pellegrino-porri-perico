package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Round {
    private final ArrayList<Player> player;
    private String currentPhase;


    public Round(ArrayList<Player> players){
        this.player=players;

        this.currentPhase="Pianificazione";
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public ArrayList<Player> nextAzione(int[] index) {
        Card x, y, z, tmp;
        if (player.size() == 3) {
            ArrayList<Player> order = new ArrayList<>();

            if (index == null)
                return order;

            x = player.get(0).playCard(index[0]);
            y = player.get(1).playCard(index[1]);
            z = player.get(2).playCard(index[2]);

            if (index[0] == 0)
                x.setValue(11);
            if (index[1] == 0)
                y.setValue(11);
            if (index[2] == 0)
                z.setValue(11);

            tmp = x.compareTo(y).compareTo(z);
            int i = 0;
            while (!player.get(i).playCard(index[i]).equals(tmp)) {
                i++;
            }
            order.add(player.get(i));
            index[i] = 0;
            if (index[0] == 0 && index[1] == 0 && index[2] == 0)
                index = null;

            return nextAzione(index);
        }

        else {
            ArrayList<Player> order = new ArrayList<>();

            if (index == null)
                return order;

            x = player.get(0).playCard(index[0]);
            y = player.get(0).playCard(index[1]);

            if (index[0] == 0)
                x.setValue(11);
            if (index[1] == 0)
                y.setValue(11);

            tmp = x.compareTo(y);
            int i = 0;
            while (!player.get(i).playCard(index[i]).equals(tmp)) {
                i++;
            }
            order.add(player.get(i));
            index[i] = 0;
            if (index[0] == 0 && index[1] == 0 && index[2] == 0)
                index = null;

            return nextAzione(index);
        }
    }

    public Player nextPianificazione(int[] index) { //index:array che contiene "VALORE" delle carte giocate da tutti i giocatori
        if (player.size() == 3) {
            Card x, y, z;

            x = player.get(0).playCard(index[0]);
            y = player.get(1).playCard(index[1]);
            z = player.get(2).playCard(index[2]);

            Card tmp = x.compareTo(y).compareTo(z);
            int i = 0;
            while (!player.get(i).playCard(index[i]).equals(tmp)) {
                i++;
            }
            return player.get(i);
        }
        else{
            Card x, y;

            x = player.get(0).playCard(index[0]);
            y = player.get(1).playCard(index[1]);

            Card tmp = x.compareTo(y);
            int i = 0;
            while (!player.get(i).playCard(index[i]).equals(tmp)) {
                i++;
            }
            return player.get(i);
        }
    }
}
