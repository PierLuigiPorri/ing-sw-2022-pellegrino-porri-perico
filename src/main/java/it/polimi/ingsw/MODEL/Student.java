package it.polimi.ingsw.MODEL;

public class Student {
    private Color color;
    private int influence;

    public Student(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void placeInIsland(int i, IslandType[] islandTypes, Gate gate){
        islandTypes[i].placeStudents(this);
        gate.removeStudent(this.color);
        //da levare, non dovrebbe averli student i metodi per muoversi ma devono muoverlo gli altri  --Doot
    }

    public void placeInHall(Hall hall, Gate gate){
        if(this.color == Color.RED){
            hall.setRed();
        }
        if(this.color == Color.BLUE){
            hall.setBlue();
        }
        if(this.color == Color.GREEN){
            hall.setGreen();
        }
        if(this.color == Color.PINK){
            hall.setPink();
        }
        if(this.color == Color.YELLOW){
            hall.setYellow();
        }
        gate.removeStudent(this.color);
        //da levare, non dovrebbe averli student i metodi per muoversi ma devono muoverlo gli altri --Doot
    }


    public void CloudToGate(Cloud cloud, Gate gate){
        //da levare, non dovrebbe averli student i metodi per muoversi ma devono muoverlo gli altri --Doot
    }

}
