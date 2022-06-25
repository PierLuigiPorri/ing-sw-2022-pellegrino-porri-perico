package it.polimi.ingsw.CLIENT;

import java.util.Scanner;

/**
 * Main runnable class of the user CLI interface. Sets the connections and creates the Classes needed to maintain it, then starts the CLI View.
 * @author GC56
 */
public class CLIAPP {

    /**
     * Main method. Creates and starts the threads of the classes needed to maintain the connection, then starts the CLI View.
     */
    public static void main(String[] args){
        Object lock = new Object();
        String ip;
        ip=getValidIP();
        ClientMsgHandler msgHandler = new ClientMsgHandler(ip, 50000, lock); //Connection setup with this IP and Port numbers
        AckSender ackSender = new AckSender(msgHandler, 2000);
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        new Thread(new CLI(msgHandler, lock)).start();
    }

    /**
     * I/O method that asks the user the IP of the server to connect the Client.
     * @return Returns the correct IP as a String.
     */
    private static String getValidIP() {
        //This method gets a non-empty reply String while asking the "request"
        boolean inv = true; //Input Not Valid
        String input = "";
        Scanner s = new Scanner(System.in);
        while (inv) {
            System.out.println("Write the IP of the server");
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
