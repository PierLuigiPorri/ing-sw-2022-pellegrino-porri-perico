package it.polimi.ingsw.GAME;

public class Round {
    private final Player[] player;
    private String currentPhase;


    public Round(Player[] players){
        this.player=players;

        this.currentPhase="Pianificazione";
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Player[] nextAzione(int[] index, int k) {
        Card x, y, z, tmp;
        if (player.length == 3) {
            Player[] order = new Player[3];

            if (index == null)
                return order;

            x = player[0].playCard(index[0]);
            y = player[1].playCard(index[1]);
            z = player[2].playCard(index[2]);

            if (index[0] == 0)
                x.setValue(11);
            if (index[1] == 0)
                y.setValue(11);
            if (index[2] == 0)
                z.setValue(11);

            tmp = x.compareTo(y).compareTo(z);
            int i = 0;
            while (!player[i].playCard(index[i]).equals(tmp)) {
                i++;
            }
            order[k] = player[i];
            k++;
            index[i] = 0;
            if (index[0] == 0 && index[1] == 0 && index[2] == 0)
                index = null;

            return nextAzione(index, k);
        }

        else {
            Player[] order = new Player[2];

            if (index == null)
                return order;

            x = player[0].playCard(index[0]);
            y = player[1].playCard(index[1]);

            if (index[0] == 0)
                x.setValue(11);
            if (index[1] == 0)
                y.setValue(11);

            tmp = x.compareTo(y);
            int i = 0;
            while (!player[i].playCard(index[i]).equals(tmp)) {
                i++;
            }
            order[k] = player[i];
            k++;
            index[i] = 0;
            if (index[0] == 0 && index[1] == 0 && index[2] == 0)
                index = null;

            return nextAzione(index, k);
        }
    }

    public Player nextPianificazione(int[] index) { //index:array che contiene "VALORE" delle carte giocate da tutti i giocatori
        if (player.length == 3) {
            Card x, y, z;

            x = player[0].playCard(index[0]);
            y = player[1].playCard(index[1]);
            z = player[2].playCard(index[2]);

            Card tmp = x.compareTo(y).compareTo(z);
            int i = 0;
            while (!player[i].playCard(index[i]).equals(tmp)) {
                i++;
            }
            return player[i];
        }
        else{
            Card x, y;

            x = player[0].playCard(index[0]);
            y = player[1].playCard(index[1]);

            Card tmp = x.compareTo(y);
            int i = 0;
            while (!player[i].playCard(index[i]).equals(tmp)) {
                i++;
            }
            return player[i];
        }
    }
}
