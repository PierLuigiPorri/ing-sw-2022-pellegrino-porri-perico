package it.polimi.ingsw.MODEL;

public class Student {
    //prova
    private int id;
    private Color color;
    private int influence;

    public Student(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void placeInHall(Hall hall, Gate gate) {
        if (this.color == Color.RED) {
            hall.setRed();
        }
        if (this.color == Color.BLUE) {
            hall.setBlue();
        }
        if (this.color == Color.GREEN) {
            hall.setGreen();
        }
        if (this.color == Color.PINK) {
            hall.setPink();
        }
        if (this.color == Color.YELLOW) {
            hall.setYellow();
        }
    }
}
