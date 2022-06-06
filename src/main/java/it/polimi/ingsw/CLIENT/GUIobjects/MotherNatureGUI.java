package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MotherNatureGUI extends Pane {

    private double stageX, stageY;

    public MotherNatureGUI(){
        this.setHeight(80);
        this.setWidth(80);
        this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/mother_nature.png")));
    }

    public void pressed(double x,double y){
        stageX=x;
        stageY=y;
    }

    public void dragged(double x, double y){
        this.setLayoutX(x-this.stageX);
        this.setLayoutY(y-this.stageY);
    }
}
