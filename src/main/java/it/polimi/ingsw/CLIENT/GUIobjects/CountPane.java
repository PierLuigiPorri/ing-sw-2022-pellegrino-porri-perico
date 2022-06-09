package it.polimi.ingsw.CLIENT.GUIobjects;


import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class CountPane extends Pane {

    public String color;
    public Text text;

    public CountPane(String color, int count){
        this.color=color;
        this.setHeight(27);
        this.setWidth(25);
        text=new Text();
        switch (color){
            case "RED":
                this.setBorder(new Border(new BorderStroke(Color.RED,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setLayoutX(62);
                this.setLayoutY(1);
                text.setFill(Color.RED);
                break;
            case "BLUE":
                this.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setLayoutX(152);
                this.setLayoutY(56);
                text.setFill(Color.LIGHTBLUE);
                break;
            case "GREEN":
                this.setBorder(new Border(new BorderStroke(Color.GREEN,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setLayoutX(139);
                this.setLayoutY(115);
                text.setFill(Color.GREEN);
                break;
            case "YELLOW":
                this.setBorder(new Border(new BorderStroke(Color.YELLOW,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setLayoutX(13);
                this.setLayoutY(115);
                text.setFill(Color.YELLOW);
                break;
            case "PINK":
                this.setBorder(new Border(new BorderStroke(Color.MAGENTA,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                this.setLayoutX(13);
                this.setLayoutY(32);
                text.setFill(Color.MAGENTA);
                break;
        }
        text.setStrokeWidth(0.5);
        text.setStroke(Color.BLACK);
        text.setFont(Font.font("papyrus",14));
        text.setLayoutX(7);
        text.setLayoutY(18);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("x"+count);
        this.getChildren().add(text);
    }
}
