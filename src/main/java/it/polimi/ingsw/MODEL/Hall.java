package it.polimi.ingsw.MODEL;

public class Hall {
    private int red;
    private int blue;
    private int green;
    private int pink;
    private int yellow;

    public Hall() {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.pink = 0;
        this.yellow = 0;
    }

    public int getRed() {
        return red;
    }

    public void setRed() {
        this.red++;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue() {
        this.blue ++;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen() {
        this.green++;
    }

    public int getPink() {
        return pink;
    }

    public void setPink() {
        this.pink++;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow() {
        this.yellow++;
    }
}
