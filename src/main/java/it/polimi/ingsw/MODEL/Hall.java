package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

public class Hall {
    private int red;
    private int blue;
    private int green;
    private int pink;
    private int yellow;
    private Player player;

    public Hall(Player p) {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.pink = 0;
        this.yellow = 0;
        this.player= p;
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
    }

    public void desetColor(ColorTracker color) throws ImpossibleActionException {
        if(getColor(color.getColor())>0) {
            if (color.getColor().equals(Color.RED)) {
                this.red--;
            } else if (color.getColor().equals(Color.BLUE)) {
                this.blue--;
            } else if (color.getColor().equals(Color.GREEN)) {
                this.green--;
            } else if (color.getColor().equals(Color.YELLOW)) {
                this.yellow--;
            } else {
                this.pink--;
            }
        }else throw new ImpossibleActionException("Impossible to delete "+color.getColor()+" from Hall, it's alredy zero.\n");
    }

    public int getColor(Color color){
        if (color.equals(Color.RED)) {
            return getRed();
        }
        else if (color.equals(Color.BLUE)) {
            return getBlue();
        }
        else if (color.equals(Color.GREEN)) {
            return getGreen();
        }
        else if (color.equals(Color.YELLOW)) {
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
        if(this.red%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    private int getBlue() {
        return blue;
    }

    private void setBlue() {
        this.blue ++;
        if(this.blue%3==0){
            this.player.addCoin();
        }
    }

    private int getGreen() {
        return green;
    }

    private void setGreen() {
        this.green++;
        if(this.green%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    private int getPink() {
        return pink;
    }

    private void setPink() {
        this.pink++;
        if(this.pink%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    private int getYellow() {
        return yellow;
    }

    private void setYellow() {
        this.yellow++;
        if(this.yellow%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }
}
