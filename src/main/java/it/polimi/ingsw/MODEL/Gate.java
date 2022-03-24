package it.polimi.ingsw.MODEL;

public class Gate implements StudentSpace{
    public Student[] students;
    public Player player;
    private final int MAX;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        this.students = new Student[9];
    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        this.students= new Student[7];
    }

    public Student[] getStudents() {
        return students;
    }

    public Student removeStudent(Student student) {
        int i=0;
        Student tmp;
        while(!students[i].equals(student))
            i++;
        tmp=students[i];
        students[i]=null;
        return tmp;
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public void addStudent(Color color) {
        int i=0;
        while (students[i]!=null && i<=MAX-1)
            i++;
        students[i]=new Student(color);
    }
}
