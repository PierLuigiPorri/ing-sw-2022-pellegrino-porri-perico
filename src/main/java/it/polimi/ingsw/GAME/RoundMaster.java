package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class RoundMaster{
    private int roundCount;
    public Round round;
    private ArrayList<Player> players;
    public final ArrayList<Player> initialOrder;

    public RoundMaster(ArrayList<Player> players) {
        this.roundCount = 0;
        this.initialOrder =players;
        round = new Round(players);
    }

   private void startRound() {round = new Round(players);}

    public ArrayList<Player> changePhase(int[] index) {  //restituisce l'ordine di gioco della fase successiva.
        if (this.round.getCurrentPhase().equals("Pianificazione")) {
            players = round.nextAzione(index);
            round.setCurrentPhase("Azione");
        } else {
            roundCount++;
            players.remove(players.size()-1);
            players.remove(players.size()-1);
            if(!players.isEmpty())
                players.remove(0);
            players.add(round.nextPianificazione(index));
            for (int i=0; i< players.size(); i++) {
                if(!players.get(0).equals(initialOrder.get(i)))
                    players.add(initialOrder.get(i));
            }
            startRound();
        }
        return players;
    }


    public int getRoundCount() {
        return roundCount;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
