package it.polimi.ingsw.MODEL;

public class RoundMaster {
    private int roundCount;
    Round round;
    private Player[] players;

    public RoundMaster(Player[] players){
        round=new Round(players);
    }

    public void startRound(){
        round=new Round(players);
    }

    public Player[] changePhase(int[] index){
        players= round.nextAzione(index);
        round.setCurrentPhase("Azione");
        return players;
    }

    public void endRound(){
        roundCount++;
        startRound();
    }

    public int getRoundCount() {
        return roundCount;
    }

    public Player[] getPlayers() {
        return players;
    }
}
