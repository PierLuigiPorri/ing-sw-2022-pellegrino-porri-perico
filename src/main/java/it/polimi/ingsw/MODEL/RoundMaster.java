package it.polimi.ingsw.MODEL;

public class RoundMaster {
    private int roundCount;
    Round round;
    private Player[] players;

    public RoundMaster(Player[] players){
        round=new Round(players);
    }

    private void startRound(){
        round=new Round(players);
    }

    public Player[] changePhase(int[] index){  //restituisce l'ordine di gioco della fase successiva.
        if(this.round.getCurrentPhase().equals("Pianificazione")) {
            players = round.nextAzione(index);
            round.setCurrentPhase("Azione");
        }
        else{
            players[0]=endRound(index);
        }
        return players;
    }

    private Player endRound(int[] index){
        roundCount++;
        Player p=round.nextPianificazione(index);
        startRound();
        return p;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public Player[] getPlayers() {
        return players;
    }
}
