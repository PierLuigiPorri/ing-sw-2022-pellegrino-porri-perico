package it.polimi.ingsw.CLIENT;

import java.util.Objects;
import java.util.Scanner;

public class ClientApp {

    private final ClientMsgHandler msgHandler;
    private final View view;

    public static void main(String[] args){
        ClientApp clientApp=new ClientApp(args);
    }

    public ClientApp(String[] CLIargs){
        msgHandler=new ClientMsgHandler("127.0.0.1", 4000); //Connection setup with this IP and Port numbers
        if(CLIargs.length!=0 && Objects.equals(CLIargs[0], "cli")){
            view=new CLI(msgHandler);
        }
        else{
            view=new GUI(msgHandler);
        }
        msgHandler.setView(view);
        new Thread(new AckSender(msgHandler,5000)).start();
        new Thread(msgHandler).start();
    }



    public static int getCorrectInput(String request, int a, int b){
        //This method gets correct input from the client of 2 possible integer values: a, b while asking the "request"
        boolean inv=true; //Input Not Valid
        String input=null;
        Scanner s=new Scanner(System.in);
        while(inv) {
            System.out.println(request);
            try {
                input = s.nextLine();
                if(Integer.parseInt(input)==a || Integer.parseInt(input)==b) {
                    inv = false;
                }
                else{
                    System.out.println("Input is not valid");
                }
            }
            catch (Exception e){
                System.out.println("Input is not valid");
            }
        }
        return Integer.parseInt(input);
    }

    
}
