package it.polimi.ingsw.MODEL;

public class MadreNatura {
    public Isola isola;


    public MadreNatura(Isola isola){
        this.isola=isola;
        isola.studenti=null;
        isola.madreNatura=true;
    }

    public Isola getIsola() {
        return isola;
    }

    public void setIsola(Isola isola) {
        this.isola = isola;
    }
}
