package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.layout.Pane;

/**
 * GUI object representing a space on a Character Card on which Students can be placed.
 * @author GC56
 */
public class CharacterGUI extends Pane {
    public final int students;

    /**
     * Constructor parameter. Sets the coordinates and the layout.
     * @param children Max number of students that can be placed on the card. Int value.
     */
    public CharacterGUI(int children){
        this.setWidth(159);
        this.setHeight(145);
        this.setLayoutX(0);
        this.setLayoutY(94);
        this.students=children;
    }
}
