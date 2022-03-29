package it.polimi.ingsw.MODEL;

public class Student extends ColorTracker{
    //prova
    private int id;
    private Color color;
    private int influence;

    public Student(Color color){
        super(color);
    }

    public Color getColor() {
        return color;
    }

}
