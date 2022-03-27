package it.polimi.ingsw.MODEL;

public class Gate implements StudentSpace{
    public Student[] students;
    public Player player;
    private int MAX;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        this.students = new Student[9];
        for(int i=0; i<9; i++){
            this.students[i]=player.getGame().getBg().extractStudent();
        }
    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        this.students= new Student[7];
        for(int i=0; i<7; i++){
            this.students[i]=player.getGame().getBg().extractStudent();
        }
    }

    public Student[] getStudents() {
        return students;
    }

    public void removeStudent(Color color) {
        //ricordiamoci di fare il controllo che il colore da rimuovere ci sia effettivamente, al lato controller
        int i=0;
        while(!students[i].getColor().equals(color))
            i++;
        students[i]=null;
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
