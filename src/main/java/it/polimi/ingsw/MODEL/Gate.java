package it.polimi.ingsw.MODEL;

public class Gate implements StudentSpace{
    public Student[] students;
    public Player player;
    private final int MAX;

    public Gate(int pcount, Player player) {
        this.player = player;
        this.MAX=9;
        this.students = new Student[9];
        //TODO: il gate va riempito una volta creato. Mi serve Bag per farlo, e non so come farlo altrimenti. -Doot

    }

    public Gate(Player player){
        this.player=player;
        this.MAX=7;
        this.students= new Student[7];
        //TODO: il gate va riempito una volta creato. Mi serve Bag per farlo, e non so come farlo altrimenti. -Doot
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
