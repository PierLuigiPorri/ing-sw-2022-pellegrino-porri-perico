package it.polimi.ingsw.MODEL;

public class Cloud {
    public Student students;

    public Student getStudenti() {
        return students;
    }

    public void setStudenti(Student studenti) {
        this.students = studenti;
    }
    public Cloud(){
        //costruttore per 2 o 4 giocatori
    }
    public Cloud(int flag){
        //costruttore per 3 giocatori
    }
}
