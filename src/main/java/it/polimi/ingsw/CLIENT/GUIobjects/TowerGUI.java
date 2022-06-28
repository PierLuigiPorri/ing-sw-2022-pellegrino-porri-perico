package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * GUI object representing a Tower token in the GUI View.
 */
public class TowerGUI extends Pane {


    public TowerGUI(String color){
        this.setWidth(46);
        this.setHeight(46);
        switch (color){
            case "BLACK":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/black_tower.png")));
                break;
            case "GREY":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/grey_tower.png")));
                break;
            case "WHITE":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/white_tower.png")));
        }
    }

}
