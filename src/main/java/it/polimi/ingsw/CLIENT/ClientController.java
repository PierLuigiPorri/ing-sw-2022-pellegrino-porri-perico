package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.ActionMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientController {
    private ClientMsgHandler msgHandler;
    ArrayList<Integer> inputInt;
    ArrayList<String> inputStr;

    public ClientController(ClientMsgHandler clientMsgHandler){
        this.msgHandler=clientMsgHandler;
        this.inputInt=new ArrayList<>();
        this.inputStr=new ArrayList<>();
    }

    public void moveMotherNature() {
        //this method needs the movement [int].
        int i;
        System.out.println("How far do you want to move MotherNature?");
        i = getIntInput();
        checkIntInput(0, 7, i, "How far do you want to move MotherNature?\n");
        messageConfirmed();
    }

    public void changePhase(){
        try{
            msgHandler.sendMessage(new ActionMessage(null, null));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void gateToIsland(){
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
        messageConfirmed();
    }

    public void gateToHall(){
        //this method needs the name of the player who calls it, the color of the student to move.
        String s;
        System.out.println("Which is the color of the student you want to move? ");
        s=getStrInput();
        checkStrInput(s, "Which is the color of the student you want to move? ");
        messageConfirmed();
    }

    public void cloudToGate(){
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
        messageConfirmed();
    }

    public void playCard(){
        // this method needs the name of the player who calls it, the index of the card to play.
        int i;
        System.out.println("Which card do you want to play?");
        i=getIntInput();
        checkIntInput(0, 10, i, "Which card do you want to play?\n");
        messageConfirmed();
    }

    private void messageConfirmed(){
        try {
            msgHandler.sendMessage(new ActionMessage(inputInt, inputStr));
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
