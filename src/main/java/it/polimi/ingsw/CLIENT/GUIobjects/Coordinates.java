package it.polimi.ingsw.CLIENT.GUIobjects;
/**
 * Represent the coordinates of an object in the GUI space.
 * @author GC56
 */
public class Coordinates {

    private final double X;
    private final double Y;

    public Coordinates(double x, double y){
        this.X=x;
        this.Y=y;
    }

    public double getX(){
        return X;
    }

    public double getY(){
        return Y;
    }
}
