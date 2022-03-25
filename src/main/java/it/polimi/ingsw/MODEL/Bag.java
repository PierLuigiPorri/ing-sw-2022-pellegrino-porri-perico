package it.polimi.ingsw.MODEL;

import java.util.Random;

public class Bag {
    private Student[] studenti;
    int size=119;


    public Bag() {
        int x;
        Student[] stud;
        Random rand;

        studenti=new Student[120];
        int length= studenti.length-1;
        stud=create();
        for (int i=0; i<120; i++){
            rand = new Random();
            x=rand.nextInt(length);
            studenti[i] = stud[x];
            stud[x]=stud[length];
            length--;
        }
    }

    public void addStudent(Color color){
        size++;
        studenti[size]=new Student(color);
    }

    public Student extractStudent(){
        Student tmp;
        tmp=studenti[size];
        size--;
        studenti[studenti.length-1]=null;
        return tmp;
    }



    private Student[] create(){
        int count =0;
        Student[] stud=new Student[120];
        for (int i = 0; i < 24; i++) {
            stud[count] = new Student(Color.RED);
            count++;
        }
        for (int i = 0; i < 24; i++) {
            stud[count] = new Student(Color.GREEN);
            count++;
        }
        for (int i = 0; i < 24; i++) {
            stud[count] = new Student(Color.YELLOW);
            count++;
        }
        for (int i = 0; i < 24; i++) {
            stud[count] = new Student(Color.BLUE);
            count++;
        }
        for (int i = 0; i < 24; i++) {
            stud[count] = new Student(Color.PINK);
            count++;
        }
        return stud;
    }

}
