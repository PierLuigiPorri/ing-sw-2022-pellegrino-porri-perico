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

    public void setColor(ColorTracker color){
        if (color.equals(player.getGame().red)) {
            setRed();
        }
        else if (color.equals(player.getGame().blue)) {
            setBlue();
           }
        else if (color.equals(player.getGame().green)) {
            setGreen();
        }
        else if (color.equals(player.getGame().yellow)) {
            setYellow();
        }
        else {
            setPink();
            }
    }

    public void desetColor(ColorTracker color){
        if (color.equals(player.getGame().red)) {
            this.red--;
        }
        else if (color.equals(player.getGame().blue)) {
            this.blue--;
        }
        else if (color.equals(player.getGame().green)) {
            this.green--;
        }
        else if (color.equals(player.getGame().yellow)) {
            this.yellow--;
        }
        else {
            this.pink--;
        }
    }

    public int getRed() {
        return red;
    }

    private void setRed() {
        this.red++;
        if(this.red%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getBlue() {
        return blue;
    }

    private void setBlue() {
        this.blue ++;
        if(this.blue%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getGreen() {
        return green;
    }

    private void setGreen() {
        this.green++;
        if(this.green%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getPink() {
        return pink;
    }

    private void setPink() {
        this.pink++;
        if(this.pink%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getYellow() {
        return yellow;
    }

    private void setYellow() {
        this.yellow++;
        if(this.yellow%3==0){ //Check regole esperto
            this.player.addCoin();
        }
    }
}
