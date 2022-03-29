package it.polimi.ingsw.MODEL;

public class Round {
    private final Player[] player;
    private String currentPhase;


    public Round(Player[] players){
        this.player=new Player[4];
        this.player[0]=players[0];
        this.player[1]=players[1];
        this.player[2]=players[2];
        this.player[3]=players[3];

        this.currentPhase="Pianificazione";
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Player[] nextAzione(int[] index){
        Card x, y, z, w, tmp;
        Player[] order= new Player[4];
        int k=0;

        if (index==null)
            return order;

        x=player[0].playCard(index[0]);
        y=player[1].playCard(index[1]);
        z=player[2].playCard(index[2]);
        w=player[3].playCard(index[3]);

        if (index[0]==0)
            x.setValue(11);
        if (index[1]==0)
            y.setValue(11);
        if (index[2]==0)
            z.setValue(11);
        if (index[3]==0)
            w.setValue(11);

        tmp=x.compareTo(y).compareTo(w).compareTo(z);
        int i=0;
        while (!player[i].playCard(index[i]).equals(tmp)){
            i++;
        }
        order[k]=player[i];
        k++;
        index[i]=0;
        if (index[0]==0 && index[1]==0 && index[2]==0 && index[3]==0)
            index=null;

        return nextAzione(index);
    }

    public Player nextPianificazione(int[] index){ //index:array che contiene "VALORE" delle carte giocate da tutti i giocatori
        Card x, y, z, w;

        x=player[0].playCard(index[0]);
        y=player[1].playCard(index[1]);
        z=player[2].playCard(index[2]);
        w=player[3].playCard(index[3]);

        Card tmp= x.compareTo(y).compareTo(w).compareTo(z);
        int i=0;
        while (!player[i].playCard(index[i]).equals(tmp)){
            i++;
        }
        return player[i];
    }

}
