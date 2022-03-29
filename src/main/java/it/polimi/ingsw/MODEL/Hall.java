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

    public void setColor(Color color){
        if (color.equals(Color.RED))
            setRed();
        else if (color.equals(Color.BLUE))
            setBlue();
        else if (color.equals(Color.GREEN))
            setGreen();
        else if (color.equals(Color.YELLOW))
            setYellow();
        else setPink();
    }

    private void setRed() {
        this.red++;
        if(this.red%3==0&&player.getGame().getGameType()==1){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getBlue() {
        return blue;
    }

    private void setBlue() {
        this.blue ++;
        if(this.blue%3==0&&player.getGame().getGameType()==1){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getGreen() {
        return green;
    }

    private void setGreen() {
        this.green++;
        if(this.green%3==0&&player.getGame().getGameType()==1){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getPink() {
        return pink;
    }

    private void setPink() {
        this.pink++;
        if(this.pink%3==0&&player.getGame().getGameType()==1){ //Check regole esperto
            this.player.addCoin();
        }
    }

    public int getYellow() {
        return yellow;
    }

    private void setYellow() {
        this.yellow++;
        if(this.yellow%3==0&&player.getGame().getGameType()==1){ //Check regole esperto
            this.player.addCoin();
        }
    }
}
