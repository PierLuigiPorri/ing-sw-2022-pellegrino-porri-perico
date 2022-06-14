package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.layout.Pane;

public class CharacterGUI extends Pane {
    int students;

    public CharacterGUI(int children){
        this.setWidth(159);
        this.setHeight(145);
        this.setLayoutX(0);
        this.setLayoutY(94);
        this.students=children;
    }
}
