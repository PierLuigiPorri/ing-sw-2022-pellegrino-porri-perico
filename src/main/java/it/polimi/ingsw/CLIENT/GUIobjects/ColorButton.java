package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * GUI object representing a Button associated with a specific color.
 * @author GC56
 */
public class ColorButton extends ToggleButton {
    public String color;

    /**
     * Constructor method. Sets the button style based on the color.
     * @param c The color chosen. String parameter.
     */
    public ColorButton(String c){
        this.color=c;
        switch (color){
            case "RED":
                this.setStyle("-fx-background-color:red; -fx-background-radius:100");
            case "BLUE":
                this.setStyle("-fx-background-color:blue; -fx-background-radius:100");
                this.setTextFill(Color.LIGHTBLUE);
            case "GREEN":
                this.setStyle("-fx-background-color:green; -fx-background-radius:100");
                this.setTextFill(Color.LIGHTGREEN);
            case "YELLOW":
                this.setStyle("-fx-background-color:yellow; -fx-background-radius:100");
            case "PINK":
                this.setStyle("-fx-background-color:magenta; -fx-background-radius:100");
        }
        this.setText(color);
        this.setFont(Font.font("papyrus",16));
    }
}
