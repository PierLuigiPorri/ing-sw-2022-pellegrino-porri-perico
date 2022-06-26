package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.Random;

/**
 * GUI object representing an Island in the GUI View. It's a pane with set anchor points for students and towers. Stores also the number
 * of students of each color and the towers on the Island. Can be selected of iterated in a List if needed.
 * @author GC56
 */
public class IslandGUI extends Pane {

    private int index;
    private int red=0;
    private int blue=0;
    private int green=0;
    private int yellow=0;
    private int pink=0;
    private int towers=0;
    private boolean selected=false;

    /**
     * Constructor method. Sets layout and coordinates, then randomly sets an image.
     * @param i The index of the island. Int parameter between 1-12.
     */
    public IslandGUI(int i) {
        this.index = i;
        this.setHeight(180);
        this.setWidth(180);
        Random rand = new Random();
        int type = rand.nextInt(3);
        if (type == 0) {
            Image im = new Image("Graphical_Assets/island1.png");
            ImageView iv=new ImageView(im);
            iv.setFitHeight(180);
            iv.setFitWidth(180);
            this.getChildren().add(iv);
        } else if (type == 1) {
            Image im =new Image("Graphical_Assets/island2.png");
            ImageView iv=new ImageView(im);
            iv.setFitHeight(180);
            iv.setFitWidth(180);
            this.getChildren().add(iv);
        }else{
            Image im =new Image("Graphical_Assets/island3.png");
            ImageView iv=new ImageView(im);
            iv.setFitHeight(180);
            iv.setFitWidth(180);
            this.getChildren().add(iv);
        }
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

    public void setRed() {
        this.red++;
    }

    public void setBlue() {
        this.blue++;
    }

    public void setGreen() {
        this.green++;
    }

    public void setYellow() {
        this.yellow++;
    }

    public void setPink() {
        this.pink++;
    }

    public int getTowers() {
        return towers;
    }

    public void setTowers(int towers) {
        this.towers = towers;
    }

    public void setSelected(){
        this.setStyle("-fx-background-color: yellow");
        selected=true;
    }

    public void deselect(){
        this.setStyle(null);
        selected=false;
    }
}
