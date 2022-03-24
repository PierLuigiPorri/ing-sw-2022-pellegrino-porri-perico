package it.polimi.ingsw.MODEL;

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

    public int getRed() {
        return red;
    }

    public void setRed() {
        this.red++;
        if(this.red%3==0){
            this.player.addCoin();
        }
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue() {
        this.blue ++;
        if(this.blue%3==0){
            this.player.addCoin();
        }
    }

    public int getGreen() {
        return green;
    }

    public void setGreen() {
        this.green++;
        if(this.green%3==0){
            this.player.addCoin();
        }
    }

    public int getPink() {
        return pink;
    }

    public void setPink() {
        this.pink++;
        if(this.pink%3==0){
            this.player.addCoin();
        }
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow() {
        this.yellow++;
        if(this.yellow%3==0){
            this.player.addCoin();
        }
    }
}
