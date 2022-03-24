package it.polimi.ingsw.MODEL;

public class Student {
    private Color colore;
    private int influenza;

    public Student(Color colore){
        this.colore=colore;
    }

    public Color getColore() {
        return colore;
    }

    public void placeInIsland(int i, IslandType[] islandTypes, Gate gate){
        islandTypes[i].placeStudents(this);
        gate.removeStudent(this);
    }

    public void placeInHall(Hall hall, Gate gate){
        if(this.colore== Color.RED){
            hall.setRed();
        }
        if(this.colore== Color.BLUE){
            hall.setBlue();
        }
        if(this.colore== Color.GREEN){
            hall.setGreen();
        }
        if(this.colore== Color.PINK){
            hall.setPink();
        }
        if(this.colore== Color.YELLOW){
            hall.setYellow();
        }
        gate.removeStudent(this);
    }


    public void takeFromCloud(Cloud cloud, Gate gate){
        for (int i=0; i<gate.students.length; i++)
            cloud.students[i]=gate.students[gate.students.length-i-1];
        cloud.students=null;
    }

}
