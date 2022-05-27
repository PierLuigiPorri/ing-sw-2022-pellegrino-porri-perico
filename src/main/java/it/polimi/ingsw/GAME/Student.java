package it.polimi.ingsw.GAME;

public class Student{
    private final String color;
    private final String colorInCLI;

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

    public String getColorInCLI() {
        return colorInCLI;
    }

    public String getColor() {
        return color;
    }

}
