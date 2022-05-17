package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.ActionMessage;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.FutureTask;

public class CLI implements View{

    private final ClientMsgHandler msgHandler;
    ArrayList<Integer> inputInt;
    ArrayList<String> inputStr;

    private final PrintStream out;
    private Thread threadInput;

    private FutureTask<String> futureTask;
    public String player;

    public CLI(ClientMsgHandler clientMsgHandler, String pl){
        this.msgHandler=clientMsgHandler;
        this.inputInt=new ArrayList<>();
        this.inputStr=new ArrayList<>();
        this.player=pl;
        out=System.out;
    }

    public void initCLI(){
        //futureTask= new FutureTask<>( INPUT );
        threadInput=new Thread(futureTask);
        threadInput.start();

    }


    public void moveMotherNature() {
        //TODO:gestire il massimo movimento possibile (considerando che c'è la possibilità di avere un bonus)
        //this method needs the movement [int].
        int i;
        System.out.println("How far do you want to move MotherNature?");
        i = getIntInput();
        checkIntInput(0, 7, i, "How far do you want to move MotherNature?\n");
        messageConfirmed(3);
    }

    public void changePhase() {
        try{
            msgHandler.sendMessage(new ActionMessage(null, null, null,6));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void gateToIsland() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index from the gate and the index of the island. [String, String, int, int]
        int i;
        String s;
        System.out.println("Which is the position of the student you want to move?");
        i=getIntInput();
        checkIntInput(0, 10, i, "Which is the position of the student you want to move?\n");
        System.out.println("Which is the color of the student? ");
        s=getStrInput();
        checkStrInput(s, "Which is the color of the student? ");
        System.out.println("In which island do you want to move your student?");
        i=getIntInput();
        checkIntInput(1, 12, i, "In which island do you want to move your student?\n");
        messageConfirmed(0);
    }

    public void gateToHall() {
        //this method needs the name of the player who calls it, the color of the student to move.
        String s;
        System.out.println("Which is the color of the student you want to move? ");
        s=getStrInput();
        checkStrInput(s, "Which is the color of the student you want to move? ");
        messageConfirmed(1);
    }

    public void cloudToGate() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index of the student in the cloud and the index of the cloud.
        int i;
        String s;
        System.out.println("Which cloud do you want to take students from?");
        i=getIntInput();
        checkIntInput(0, 3, i, "Which cloud do you want to take students from?\n");
        System.out.println("Which is the position of the student you want to take?");
        i=getIntInput();
        checkIntInput(0, 10, i, "Which is the position of the student you want to take?\n");
        System.out.println("Which is the color of the student? ");
        s=getStrInput();
        checkStrInput(s, "Which is the color of the student? ");
        messageConfirmed(2);
    }

    public void playCard() {
        // this method needs the name of the player who calls it, the index of the card to play.
        int i;
        System.out.println("Which card do you want to play?");
        i=getIntInput();
        checkIntInput(0, 10, i, "Which card do you want to play?\n");
        messageConfirmed(4);
    }

    public void activateCharacter(int index) throws IOException {
        ArrayList<Integer> a=new ArrayList<>(), c=null;
        ArrayList<String> b=new ArrayList<>();
        a.add(index);
        b.add(player);
        switch(index){
            //TODO:completare i case
            case 0:
                int i, x;
                System.out.println("What's the position of the student on the card you want to move?");
                i=getIntInput();
                checkIntInput(0, 3, i, "What's the position of the student on the card you want to move?\n");
                a.add(inputInt.get(inputInt.size()-1));
                System.out.println("What's the index of the island on which you want to move the student?");
                x=getIntInput();
                checkIntInput(1, 12, x, "What's the index of the island on which you want to move the student?");
                a.add(inputInt.get(inputInt.size()-1));
                break;
            case 2:
                System.out.println("What's the index of the island you want to determine the influence of?");
                i=getIntInput();
                checkIntInput(1, 12, i, "What's the index of the island you want to determine the influence of?\n");
                a.add(inputInt.get(inputInt.size()-1));
                break;
            case 4:
                System.out.println("What's the index of the island you want to put the counter on?");
                i=getIntInput();
                checkIntInput(1, 12, i, "What's the index of the island you want to put the counter on?\n");
                a.add(inputInt.get(inputInt.size()-1));
                break;
            case 6:
                System.out.println("How many students do you want to swap?");
                i=getIntInput();
                checkIntInput(1, 3, i, "How many students do you want to swap?");
                int max=inputInt.get(inputInt.size()-1);
                inputInt.remove(inputInt.size()-1);
                System.out.println("What are the indexes on the Character card of the students you want to swap?");
                for(int z=0; z<max; z++){
                    i=getIntInput();
                    checkIntInput(0,3,i,"What's the index?");
                    a.add(inputInt.get(inputInt.size()-1));
                }
                c=new ArrayList<>();
                System.out.println("What are the indexes on the gate of the students you want to swap?");
                for(int z=0; z<max; z++){
                    i=getIntInput();
                    checkIntInput(0,3,i,"What's the index?");
                    c.add(inputInt.get(inputInt.size()-1));
                }
                break;
            case 8:
                System.out.println("What color would you like to disable?");
                String in=getStrInput();
                checkStrInput(in, "What color would you like to disable?");
                b.add(inputStr.get(inputStr.size()-1));
                break;
            case 9:
                System.out.println("How many students would you like to swap?");
                i=getIntInput();
                checkIntInput(1, 2, i, "How many students would you like to swap?");
                max=inputInt.get(inputInt.size()-1);
                inputInt.remove(inputInt.size()-1);
                System.out.println("What are the indexes of the students on the gate you want to swap?");
                for(int z=0; z<max; z++){
                    i=getIntInput();
                    checkIntInput(0,9,i,"What's the index?");
                    a.add(inputInt.get(inputInt.size()-1));
                }
                System.out.println("Which colors would you like to swap in you hall?");
                for(int z=0; z<max; z++){
                    in=getStrInput();
                    checkStrInput(in, "Which color?");
                    b.add(inputStr.get(inputStr.size()-1));
                }
                break;
            case 10:
                System.out.println("What is the index on the card of the student you want to add?");
                i=getIntInput();
                checkIntInput(0,3,i,"What is the index on the card of the student you want to add?");
                a.add(inputInt.get(inputInt.size()-1));
                break;
            case 11:
                System.out.println("What color would you like to affect?");
                in=getStrInput();
                checkStrInput(in, "Which color?");
                b.add(inputStr.get(inputStr.size()-1));
            default:break;
        }
        msgHandler.sendMessage(new ActionMessage(a,b,c,5));
        inputInt.clear();
        inputStr.clear();
    }

    private void messageConfirmed(int type){
        try {msgHandler.sendMessage(new ActionMessage(inputInt, inputStr, null, type));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        inputInt.clear();
        inputStr.clear();
    }

    private int getIntInput() {
        Scanner s=new Scanner(System.in);
        inputInt.add(Integer.parseInt(s.nextLine()));
        return inputInt.get(inputInt.size()-1);
    }

    private String getStrInput() {
        Scanner s=new Scanner(System.in);
        inputStr.add(s.nextLine());
        return inputStr.get(inputInt.size()-1);
    }

    private void checkIntInput(int a, int b, int input, String string){
        while ( input<a || input >b ){
            inputInt.remove(inputInt.size()-1);
            System.out.println("The number entered is not allowed.\n" + string + "\n");
            input = getIntInput();
        }
    }

    private void checkStrInput(String s, String c) {
        while(!s.equals("RED") && !s.equals("BLUE") && !s.equals("GREEN") && !s.equals("YELLOW") && !s.equals("PINK")){
            inputStr.remove(inputStr.size()-1);
            System.out.println("The color entered is not allowed.\n" + c + "\n");
            s=getStrInput();
        }
    }

}
