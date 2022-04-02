package it.polimi.ingsw.MODEL;

import java.util.ArrayList;

public abstract class StudentSpace{
    protected ArrayList<Student> students = null;

    public abstract void addStudent(ColorTracker color);
    public abstract void removeStudent(int index);
}
