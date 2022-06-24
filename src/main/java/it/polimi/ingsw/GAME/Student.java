package it.polimi.ingsw.GAME;

/**
 * Represents the pawn student, which has two attributes: the color (which is the main information) and the colorInCli,
 * which sets the color in Command Line Interface, so it's a design attribute.
 * @author GC56
 */
public class Student{
    private final String color;
    private final String colorInCLI;

    /**
     * Student constructor. It sets the color of the student by the specified one and set the String Color in Command Line Interface standard.
     * @param color the color of the student to be build.
     * @author GC56
     */
    public Student(String color){
        this.color=color;

        if(this.color.equals("RED")){
            colorInCLI="\u001B[31m";
        }
        else if(this.color.equals("BLUE")){
            colorInCLI="\u001B[34m";
        }
        else if(this.color.equals("GREEN")){
            colorInCLI="\u001B[32m";
        }
        else if(color.equals("YELLOW")){
            colorInCLI="\u001B[33m";
        }
        else if(color.equals("PINK")){
            colorInCLI="\u001B[35m";
        }
        else
            colorInCLI=null;
    }

    /**
     * Returns the Color in Command Line Interface standard.
     * @return the Color in Command Line Interface standard.
     */
    public String getColorInCLI() {
        return colorInCLI;
    }

    /**
     * Returns the color of the student.
     * @return the color of the student.
     */
    public String getColor() {
        return color;
    }

}
