package it.polimi.ingsw.GAME;


import java.util.Objects;

public class Hall {
    private int red;
    private int blue;
    private int green;
    private int pink;
    private int yellow;
    //private final Player player;
    public boolean cardActivated=false;

    public Hall() {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.pink = 0;
        this.yellow = 0;
    }

    public void setColor(String color){
        if (Objects.equals(color, "RED")) {
            setRed();
        }
        else if (Objects.equals(color, "BLUE")) {
            setBlue();
           }
        else if (Objects.equals(color, "GREEN")) {
            setGreen();
        }
        else if (Objects.equals(color, "YELLOW")) {
            setYellow();
        }
        else {
            setPink();
            }
    }

    public void desetColor(String color){
        if (Objects.equals(color, "RED")) {
            desetRed();
        }
        else if (Objects.equals(color, "BLUE")) {
            desetBlue();
        }
        else if (Objects.equals(color, "GREEN")) {
            desetGreen();
        }
        else if (Objects.equals(color, "YELLOW")) {
            desetYellow();
        }
        else {
            desetPink();
        }
    }

    public int getColor(String color){
        if (color.equals("RED")) {
            return getRed();
        }
        else if (color.equals( "BLUE")) {
            return getBlue();
        }
        else if (color.equals("GREEN")) {
            return getGreen();
        }
        else if (color.equals("YELLOW")) {
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

    public boolean getCardState(){
        return cardActivated;
    }
}
