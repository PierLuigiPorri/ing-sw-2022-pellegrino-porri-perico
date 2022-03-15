package it.polimi.ingsw.MODEL;

public class Cloud {
    public Student[] students;

    public Student[] getStudenti() {
        return students;
    }

    public void setStudenti(Student studenti, int i) {
        this.students[i] = studenti;
    }

    public Cloud(){
        students=new Student[3];
    }
    public Cloud(int flag){
        students=new Student[4];
    }


    public void removeStudent(int i){
        students[i]=null;
    }

}
