package it.polimi.ingsw.MODEL;

public class Character extends StudentSpace implements TDSpace{
    private int costo;
    private int id; //Da 1 a 12
    private int maxStud;
    private Student[] studenti;
    private int TD=0;

    public Character(int numCarta){
        this.id=numCarta;
        switch (id){
            case 1:
                costo=1;
                maxStud=4;
                riempiStudenti(maxStud);
                break;
            case 2:
                costo=2;
                break;
            case 3:
                costo=3;
                break;
            case 4:
                costo=1;
                break;
            case 5:
                costo=2;
                TD=4;
                break;
            case 6:
                costo=3;
                break;
            case 7:
                costo=1;
                maxStud=6;
                riempiStudenti(maxStud);
                break;
            case 8:
                costo=2;
                break;
            case 9:
                costo=3;
                break;
            case 10:
                costo=1;
                break;
            case 11:
                costo=2;
                maxStud=4;
                riempiStudenti(maxStud);
                break;
            case 12:
                costo=3;
                break;
        }
    }

    private void riempiStudenti(int n){
        for(int i=0; i<n; i++){
            //TODO: studenti[i]= Studente pescato dalla bag
        }
    }

    public int getCosto() {
        return costo;
    }

    public int getId() {
        return id;
    }

    public Student getStudente(int indice) {
        return studenti[indice];
    }

    @Override
    public void addStudent(Color color) {

    }

    public void removeStudent(int index) {

    }

    @Override
    public void addTD() {
        if(TD<4) {
            TD++;
        }
        else{
            //Gestione dell'errore
        }
    }

    @Override
    public void removeTD() {
        if(TD>0) {
            TD--;
        }
        else{
            //Gestione dell'errore
        }
    }
}
