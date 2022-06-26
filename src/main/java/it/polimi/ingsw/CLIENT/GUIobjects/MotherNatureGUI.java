package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * GUI object representing the token of Mother Nature in the GUI View. Can be placed and dragged/dropped around the board.
 * @author GC56
 */
public class MotherNatureGUI extends Pane {

    private double stageX, stageY;

    public MotherNatureGUI(){
        this.setHeight(80);
        this.setWidth(80);
        this.getChildren().add(new ImageView(new Image("Graphical_Assets/mother_nature.png")));
        this.setCursor(Cursor.HAND);
    }

    /**
     * Method called when the object is pressed. Stores the current coordinates for later use.
     */
    public void pressed(double x,double y){
        stageX=x;
        stageY=y;
    }

    /**
     * Method called when the object is dragged. Sets the coordinates based on the movement.
     */
    public void dragged(double x, double y){
        this.setTranslateX(x-this.stageX);
        this.setTranslateY(y-this.stageY);
    }
}
