package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements View {

    private final ClientMsgHandler msgHandler;
    private final ArrayList<Integer> inputInt;
    private final ArrayList<String> inputStr;
    private final ArrayList<MessageType> messages;

    private UpdateMessage update;

    public final String player;
    private boolean responseNeeded;

    public CLI(ClientMsgHandler clientMsgHandler) {
        this.msgHandler = clientMsgHandler;
        this.inputInt = new ArrayList<>();
        this.inputStr = new ArrayList<>();
        this.messages=new ArrayList<>();
        System.out.println(
                "    __________  _______    _   __________  _______\n" +
                "   / ____/ __ \\/  _/   |  / | / /_  __/\\ \\/ / ___/\n" +
                "  / __/ / /_/ // // /| | /  |/ / / /    \\  /\\__ \\\n" +
                " / /___/ _, _// // ___ |/ /|  / / /     / /___/ /\n" +
                "/_____/_/ |_/___/_/  |_/_/ |_/ /_/     /_//____/\n"
        );
        this.player = getValidString("What's your name?");
        System.out.println("What would you like to do?" +
                "\n0:Create a new Game" +
                "\n1:Join a game" +
                "\nDigit the appropriate number:");
        int g = getIntInput(); //0=New Game, 1=Join Game
        //New Game or Join Game?
        g = checkIntInput(0, 1, g, "Digit 0 to start a New Game or 1 to Join a game");
        if (g == 0) {
            newGame();
        } else if (g == 1) {
            joinGame();
        }
    }

    public void newGame() {
        //TODO
    }

    public void joinGame() {
        //TODO
    }

    public void initCLI() {
        new Thread(() -> {
            try {
                refresh();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            if (responseNeeded) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!messages.isEmpty()) {
                MessageType lastmessage = messages.get(messages.size() - 1);
                if (lastmessage instanceof UpdateMessage) {
                    //TODO:stampa messaggio di riuscita
                    messages.remove(messages.size() - 1);
                    update((UpdateMessage) lastmessage);
                } else {
                    //stampa errore
                }
            }
        });
    }

    public void update(UpdateMessage update) {
        this.update = update;
        initCLI();
    }

    public void relay(MessageType msg) {
        messages.add(msg);
    }

    public void refresh() throws InterruptedException, IOException {
        int choice;
        if (update.phase.equals("Planning")) {
            System.out.println("\nPlanning Time!" +
                    "\nNow's your chance to, you know, plan." +
                    "\nYou should all play a card. The best stuff happens later.");
            //TODO:stampare le carte già giocate
            if (update.order.get(0).equals(player)) {
                System.out.println("Now! Fire your card! Shape you destiny with a few single digit numbers!");
                actions(0).forEach((el) -> System.out.println(actions(0).indexOf(el) + ":" + el));
                choice = getSingleIntInput(1);
                perform(actions(0).get(choice));
            } else {
                System.out.println("It's not your time to play a card yet. Hold..." +
                        "\nWanna do something in the mean time? Digit the appropriate number and we'll do that for you:");
                actions(1).forEach((el) -> System.out.println(actions(1).indexOf(el) + ":" + el));
                choice = getSingleIntInput(0);
                perform(actions(1).get(choice));
            }
        } else {
            System.out.println("Action time!" +
                    "\nThis is the big league. Now is when the game is decided. Every round. Let's go!" +
                    "\nMove students. Activate special effects. Move digital imaginary tokens. Your call.");
            if (update.order.get(0).equals(player)) {
                System.out.println("Your turn!" +
                        "\nGo" +
                        "\nDo stuff!" +
                        "\nYou know what to do. If you don't, here's a reminder." +
                        "\nDigit the appropriate number and we'll do that for you:");
                actions(3).forEach((el) -> System.out.println(actions(3).indexOf(el) + ":" + el));
                choice = getSingleIntInput(8);
                perform(actions(3).get(choice));
            }
            else{
                System.out.println("Not your time to shine yet, somebody else is playing." +
                        "\nBe ready for when your turn comes." +
                        "\nIn the mean time, wanna do something? Digit the appropriate number and we'll do that for you:");
                actions(2).forEach((el) -> System.out.println(actions(2).indexOf(el) + ":" + el));
                choice = getSingleIntInput(4);
                perform(actions(2).get(choice));
            }
        }
    }

    private void perform(String request) throws IOException {
        switch(request){
            case "Refresh":
                responseNeeded=false;
                break;
            case "Play a card":
                responseNeeded=true;
                playCard();
                break;
            case "See other players' boards":
                responseNeeded=false;
                //TODO:scrivere il metodo
                break;
            case "See board (islands and clouds)":
                responseNeeded=false;
                //TODO:scrivere il metodo;
                break;
            case "See hand":
                responseNeeded=false;
                //TODO:scrivere il metodo;
                break;
            case "See Characters":
                responseNeeded=false;
                //TODO:scrivere il metodo;
                break;
            case "Move a student from the gate to an Island":
                responseNeeded=true;
                gateToIsland();
                break;
            case "Move a student from the gate to your Hall":
                responseNeeded=true;
                gateToHall();
                break;
            case "Activate a Character":
                responseNeeded=true;
                activateCharacter();
                break;
            case "Move Mother Nature":
                responseNeeded=true;
                moveMotherNature();
                break;
            case "Get students from a Cloud":
                responseNeeded=true;
                cloudToGate();
                break;
            default:break;
        }
    }

    private ArrayList<String> actions(int spot) {
        ArrayList<String> list = new ArrayList<>();
        switch (spot) {
            case 0://Player's turn, Planning phase
                list.add("Play a card");
                list.add("Refresh");
                break;
            case 1://Planning phase, not player's turn
                list.add("Refresh");
                break;
            case 2://Action phase, not player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                list.add("See Characters");
                list.add("Refresh");
                break;
            case 3://Action phase, player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                list.add("See Characters");
                list.add("Move a student from the gate to an Island");
                list.add("Move a student from the gate to your Hall");
                list.add("Activate a Character");
                list.add("Move Mother Nature");
                list.add("Refresh");
                break;
            case 4://End of action phase, player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                list.add("See Characters");
                list.add("Get students from a Cloud");
                list.add("Refresh");
                break;
        }
        return list;
    }


    public void moveMotherNature() {
        //this method needs the movement [int].
        int i;
        System.out.println("How far do you want to move MotherNature?");
        i = getIntInput();
        checkIntInput(0, 7, i, "How far do you want to move MotherNature?\n");
        messageConfirmed(3);
    }

    public void changePhase() {
        msgHandler.send(new ActionMessage(null, null, null, 6));
    }

    public void gateToIsland() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index from the gate and the index of the island. [String, String, int, int]
        int i;
        String s;
        System.out.println("Which is the position of the student you want to move?");
        i = getIntInput();
        checkIntInput(0, 10, i, "Which is the position of the student you want to move?\n");
        System.out.println("Which is the color of the student? ");
        s = getStrInput();
        checkStrInput(s, "Which is the color of the student? ");
        System.out.println("In which island do you want to move your student?");
        i = getIntInput();
        checkIntInput(1, 12, i, "In which island do you want to move your student?\n");
        messageConfirmed(0);
    }

    public void gateToHall() {
        //this method needs the name of the player who calls it, the color of the student to move.
        String s;
        System.out.println("Which is the color of the student you want to move? ");
        s = getStrInput();
        checkStrInput(s, "Which is the color of the student you want to move? ");
        messageConfirmed(1);
    }

    public void cloudToGate() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index of the student in the cloud and the index of the cloud.
        int i;
        String s;
        System.out.println("Which cloud do you want to take students from?");
        i = getIntInput();
        checkIntInput(0, 3, i, "Which cloud do you want to take students from?\n");
        System.out.println("Which is the position of the student you want to take?");
        i = getIntInput();
        checkIntInput(0, 10, i, "Which is the position of the student you want to take?\n");
        System.out.println("Which is the color of the student? ");
        s = getStrInput();
        checkStrInput(s, "Which is the color of the student? ");
        messageConfirmed(2);
    }

    public void playCard() {
        // this method needs the name of the player who calls it, the index of the card to play.
        int i;
        System.out.println("Which card do you want to play?");
        i = getIntInput();
        checkIntInput(0, 10, i, "Which card do you want to play?\n");
        messageConfirmed(4);
    }

    public void activateCharacter() throws IOException {
        System.out.println("Which character would you like to activate? Digit the appropriate index, between these:");
        //TODO:stampa i personaggi
        int index=getSingleIntInput(11);
        ArrayList<Integer> a = new ArrayList<>(), c = null;
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        b.add(player);
        switch (index) {
            case 0:
                int i, x;
                System.out.println("What's the position of the student on the card you want to move?");
                i = getIntInput();
                checkIntInput(0, 3, i, "What's the position of the student on the card you want to move?\n");
                a.add(inputInt.get(inputInt.size() - 1));
                System.out.println("What's the index of the island on which you want to move the student?");
                x = getIntInput();
                checkIntInput(1, 12, x, "What's the index of the island on which you want to move the student?");
                a.add(inputInt.get(inputInt.size() - 1));
                break;
            case 2:
                System.out.println("What's the index of the island you want to determine the influence of?");
                i = getIntInput();
                checkIntInput(1, 12, i, "What's the index of the island you want to determine the influence of?\n");
                a.add(inputInt.get(inputInt.size() - 1));
                break;
            case 4:
                System.out.println("What's the index of the island you want to put the counter on?");
                i = getIntInput();
                checkIntInput(1, 12, i, "What's the index of the island you want to put the counter on?\n");
                a.add(inputInt.get(inputInt.size() - 1));
                break;
            case 6:
                System.out.println("How many students do you want to swap?");
                i = getIntInput();
                checkIntInput(1, 3, i, "How many students do you want to swap?");
                int max = inputInt.get(inputInt.size() - 1);
                inputInt.remove(inputInt.size() - 1);
                System.out.println("What are the indexes on the Character card of the students you want to swap?");
                for (int z = 0; z < max; z++) {
                    i = getIntInput();
                    checkIntInput(0, 3, i, "What's the index?");
                    a.add(inputInt.get(inputInt.size() - 1));
                }
                c = new ArrayList<>();
                System.out.println("What are the indexes on the gate of the students you want to swap?");
                for (int z = 0; z < max; z++) {
                    i = getIntInput();
                    checkIntInput(0, 3, i, "What's the index?");
                    c.add(inputInt.get(inputInt.size() - 1));
                }
                break;
            case 8:
                System.out.println("What color would you like to disable?");
                String in = getStrInput();
                checkStrInput(in, "What color would you like to disable?");
                b.add(inputStr.get(inputStr.size() - 1));
                break;
            case 9:
                System.out.println("How many students would you like to swap?");
                i = getIntInput();
                checkIntInput(1, 2, i, "How many students would you like to swap?");
                max = inputInt.get(inputInt.size() - 1);
                inputInt.remove(inputInt.size() - 1);
                System.out.println("What are the indexes of the students on the gate you want to swap?");
                for (int z = 0; z < max; z++) {
                    i = getIntInput();
                    checkIntInput(0, 9, i, "What's the index?");
                    a.add(inputInt.get(inputInt.size() - 1));
                }
                System.out.println("Which colors would you like to swap in you hall?");
                for (int z = 0; z < max; z++) {
                    in = getStrInput();
                    checkStrInput(in, "Which color?");
                    b.add(inputStr.get(inputStr.size() - 1));
                }
                break;
            case 10:
                System.out.println("What is the index on the card of the student you want to add?");
                i = getIntInput();
                checkIntInput(0, 3, i, "What is the index on the card of the student you want to add?");
                a.add(inputInt.get(inputInt.size() - 1));
                break;
            case 11:
                System.out.println("What color would you like to affect?");
                in = getStrInput();
                checkStrInput(in, "Which color?");
                b.add(inputStr.get(inputStr.size() - 1));
            default:
                break;
        }
        msgHandler.send(new ActionMessage(a, b, c, 5));
        inputInt.clear();
        inputStr.clear();
    }

    private void messageConfirmed(int type) {
        msgHandler.send(new ActionMessage(inputInt, inputStr, null, type));
        inputInt.clear();
        inputStr.clear();
    }

    private int getIntInput() {
        Scanner s = new Scanner(System.in);
        inputInt.add(Integer.parseInt(s.nextLine()));
        return inputInt.get(inputInt.size() - 1);
    }

    private int getSingleIntInput(int b) {
        Scanner s = new Scanner(System.in);
        int res = Integer.parseInt(s.nextLine());
        while (res < 0 || res > b) {
            System.out.println("The number entered is not allowed.\n");
            res = Integer.parseInt(s.nextLine());
        }
        return res;
    }

    private String getStrInput() {
        Scanner s = new Scanner(System.in);
        inputStr.add(s.nextLine());
        return inputStr.get(inputInt.size() - 1);
    }

    private int checkIntInput(int a, int b, int input, String string) {
        while (input < a || input > b) {
            inputInt.remove(inputInt.size() - 1);
            System.out.println("The number entered is not allowed.\n" + string + "\n");
            input = getIntInput();
        }
        return input;
    }

    private void checkStrInput(String s, String c) {
        while (!s.equals("RED") && !s.equals("BLUE") && !s.equals("GREEN") && !s.equals("YELLOW") && !s.equals("PINK")) {
            inputStr.remove(inputStr.size() - 1);
            System.out.println("The color entered is not allowed.\n" + c + "\n");
            s = getStrInput();
        }
    }

    public String getValidString(String request) {
        //This method gets a non empty reply String while asking the "request"
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
