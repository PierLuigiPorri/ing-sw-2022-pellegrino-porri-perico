package it.polimi.ingsw.MODEL;

public class Cloud {
    public Student studenti;

    public Student getStudenti() {
        return studenti;
    }

    public void setStudenti(Student studenti) {
        this.studenti = studenti;
    }
    public Cloud(){
        //costruttore per 2 o 4 giocatori
    }
    public Cloud(int flag){
        //costruttore per 3 giocatori
    }
}
