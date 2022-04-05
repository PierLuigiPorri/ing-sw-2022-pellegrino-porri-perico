package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

public class Hall {
    private int red;
    private int blue;
    private int green;
    private int pink;
    private int yellow;
    //private final Player player;
    private boolean cardActivated=false;

    public Hall() {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.pink = 0;
        this.yellow = 0;
    }

    public void setColor(ColorTracker color){
        if (color.getColor().equals(Color.RED)) {
            setRed();
        }
        else if (color.getColor().equals(Color.BLUE)) {
            setBlue();
           }
        else if (color.getColor().equals(Color.GREEN)) {
            setGreen();
        }
        else if (color.getColor().equals(Color.YELLOW)) {
            setYellow();
        }
        else {
            setPink();
            }

        //player.getGame().checkColorChanges(cardActivated);
        //TODO: PER DAVIDE: Questa linea di codice non va qui, va in game dove segnalato (metodo addStudentToHall)
    }

    public int getRed() {
        return red;
    }

    private void setRed() {
        this.red++;
    }

    public void desetRed(){
        this.red--;
    }

    public int getBlue() {
        return blue;
    }

    private void setBlue() {
        this.blue ++;
    }

    public void desetBlue(){
        this.blue--;
    }

    public int getGreen() {
        return green;
    }

    private void setGreen() {
        this.green++;
    }

    public void desetGreen(){
        this.green--;
    }

    public int getPink() {
        return pink;
    }

    private void setPink() {
        this.pink++;
    }

    public void desetPink(){
        this.pink--;
    }

    public int getYellow() {
        return yellow;
    }

    private void setYellow() {
        this.yellow++;
    }

    public void desetYellow(){
        this.yellow--;
    }

    public void activateCard(){
        this.cardActivated=true;
    }

    public void disableCard(){
        this.cardActivated=false;
    }
}
