package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class IslandGUI extends Pane {

    private int index;

    public IslandGUI(int i) {
        this.index = i;
        this.setHeight(180);
        this.setWidth(180);
        Random rand = new Random();
        int type = rand.nextInt(1,4);
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
}
