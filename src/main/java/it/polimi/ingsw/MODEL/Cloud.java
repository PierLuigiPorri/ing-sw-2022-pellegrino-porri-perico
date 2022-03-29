package it.polimi.ingsw.MODEL;

public class Cloud implements StudentSpace{
    private Student[] students;
    private int max;

    public Cloud(){
        max=3;
        students=new Student[3];
    }
    public Cloud(int flag) {
        max=4;
        students=new Student[4];
    }

    public Student[] getStudents() {
        return students;
    }

    @Override
    public void addStudent(Color color){
        int i=0;
        while (students[i]!=null && i<=max-1)
            i++;
        students[i]=new Student(color);
    }
    public void removeStudent(Color color){
        int i=0;
        while (!students[i].getColor().equals(color)) {
            i++;
        }
        students[i]=null;
    }

    public void emptyCloud(){
        students=null;
    }

}
