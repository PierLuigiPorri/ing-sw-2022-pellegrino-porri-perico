package it.polimi.ingsw.MODEL;

import java.util.Random;

public class Bag {
    private Student[] studenti;

    public Bag(){
        int r=24;
        int g=24;
        int y=24;
        int b=24;
        int p=24;

        int[] array={
                0,1,2,3,4};

        studenti=new Student[130];
        for(int i=0; i<130; i++){
            Random rand = new Random();

            int x = (rand.nextInt(array.length));
            if(x==0) {
                r--;
                if (r == 0) {
                    array=new int[array.length-1];
                }
                studenti[i] = new Student(Colors.RED);

            }
            if(x==1){
                b--;
                studenti[i]=new Student(Colors.BLUE);
            }
            if(x==2){
                g--;
                studenti[i]=new Student(Colors.GREEN);
            }
            if(x==3){
                p--;
                studenti[i]=new Student(Colors.PINK);
            }
            if(x==4){
                y--;
                studenti[i]=new Student(Colors.YELLOW);
            }
        }
    }
}
