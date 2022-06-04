package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TowerGUI extends Pane {

    private final String color;

    public TowerGUI(String color){
        this.color=color;
        this.setWidth(46);
        this.setHeight(46);
        switch (color){
            case "BLACK":
                this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/black_tower.png")));
                break;
            case "GREY":
                this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/grey_tower.png")));
                break;
            case "WHITE":
                this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/white_tower.png")));
        }
    }

    public String getColor() {
        return color;
    }
}