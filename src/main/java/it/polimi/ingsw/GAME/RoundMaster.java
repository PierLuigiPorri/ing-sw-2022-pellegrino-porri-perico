package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class RoundMaster{
    private int roundCount;
    public Round round;
    private ArrayList<Player> players;
    public final ArrayList<Player> initialOrder;

    public RoundMaster(ArrayList<Player> players) {
        this.roundCount = 0;
        this.players=players;
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
            players.remove(0);
            players.remove(0);
            if(!players.isEmpty())
                players.remove(0);
            players=round.nextPianificazione(index);

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
