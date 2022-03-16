package it.polimi.ingsw.MODEL;

public class Student {
    private Colors colore;
    private int influenza;

    public Student(Colors colore){
        this.colore=colore;
    }

    public Colors getColore() {
        return colore;
    }

    public void placeInIsland(int i, Island[] islands, Gate gate){
        islands[i].placeStudents(this);
        gate.removeFromGate(this);
    }

    public void placeInHall(Hall hall, Gate gate){
        if(this.colore==Colors.RED){
            hall.setRed();
        }
        if(this.colore==Colors.BLUE){
            hall.setBlue();
        }
        if(this.colore==Colors.GREEN){
            hall.setGreen();
        }
        if(this.colore==Colors.PINK){
            hall.setPink();
        }
        if(this.colore==Colors.YELLOW){
            hall.setYellow();
        }
        gate.removeFromGate(this);
    }


    public void takeFromCloud(Cloud cloud, Gate gate){
        for (int i=0; i<gate.students.length; i++)
            cloud.students[i]=gate.students[gate.students.length-i-1];
        cloud.students=null;
    }

}
