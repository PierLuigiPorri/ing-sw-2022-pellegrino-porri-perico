package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is the one that sends and receives all messages exchanged with the server
 * @author GC56
 */
public class ClientMsgHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private View view;
    private boolean kill;
    private final Object lock;

    private final ArrayList<UpdateMessage> updates;
    private final ArrayList<ResponseMessage> responses;

    /**
     * Constructor.
     * @param host IP or URL of the server
     * @param port Port number of the server
     * @param lock An Object that is used as a lock and shared between the main App and the MessageHandler thread
     */
    public ClientMsgHandler(String host, int port, Object lock) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected!");
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.exit(0);
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Stream connection failed");
            System.exit(0);
        }
        this.kill = false;
        this.lock = lock;
        this.updates = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    /**
     * Send a message to the server
     * @param message The message to send
     * @author GC56
     */
    public void send(MessageType message) {
        try {
            out.writeObject(message);
        } catch (Exception e) {
            System.out.println("Message send failed");
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (!kill) {
            try {
                MessageType latestMessage = (MessageType) in.readObject();
                synchronized (lock) {
                    sort(latestMessage);
                }
                try {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Connection lost");
                kill = true;
            }
        }
    }

    /**
     * Method that sorts messages based on their Type
     * @param message the message to sort
     * @author GC56
     */
    public void sort(MessageType message) {
        if (message.type == 4) {
            this.updates.add((UpdateMessage) message);
            view.signalUpdate();
        } else {
            ResponseMessage rMex = (ResponseMessage) message;
            this.responses.add(rMex);
            if (!rMex.allGood)
                view.signalResponse();
        }
    }

    public ArrayList<UpdateMessage> getUpdates() {
        return this.updates;
    }

    public ArrayList<ResponseMessage> getResponses() {
        return this.responses;
    }

    public void clearAllMessages() {
        this.updates.clear();
        this.responses.clear();
    }
}
