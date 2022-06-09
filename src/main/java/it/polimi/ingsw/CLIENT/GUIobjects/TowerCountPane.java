package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TowerCountPane extends Pane {
    public Text text;

    public TowerCountPane(int count){
        this.setHeight(42);
        this.setWidth(42);
        this.setLayoutX(70);
        this.setLayoutY(94);
        this.text=new Text();
        text.setFill(Color.BLACK);
        text.setFont(Font.font("papyrus",26));
        text.setLayoutX(5);
        text.setLayoutY(26);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("x"+count);
        this.getChildren().add(text);
    }
}
