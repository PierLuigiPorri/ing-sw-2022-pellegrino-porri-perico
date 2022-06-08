package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class IslandGUI extends Pane {

    private int index;
    private int red=0;
    private int blue=0;
    private int green=0;
    private int yellow=0;
    private int pink=0;

    public IslandGUI(int i) {
        this.index = i;
        this.setHeight(180);
        this.setWidth(180);
        Random rand = new Random();
        int type = rand.nextInt(3);
        if (type == 0)
            this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/island1.png")));
        else if (type == 1)
            this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/island2.png")));
        else
            this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/island3.png")));
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getYellow() {
        return yellow;
    }

    public int getPink() {
        return pink;
    }

    public void setRed(int red) {
        this.red++;
    }

    public void setBlue(int blue) {
        this.blue++;
    }

    public void setGreen(int green) {
        this.green++;
    }

    public void setYellow(int yellow) {
        this.yellow++;
    }

    public void setPink(int pink) {
        this.pink++;
    }
}
