package it.polimi.ingsw.CLIENT;


import java.util.Scanner;

public class CLIAPP {

    public static void main(String[] args){
        Object lock = new Object();
        String ip;
        ip="127.0.0.1";
        //TODO: Decommentare questa riga per inserire l'ip manualmente
        //ip=getValidString("Write the IP of the server");
        ClientMsgHandler msgHandler = new ClientMsgHandler(ip, 50000, lock); //Connection setup with this IP and Port numbers
        AckSender ackSender = new AckSender(msgHandler, 2000);
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        new Thread(new CLI(msgHandler, lock)).start();
    }

    private static String getValidString(String request) {
        //This method gets a non-empty reply String while asking the "request"
        boolean inv = true; //Input Not Valid
        String input = "";
        Scanner s = new Scanner(System.in);
        while (inv) {
            System.out.println(request);
            try {
                input = s.nextLine();
                if (!input.isEmpty()) {
                    inv = false;
                } else {
                    System.out.println("Input is not valid");
                }
            } catch (Exception e) {
                System.out.println("Input is not valid");
            }
        }
        return input;
    }
}
