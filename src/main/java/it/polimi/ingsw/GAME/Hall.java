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

    public void setColor(String color){
        if (color=="RED") {
            setRed();
        }
        else if (color=="BLUE") {
            setBlue();
           }
        else if (color=="GREEN") {
            setGreen();
        }
        else if (color=="YELLOW") {
            setYellow();
        }
        else {
            setPink();
            }

        //player.getGame().checkColorChanges(cardActivated);
        //TODO: PER DAVIDE: Questa linea di codice non va qui, va in game dove segnalato (metodo addStudentToHall)
    }

    public void desetColor(String color){
        if (color=="RED") {
            desetRed();
        }
        else if (color=="BLUE") {
            desetBlue();
        }
        else if (color=="GREEN") {
            desetGreen();
        }
        else if (color=="YELLOW") {
            desetYellow();
        }
        else {
            desetPink();
        }
    }

    public int getColor(String color){
        if (color=="RED") {
            return getRed();
        }
        else if (color=="BLUE") {
            return getBlue();
        }
        else if (color=="GREEN") {
            return getGreen();
        }
        else if (color=="YELLOW") {
            return getYellow();
        }
        else {
            return getPink();
        }
    }

    private int getRed() {
        return red;
    }

    private void setRed() {
        this.red++;
    }

    private void desetRed(){
        this.red--;
    }

    private int getBlue() {
        return blue;
    }

    private void setBlue() {
        this.blue ++;
    }

    private void desetBlue(){
        this.blue--;
    }

    private int getGreen() {
        return green;
    }

    private void setGreen() {
        this.green++;
    }

    private void desetGreen(){
        this.green--;
    }

    private int getPink() {
        return pink;
    }

    private void setPink() {
        this.pink++;
    }

    private void desetPink(){
        this.pink--;
    }

    private int getYellow() {
        return yellow;
    }

    private void setYellow() {
        this.yellow++;
    }

    private void desetYellow(){
        this.yellow--;
    }

    public void activateCard(){
        this.cardActivated=true;
    }

    public void disableCard(){
        this.cardActivated=false;
    }
}
