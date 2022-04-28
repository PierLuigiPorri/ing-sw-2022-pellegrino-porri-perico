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

        this.currentPhase="Pianificazione";
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public ArrayList<Player> nextAzione(int[] index) {
        Card x, y, z;
        ArrayList<Player> order = new ArrayList<>();

        if (player.size() == 3) {

            if (index == null)
                return order;

            x = player.get(0).getLastCardPlayed();
            y = player.get(1).getLastCardPlayed();
            z = player.get(2).getLastCardPlayed();

            int i = Math.max(Math.max(x.getValue(), y.getValue()), z.getValue());
            if (i == x.getValue())
                this.order.add(player.get(0));
            else if (i == y.getValue())
                this.order.add(player.get(1));
            else
                this.order.add(player.get(2));
            order.addAll(player);
            order.sort((o1, o2) -> {
                if(o1.getLastCardPlayed().getValue()==o2.getLastCardPlayed().getValue()){
                    return 0;
                }
                else return 1;
            });

            return this.order;
        }

        else {

            x = player.get(0).getLastCardPlayed();
            y = player.get(1).getLastCardPlayed();

            int i = Math.max(x.getValue(), y.getValue());

            if (i == x.getValue())
                this.order.add(player.get(0));
            else this.order.add(player.get(1));

            if(this.order.contains(player.get(0)))
                this.order.add(player.get(1));
            else this.order.add(player.get(0));

            /*order.add(player.get(0));
            order.add(player.get(1));
            order.sort((o1, o2) -> Integer.compare(o2.getLastCardPlayed().getValue(), o1.getLastCardPlayed().getValue()));
            */
            return this.order;

        }
    }

    public ArrayList<Player> nextPianificazione(int[] index) { //index:array che contiene "VALORE" delle carte giocate da tutti i giocatori
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
            return this.order;
        }
        else{
            Card x, y;

            x = player.get(0).playCard(index[0]);
            y = player.get(1).playCard(index[1]);

            int i= Math.max(x.getValue(), y.getValue());

            if (i == x.getValue())
                this.order.add(player.get(0));
            else this.order.add(player.get(1));
            if(this.order.contains(player.get(0)))
                this.order.add(player.get(1));
            else this.order.add(player.get(0));

            return this.order;
        }
    }
}
