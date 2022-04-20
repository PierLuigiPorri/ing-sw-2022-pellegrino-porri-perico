package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class Round {
    private final ArrayList<Player> player;
    private String currentPhase;


    public Round(ArrayList<Player> players){
        this.player=new ArrayList<>();
        int i=0;
        while(i<players.size()) {
            this.player.add(players.get(i));
            i++;
        }

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
        ArrayList<Player> order = new ArrayList<>();

        if (player.size() == 3) {

            if (index == null)
                return order;

            x = player.get(0).getLastCardPlayed();
            y = player.get(1).getLastCardPlayed();
            z = player.get(2).getLastCardPlayed();

            int k=0;
            while (k<3) {
                int i = Math.max(Math.max(x.getValue(), y.getValue()), z.getValue());
                if (i == x.getValue())
                    order.add(player.get(0));
                else if (i == y.getValue())
                    order.add(player.get(1));
                else
                    order.add(player.get(2));
                k++;
            }

            return order;
        }

        else {

            x = player.get(0).getLastCardPlayed();
            y = player.get(1).getLastCardPlayed();

            int k = 0;
            while (k < 3) {
                int i = Math.max(x.getValue(), y.getValue());
                if (i == x.getValue())
                    order.add(player.get(0));
                else order.add(player.get(1));

                k++;
            }
            return order;

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
