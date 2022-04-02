package it.polimi.ingsw.MODEL;

public class Student{
    private ColorTracker color;

    public Student(ColorTracker color){
        this.color=color;;
    }

    public ColorTracker getColor() {
        return color;
    }

    public int getInfluence(){
        return this.color.getInfluence();
    }
}
