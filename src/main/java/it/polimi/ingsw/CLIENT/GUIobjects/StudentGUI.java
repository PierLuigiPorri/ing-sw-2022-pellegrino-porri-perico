package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * GUI object representing a Student in the GUI View. Can be placed and drag/dropped around the board. Can also be selected and deselected.
 * @author GC56
 */
public class StudentGUI extends Pane{
    private final String color;
    private double stageX, stageY;
    private Coordinates coord;


    /**
     * Constructor method. Sets the color, the coordinates and the layout.
     * @param color The color of the student. String parameter.
     */
    public StudentGUI(String color){
        this.color=color;
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)");
        switch(color){
            case "RED":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/student_red.png")));
                break;
            case "BLUE":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/student_blue.png")));
                break;
            case "GREEN":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/student_green.png")));
                break;
            case "YELLOW":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/student_yellow.png")));
                break;
            case "PINK":
                this.getChildren().add(new ImageView(new Image("Graphical_Assets/student_pink.png")));
                break;
        }
        this.setHeight(34);
        this.setWidth(34);
        this.setCursor(Cursor.HAND);
    }

    public String getColor() {
        return color;
    }

    public Coordinates getCoord() {
        return coord;
    }

    /**
     * Method called when the object is pressed. Stores the current coordinates for later use.
     */
    public void pressed(double x,double y){
        stageX=x;
        stageY=y;
        coord=new Coordinates(this.getLayoutX(),this.getLayoutY());
    }

    /**
     * Method called when the object is dragged. Sets the coordinates based on the movement.
     */
    public void dragged(double x, double y){
        this.setLayoutX(x-this.stageX);
        this.setLayoutY(y-this.stageY);
    }

    public void setSelected(){
        this.setStyle("-fx-background-color: yellow");
    }

    public void deselect(){
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)");
    }
}
