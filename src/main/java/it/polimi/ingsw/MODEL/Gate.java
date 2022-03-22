package it.polimi.ingsw.MODEL;

public class Gate implements StudentSpace{
    public Student[] students;
    public Player player;
    private static int MAX;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        this.students = null;
    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        this.students=null;
    }

    public Student[] getStudents() {
        return students;
    }

    public void removeFromGate(Student student) {
        int i=0;
        while(!students[i].equals(student))
            i++;
        students[i]=null;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void addStudent(Color color) {

    }
}
