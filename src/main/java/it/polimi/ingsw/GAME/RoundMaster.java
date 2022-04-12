package it.polimi.ingsw.GAME;

import java.util.ArrayList;

public class RoundMaster{
    private int roundCount;
    public Round round;
    private ArrayList<Player> players;

    public RoundMaster(ArrayList<Player> players) {
        this.roundCount = 0;
        round = new Round(players);
    }

    private void startRound() {
        round = new Round(players);
    }

    public ArrayList<Player> changePhase(int[] index) {  //restituisce l'ordine di gioco della fase successiva.
        if (this.round.getCurrentPhase().equals("Pianificazione")) {
            players = round.nextAzione(index);
            round.setCurrentPhase("Azione");
        } else {
            players.add(endRound(index));
        }
        return players;
    }

    private Player endRound(int[] index) {
        roundCount++;
        Player p = round.nextPianificazione(index);
        startRound();
        return p;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
