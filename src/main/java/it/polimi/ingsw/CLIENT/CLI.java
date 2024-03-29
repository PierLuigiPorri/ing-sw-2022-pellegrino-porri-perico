package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Runnable class that acts as the CLI View for the Client. Receives and handles the inputs from the user, and generates the output in the form of a command line.
 * @author GC56
 */
public class CLI implements View, Runnable {

    private final ClientMsgHandler msgHandler;
    private final ArrayList<Integer> inputInt;
    private final ArrayList<String> inputStr;

    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    private UpdateMessage update;

    public String nick;
    private boolean responseNeeded = false;
    private final Object lock;
    private String currentPhase = "";
    private String currentPlayer = "";
    private boolean kill = false;

    /**
     * Constructor method.
     */
    public CLI(ClientMsgHandler clientMsgHandler, Object lock) {
        this.msgHandler = clientMsgHandler;
        this.inputInt = new ArrayList<>();
        this.inputStr = new ArrayList<>();
        this.lock = lock;
    }

    /**
     * Starts the CLI and calls the first method in the chain to get the user's input.
     */
    @Override
    public void run() {
        msgHandler.setView(this);
        System.out.println(ANSI_PURPLE +
                "    __________  _______    _   __________  _______\n" +
                "   / ____/ __ \\/  _/   |  / | / /_  __/\\ \\/ / ___/\n" +
                "  / __/ / /_/ // // /| | /  |/ / / /    \\  /\\__ \\\n" + ANSI_CYAN +
                " / /___/ _, _// // ___ |/ /|  / / /     / /___/ /\n" +
                "/_____/_/ |_/___/_/  |_/_/ |_/ /_/     /_//____/\n"
                + ANSI_RESET);
        setNickname();
    }

    private void setNickname() {
        this.nick = getValidString("Enter the nickname you want to use");
        menu();
    }

    /**
     * Asks the user the fist input to determine the next method to call, receiving an index corresponding to the chosen action.
     */
    private void menu() {
        System.out.println("What would you like to do?" +
                "\n0:Create a new Game" +
                "\n1:Join a game" +
                "\n2: Change your nickname" +
                "\nDigit the appropriate number:");
        int g = getSingleIntInput(3);
        if (g == 0) {
            newGame();
        } else if (g == 1) {
            joinGame();
        } else if (g == 2) {
            setNickname();
        }
    }

    /**
     * Collects user input, then creates a request to create a new game and instructs the ClientMsgHandler class to send it to the Server. When a response from the
     * Server is notified, moves on to the next method.
     */
    private void newGame() {
        System.out.println("New Game");
        int gt; //Game Type
        int np; //Number of players
        gt = getCorrectInput("Digit 0 to use simplified rules or 1 to use expert rules", 0, 1);
        //Number of players request
        np = getCorrectInput("Digit 2 for a two-player game or 3 for a three-player game", 2, 3);
        try {
            msgHandler.send(new CreationMessage(0, nick, gt, np));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Waiting for other players and for the creation of the game");
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!msgHandler.getResponses().isEmpty()) {
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            if (lastMessage.allGood) {
                System.out.println(lastMessage.response);
                startGame();
            } else {
                System.out.println(lastMessage.response);
                menu();
            }
        }
    }

    /**
     * Collects user input, then creates a request to join a game and instructs ClientMsgHandler to send it to the Server.When a response from the
     * Server is notified, moves on to the next method.
     */
    private void joinGame() {
        int choice;
        choice = getCorrectInput("Digit 0 to join a random game or 1 to join a specific game with its ID", 0, 1);
        if (choice == 0) {
            try {
                msgHandler.send(new CreationMessage(1, nick));
                System.out.println("Great! The request has been sent");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 1) {
            int id;
            id = getValidInt();
            try {
                msgHandler.send(new CreationMessage(2, nick, id));
                System.out.println("Great! The request has been sent");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (msgHandler.getResponses().isEmpty()) {
            startGame();
        } else {
            ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
            System.out.println(lastMessage.response);
            menu();
        }
    }

    /**
     * Called when the Server has confirmed that the game has been created. Applies the first update and initializes the CLI.
     */
    private void startGame() {
        if (!msgHandler.getUpdates().isEmpty()) {
            UpdateMessage firstUpd = msgHandler.getUpdates().remove(msgHandler.getUpdates().size() - 1);
            System.out.println(firstUpd.update);
            inputStr.clear();
            inputInt.clear();
            update(firstUpd);
            initCLI();
        } else {
            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!msgHandler.getUpdates().isEmpty()) {
                UpdateMessage firstUpd = msgHandler.getUpdates().remove(msgHandler.getUpdates().size() - 1);
                firstUpd.update.forEach(System.out::println);
                inputStr.clear();
                inputInt.clear();
                update(firstUpd);
                initCLI();
            } else {
                if (!msgHandler.getResponses().isEmpty()) {
                    ResponseMessage lastMessage = msgHandler.getResponses().remove(msgHandler.getResponses().size() - 1);
                    System.out.println(lastMessage.response);
                    menu();
                }
            }
        }
    }


    /**
     * Main running method. Refreshes the message shown in the command line, and waits for a response from the server if one is needed.
     * Kills the game and goes back to menu() if instructed to.
     */
    public void initCLI() {
        while (!kill) {
            try {
                synchronized (lock) {
                    lock.wait(1000);
                }
                refresh();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (responseNeeded) {
                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            reload();
        }
        System.out.println("The game has been killed");
        this.kill = false;
        menu();
    }

    /**
     * Shows the last messages received by the server. Called when an action has been performed and before notifying to be ready to perform a new one.
     */
    public void reload() {
        synchronized (lock) {
            if (!msgHandler.getResponses().isEmpty()) {
                for (ResponseMessage rsp : msgHandler.getResponses()) {
                    System.out.println(ANSI_RED+rsp.response+ANSI_RESET);
                }
            }
            if (!msgHandler.getUpdates().isEmpty()) {
                for (UpdateMessage up : msgHandler.getUpdates()) {
                    up.update.forEach((u) -> System.out.println(ANSI_CYAN + u + ANSI_RESET));
                }
                update(msgHandler.getUpdates().get(msgHandler.getUpdates().size() - 1));
            }
            msgHandler.clearAllMessages();
        }
        if (this.update.gameEnded) {
            menu();
        } else {
            initCLI();
        }
    }

    /**
     * Overridden method from the View Interface.
     */
    @Override
    public void update(UpdateMessage update) {
        this.update = update;
    }


    /**
     * Main I/O method. Prints the user interface and asks the user for input, then calls the corresponding method to perform the request.
     * Shows to user only the performable action in every specific phase of the game.
     */
    public void refresh() {
        System.out.println("\n" + ANSI_CYAN + "*********************************************************************************" + ANSI_RESET);
        int choice;
        ArrayList<String> actions;
        if (!update.phase.equals(currentPhase)) {
            currentPhase = update.phase;
            if (currentPhase.equals("Planning"))
                introducePhase(0);
            else
                introducePhase(1);
        }
        System.out.println("\nPlayers that have to play, in order:" + update.order);
        if (update.phase.equals("Planning")) {    //Planning Phase
            if (!update.lastCardsPlayed.isEmpty()) {
                ArrayList<String> played = new ArrayList<>(update.players);
                played.removeAll(update.order);
                System.out.println("\nHere's the cards that have already been played (Movement,Value):");
                played.forEach(System.out::println);
                for (int i = 0; i < update.lastCardsPlayed.size(); i = i + 2) {
                    System.out.print(update.lastCardsPlayed.get(i) + ", " + update.lastCardsPlayed.get(i + 1) + "; ");
                }
            }
            if (update.order.get(0).equals(nick)) {             //Player's turn
                if (!currentPlayer.equals(update.order.get(0))) {
                    introduceTurn(0);
                    currentPlayer = update.order.get(0);
                }
                seeHand();
                actions = actions(0);
            } else {                                            //not Player's turn
                if (!currentPlayer.equals(update.order.get(0))) {
                    introduceTurn(1);
                    currentPlayer = update.order.get(0);
                }
                actions = actions(1);
            }
        } else {                                                     //Action Phase
            if (update.order.get(0).equals(nick) && update.playersMoves.get(0) != 0) {  //Player's turn, student managing
                if (!currentPlayer.equals(update.order.get(0))) {
                    introduceTurn(3);
                    currentPlayer = update.order.get(0);
                }
                actions = actions(3);
            } else if (update.order.get(0).equals(nick) && !update.cloudtaken) {                   //Player's turn, cloud sub-phase
                introduceTurn(4);
                actions = actions(4);
            } else if (update.order.get(0).equals(nick)) {                              //Player's turn, MN sub-phase
                introduceTurn(5);
                actions = actions(5);
            } else {                                                                     //not Player's turn
                if (!currentPlayer.equals(update.order.get(0))) {
                    introduceTurn(2);
                    currentPlayer = update.order.get(0);
                }
                actions = actions(2);
            }
        }
        actions.forEach((el) -> System.out.println(actions.indexOf(el) + "->" + el));
        choice = getSingleIntInput(actions.size());
        perform(actions.get(choice));
    }

    /**
     * Overridden method from the View interface.
     */
    @Override
    public void signalUpdate() {
        System.out.println(ANSI_CYAN + "Something's happened in the meantime! Hit Refresh!" + ANSI_RESET);
    }

    /**
     * Overridden method from the View interface.
     */
    @Override
    public void signalResponse() {
        System.out.println("ERRORE!");
    }

    /**
     * Contains the String parameters to be shown to the user in specific phases of the game.
     * @param phase The current phase of the game, as String.
     */
    private void introducePhase(int phase) {
        System.out.println("\nTurn " + update.turnNumber + "!");
        switch (phase) {
            case 0:
                System.out.println("\n" + ANSI_CYAN + "Planning Time!" +
                        "\nNow's your chance to, you know, plan." +
                        "\nYou should all play a card. The best stuff happens later." + ANSI_RESET);
                break;
            case 1:
                System.out.println("\n" + ANSI_CYAN + "Action time!" +
                        "\nThis is the big league. Now is when the game is decided. Every round. Let's go!" +
                        "\nMove students. Activate special effects. Move digital imaginary tokens. Your call." + ANSI_RESET);
        }
    }

    /**
     * Shows to the user the correct String based on the turn characteristics.
     * @param turn The turn information needed. Int parameter between 0-5.
     */
    private void introduceTurn(int turn) {
        switch (turn) {
            case 0://Player's turn, Planning phase
                System.out.println("\n" + ANSI_CYAN + "Now! Fire your card! Shape you destiny with a few single digit numbers!" +
                        "\nRemember, you can't play a card that has already been played this round. Just don't." + ANSI_RESET);
                break;
            case 1://Planning phase, not player's turn
                System.out.println("\n" + ANSI_CYAN + "It's not your time to play a card. Hold..." +
                        "\nWanna do something in the mean time? Digit the appropriate number and we'll do that for you:" + ANSI_RESET);
                break;
            case 2://Action phase, not player's turn
                System.out.println("\n" + ANSI_CYAN + "Not your time to shine yet, somebody else is playing." +
                        "\nBe ready for when your turn comes." +
                        "\nIn the mean time, wanna do something? Digit the appropriate number and we'll do that for you:" + ANSI_RESET);
                break;
            case 3://Action phase, player's turn
                System.out.println("\n" + ANSI_CYAN + "Your turn!" +
                        "\nGo" +
                        "\nDo stuff!" +
                        "\nYou know what to do. If you don't, here's a reminder." +
                        "\nDigit the appropriate number and we'll do that for you:" + ANSI_RESET);
                break;
            case 4://End of action phase, player's turn, cloud sub-phase
                System.out.println("\n" + ANSI_CYAN + "You moved your students, you get new ones. That's how it works. Go get some from a cloud!" + ANSI_RESET);
                break;
            case 5://End of action phase, player's turn, MN movement
                System.out.println("\n" + ANSI_CYAN + "OK! Good student managing. Now let's end this round. " +
                        "\nTime to politely ask Lady Mother Nature to relocate on an Island of your choosing." + ANSI_RESET);
                break;
        }
    }

    /**
     * Performs the requested action, by calling the corresponding method.
     * WARNING: Might contain unwanted actions! (It's an Easter egg don't worry).
     * @param request String parameter containing the request.
     */
    private void perform(String request) {
        switch (request) {
            case "Refresh":
                responseNeeded = false;
                break;
            case "Play a card":
                responseNeeded = true;
                playCard();
                break;
            case "See other players' boards":
                responseNeeded = false;
                seeOtherBoards();
                break;
            case "See board (islands and clouds)":
                responseNeeded = false;
                seeBoard();
                break;
            case "See hand":
                responseNeeded = false;
                seeHand();
                break;
            case "See Characters":
                responseNeeded = false;
                seeCharacters();
                break;
            case "See your board":
                responseNeeded = false;
                seeOwnBoard();
                break;
            case "Move Mother Nature":
                responseNeeded = true;
                moveMotherNature();
                break;
            case "Dissimulate all pancakes in a 3 km radius":
                responseNeeded = false;
                System.out.println("\n...What?");
                break;
            case "Get students from a Cloud":
                responseNeeded = true;
                cloudToGate();
                break;
            case "See other players' hands":
                responseNeeded = false;
                System.out.println("\nSure! Wait...no! Absolutely not! How dare you question our integrity. Shame on you.");
                break;
            case "Win instantly":
                responseNeeded = false;
                System.out.println("\nYeah, you'd like that, wouldn't you. Too bad this option doesn't exist." +
                        "It was specifically designed to mock you." +
                        "Hurry up, people are waiting.");
                break;
            case "Ask support for all these hidden options that keep appearing":
                responseNeeded = false;
                System.out.println("\nYeah, yeah, we know about that. We don't know why it keeps happening." +
                        "We don't know how to fix it." +
                        "We're a little afraid of what might come up.");
                break;
            case "Bribe a professor of your choice":
                responseNeeded = false;
                System.out.println("\nBuddy...that would maybe work if you had money. You have what, " + update.coinsOnPlayer.get(update.players.indexOf(nick)) + " coins?" +
                        "That's not gonna cut it.");
                break;
            case "Move a student from the gate to an Island":
                responseNeeded = true;
                gateToIsland();
                break;
            case "Move a student from the gate to your Hall":
                responseNeeded = true;
                gateToHall();
                break;
            case "Activate a Character":
                responseNeeded = true;
                activateCharacter();
                break;
            case "Exit game":
                responseNeeded = false;
                exitGame();
            default:
                break;
        }
    }

    /**
     * Contains the actions that could be requested by the user. Builds an ArrayList with the performable actions based on the turn characteristics.
     * @param spot Integer value containing the information needed. Between 0-5.
     * @return Returns an ArrayList of Strings to be shown to the user.
     */
    private ArrayList<String> actions(int spot) {
        ArrayList<String> list = new ArrayList<>();
        LocalTime time = LocalTime.now();
        list.add("See your board");
        list.add("See other players' boards");
        list.add("See board (islands and clouds)");
        list.add("See hand");
        if (update.game_Type == 1)
            list.add("See Characters");
        switch (spot) {
            case 0://Player's turn, Planning phase
                list.add("Play a card");
                if (time.getSecond() % 3 == 0)
                    list.add("Dissimulate all pancakes in a 3 km radius");
                break;
            case 1://Planning phase, not player's turn
            case 2://Action phase, not player's turn
                break;
            case 3://Action phase, player's turn
                if (time.getSecond() % 3 == 0)
                    list.add("See other players' hands");
                if (time.getSecond() % 7 == 0)
                    list.add("Win instantly");
                list.add("Move a student from the gate to an Island");
                list.add("Move a student from the gate to your Hall");
                if (update.game_Type == 1) {
                    list.add("Activate a Character");
                }
                break;
            case 4://Cloud sub-phase, player's turn
                if (time.getSecond() % 3 == 0)
                    list.add("Ask support for all these hidden options that keep appearing");
                if (time.getSecond() % 7 == 0)
                    list.add("Bribe a professor of your choice");
                list.add("Get students from a Cloud");
                break;
            case 5://End of action phase, player's turn
                list.add("Move Mother Nature");
                break;
        }
        list.add("Refresh");
        list.add("Exit game");
        return list;
    }

    /**
     * Overridden method from the View Interface.
     */
    public void moveMotherNature() {
        //this method needs the movement [int].
        int i;
        System.out.println("How many jumps forward you want Mother Nature to do?");
        i = getIntInput();
        if (i != -1) {
            checkIntInput(0, 7, i, "How many jumps forward you want Mother Nature to do?\n");
            messageConfirmed(3);
        } else {
            responseNeeded = false;
        }
    }

    /**
     * Overridden method from the View Interface.
     */
    public void gateToIsland() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index from the gate and the index of the island. [String, String, int, int]
        seeOwnBoard();
        int i;
        System.out.println("Which is the position of the student you want to move?");
        i = getIntInput();
        if (i != -1) {
            checkIntInput(0, 10, i, "Which is the position of the student you want to move?\n");
            System.out.println("In which island do you want to move your student?");
            i = getIntInput();
            if (i != -1) {
                checkIntInput(1, 12, i, "In which island do you want to move your student?\n");
                messageConfirmed(0);
            } else {
                inputInt.clear();
                responseNeeded = false;
            }
        } else {
            responseNeeded = false;
        }
    }

    /**
     * Overridden method from the View Interface.
     */
    public void gateToHall() {
        //this method needs the name of the player who calls it, the color of the student to move.
        String s;
        seeOwnBoard();
        System.out.println("Which is the color of the student you want to move? ");
        s = getStrInput();
        if (!s.equals("-1")) {
            checkStrInput(s, "Which is the color of the student you want to move? ");
            messageConfirmed(1);
        } else {
            responseNeeded = false;
        }
    }

    /**
     * Overridden method from the View Interface.
     */
    public void cloudToGate() {
        // this method needs the name of the player who calls it, the color of the student to move,
        // the index of the student in the cloud and the index of the cloud.
        System.out.println("\n\n                " + ANSI_CYAN + "CLOUDS" + ANSI_RESET + "           ");
        for (int index : update.studentsOnCloud.keySet()) {
            System.out.print("\nCloud " + index + ":");
            for (int i = 0; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                System.out.print(update.studentsOnCloud.get(index).get(i) + update.studentsOnCloud.get(index).get(i + 1) + ANSI_RESET + " ");
            }
        }
        int i;
        System.out.println("Which cloud do you want to take students from?");
        i = getIntInput();
        if (i != -1) {
            checkIntInput(0, 3, i, "Which cloud do you want to take students from?\n");
            messageConfirmed(2);
        } else responseNeeded = false;
    }

    /**
     * Overridden method from the View Interface.
     */
    public void playCard() {
        // this method needs the name of the player who calls it, the index of the card to play.
        int i;
        System.out.println("Which card do you want to play?");
        i = getIntInput();
        if (i != -1) {
            checkIntInput(0, 9, i, "Which card do you want to play?\n");
            messageConfirmed(4);
        } else responseNeeded = false;
    }

    /**
     * Overridden method from the View Interface.
     */
    public void activateCharacter() {
        boolean cancel = false;
        System.out.println("Which character would you like to activate? Digit the appropriate index:");
        seeCharacters();
        int index = getSingleIntInput(11);
        ArrayList<Integer> a = new ArrayList<>(), c = null;
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        b.add(nick);
        switch (index) {
            case 0:
                int i, x;
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What's the position of the student on the card you want to move?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(0, 3, i, "What's the position of the student on the card you want to move?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                    System.out.println("What's the index of the island on which you want to move the student?");
                    x = getIntInput();
                    if (x == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(1, 12, x, "What's the index of the island on which you want to move the student?");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else
                    System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 1:
            case 7:
            case 3:
            case 5:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) < characterCost(index))
                    System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 2:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What's the index of the island you want to determine the influence of?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(1, 12, i, "What's the index of the island you want to determine the influence of?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 4:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What's the index of the island you want to put the counter on?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(1, 12, i, "What's the index of the island you want to put the counter on?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 6:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("How many students do you want to swap?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
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
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 8:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What color would you like to disable?");
                    String in = getStrInput();
                    if (in.equals("-1")) {
                        cancel = true;
                        break;
                    }
                    checkStrInput(in, "What color would you like to disable?");
                    b.add(inputStr.get(inputStr.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 9:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("How many students would you like to swap?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(1, 2, i, "How many students would you like to swap?");
                    int max = inputInt.get(inputInt.size() - 1);
                    inputInt.remove(inputInt.size() - 1);
                    System.out.println("What are the indexes of the students on the gate you want to swap?");
                    for (int z = 0; z < max; z++) {
                        i = getIntInput();
                        checkIntInput(0, 9, i, "What's the index?");
                        a.add(inputInt.get(inputInt.size() - 1));
                    }
                    System.out.println("Which colors would you like to swap in you hall?");
                    for (int z = 0; z < max; z++) {
                        String in = getStrInput();
                        checkStrInput(in, "Which color?");
                        b.add(inputStr.get(inputStr.size() - 1));
                    }
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 10:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What is the index on the card of the student you want to add?");
                    i = getIntInput();
                    if (i == -1) {
                        cancel = true;
                        break;
                    }
                    checkIntInput(0, 3, i, "What is the index on the card of the student you want to add?");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 11:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What color would you like to affect?");
                    String in = getStrInput();
                    if (in.equals("-1")) {
                        cancel = true;
                        break;
                    }
                    checkStrInput(in, "Which color?");
                    b.add(inputStr.get(inputStr.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            default:
                break;
        }
        if (!cancel) {
            msgHandler.send(new ActionMessage(a, b, c, 5));
        } else {
            responseNeeded = false;
        }
        inputInt.clear();
        inputStr.clear();
    }

    /**
     * Prints the information regarding the board of a chosen player.
     * @param choice Integer value, corresponding to a specific player.
     */
    private void seePlayerBoards(int choice) {
        System.out.println("\n" + ANSI_CYAN + "GATE:" + ANSI_RESET);
        for (int i = 0; i < update.gatePlayer.get(choice).size(); i = i + 2) {
            System.out.print(update.gatePlayer.get(choice).get(i) + update.gatePlayer.get(choice).get(i + 1) + ANSI_RESET + " ");
        }
        System.out.println("\n" + ANSI_CYAN + "HALL:" + ANSI_RESET);
        System.out.println("\n      " + ANSI_CYAN + "STUDENTS" + ANSI_RESET + "          " + ANSI_CYAN + "PROFESSORS" + ANSI_RESET + "\n");
        System.out.println(ANSI_RED + "RED:      " + ANSI_RESET + update.hallPlayer.get(choice).get(0) + "                  " + (update.professors.get(choice).get(0) ? "YES" : "NO"));
        System.out.println(ANSI_BLUE + "BLUE:     " + ANSI_RESET + update.hallPlayer.get(choice).get(1) + "                  " + (update.professors.get(choice).get(1) ? "YES" : "NO"));
        System.out.println(ANSI_GREEN + "GREEN:    " + ANSI_RESET + update.hallPlayer.get(choice).get(2) + "                  " + (update.professors.get(choice).get(2) ? "YES" : "NO"));
        System.out.println(ANSI_YELLOW + "YELLOW:   " + ANSI_RESET + update.hallPlayer.get(choice).get(3) + "                  " + (update.professors.get(choice).get(3) ? "YES" : "NO"));
        System.out.println(ANSI_PURPLE + "PINK:     " + ANSI_RESET + update.hallPlayer.get(choice).get(4) + "                  " + (update.professors.get(choice).get(4) ? "YES" : "NO"));
        System.out.println("\nTowers left:" + update.towersOnPlayer.get(choice) + ((update.game_Type == 1) ? "   Coins left:" + update.coinsOnPlayer.get(choice) : ""));
    }

    /**
     * Handles the request to see the boards of other players.
     */
    private void seeOtherBoards() {
        System.out.println("\nSure! Which board would you like to see? As always, digit the appropriate number:");
        for (String n : update.players) {
            if (!Objects.equals(n, nick)) {
                System.out.println(update.players.indexOf(n) + "->" + n);
            }
        }
        int choice = getSingleIntInput(update.nPlayers - 1);
        if (choice != -1) {
            System.out.println("\nOK! Here's what " + update.players.get(choice) + " has:");
            seePlayerBoards(choice);
        }
    }

    /**
     * Handles the request to see the board of the user.
     */
    private void seeOwnBoard() {
        System.out.println("\nHere's what you have:");
        seePlayerBoards(update.players.indexOf(nick));
    }

    /**
     * Prints the game board as it is in the stored update.
     */
    private void seeBoard() {
        System.out.println("\nSure! Here's what we're at:");
        System.out.println("\n                " + ANSI_CYAN + "ISLANDS" + ANSI_RESET + (update.game_Type == 1 ? "(if you see a " + ANSI_RED + "[X]" + ANSI_RESET + " it means there's a Prohibition counter there!)" : ""));
        for (int index : update.studentsOnIsland.keySet()) {
            System.out.print("\nIsland " + index + (update.game_Type == 1 ? (update.numTDOnIsland.get(index - 1) ? (ANSI_RED + "[X]" + ANSI_RESET) : "") : "") + ":");
            for (int i = 0; i < update.studentsOnIsland.get(index).size(); i = i + 2) {
                System.out.print(update.studentsOnIsland.get(index).get(i) + update.studentsOnIsland.get(index).get(i + 1) + ANSI_RESET + " ");
            }
            System.out.print((update.motherNatureOnIsland.get(index - 1) ? (" " + ANSI_CYAN + " <----Mother Nature is here! Say hello!" + ANSI_RESET + "") : ""));
            System.out.print(update.towersOnIsland.get(index - 1) != 0 ? ("\n             ->" + update.towersOnIsland.get(index - 1) + "Tower" + (update.towersOnIsland.get(index - 1) != 1 ? "s" : "") + "here, owned by " + update.whoOwnTowers.get(index - 1)) + "!" : "");
        }
        System.out.println("\n\n                " + ANSI_CYAN + "CLOUDS" + ANSI_RESET + "           ");
        for (int index : update.studentsOnCloud.keySet()) {
            System.out.print("\nCloud " + index + ":");
            for (int i = 0; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                System.out.print(update.studentsOnCloud.get(index).get(i) + update.studentsOnCloud.get(index).get(i + 1) + ANSI_RESET + " ");
            }
        }
    }

    /**
     * Prints the user's hand.
     */
    private void seeHand() {
        System.out.println("\nHere's your hand(Index->Movement,Value):");
        int ind = 0;
        for (int i = 0; i < update.handPlayer.get(update.players.indexOf(nick)).size(); i = i + 2) {
            System.out.print(ind + "->" + update.handPlayer.get(update.players.indexOf(nick)).get(i) + "," + update.handPlayer.get(update.players.indexOf(nick)).get(i + 1) + ";  ");
            ind++;
        }
        System.out.println("\n");
    }

    /**
     * Handles the request to exit the game.
     */
    private void exitGame() {
        String resp = getValidString("So...are you sure about this? [Y/N]");
        if (resp.equals("Y")) {
            msgHandler.send(new KillMessage());
            setKill();
        }
    }

    /**
     * Prints the Characters in play.
     */
    private void seeCharacters() {
        System.out.println("\nThese guys can give you the boost you need to win! Here's what we've got today:");
        System.out.println("\nIf the cost is " + ANSI_CYAN + "cyan" + ANSI_RESET + ", it means that Character has already been used!");
        System.out.println("\n" + ANSI_CYAN + "INDEX" + ANSI_RESET + "     " + ANSI_CYAN + "COST" + ANSI_RESET + "        " + ANSI_CYAN + "EFFECT" + ANSI_RESET);
        for (int i : update.idCharacter) {
            System.out.println("\n" + i + "         " + (update.activated.get(update.idCharacter.indexOf(i)) ? ANSI_CYAN : "") + characterCost(i) + (update.activated.get(update.idCharacter.indexOf(i)) ? ANSI_RESET : "") + "           " + characterEffect(i));
            if (i == 0 || i == 6 || i == 10) {
                System.out.print("                                    ->Students currently on card:");
                for (int x = 0; x < update.studentsOnCard.get(update.idCharacter.indexOf(i)).size(); x = x + 2) {
                    System.out.print(update.studentsOnCard.get(update.idCharacter.indexOf(i)).get(x) + update.studentsOnCard.get(update.idCharacter.indexOf(i)).get(x + 1) + ANSI_RESET + " ");
                }
            } else if (i == 4) {
                System.out.println("                                    ->Prohibition counters currently on this card:" + update.numTD);
            }
        }
    }

    /**
     * Stores and returns the Characters' effects.
     * @param index The index of the chosen Character. Between 0-11.
     * @return Returns a String containing a description of the Character's effect.
     */
    private String characterEffect(int index) {
        switch (index) {
            case 0:
                return "You can take a student from this card and place it on an Island of your choosing! Don't worry, the student on the card will replace itself!";
            case 1:
                return "This guy lets you take control of a Professor even if you have the same number of corresponding students in the Hall, until the end of this turn!";
            case 2:
                return "Perform an extraordinary calculation of the influence on an Island of your choosing!";
            case 3:
                return "This guy gives you a bonus of +2 on your Mother Nature potential movement!";
            case 4:
                return "You can take a Prohibition counter from this card and place it on an Island of your choosing! It'll prevent Mother Nature from calculating the influence there, but just once!";
            case 5:
                return "This guy disables the towers' influence on the Islands, until the end of the turn! They will count as 0!";
            case 6:
                return "You can take up to 3 students from this card, and swap them with the same number of students in your Gate!";
            case 7:
                return "This guy gives you a bonus of +2 when calculating the influence this turn!";
            case 8:
                return "This guy disables the influence of 1 color of your choosing! Every student of that color will count as 0!";
            case 9:
                return "You can take up to 2 students from your Gate and swap them with the same number of students in your Hall!";
            case 10:
                return "You can take a student from this card and place it directly in your Hall! Don't worry, the student on the card will replace itself!";
            case 11:
                return "This guy takes away 3 students (or up to 3 if they have less) of a color of your choosing from EVERYONE's Hall!";
            default:
                return "";
        }
    }

    /**
     * Stores and returns the Characters' costs.
     * @param index The index of the chosen Character. Between 0-11.
     * @return Returns the cost of the Character, handling the case in which it has been already activated.
     */
    private int characterCost(int index) {
        switch (index) {
            case 0:
            case 3:
            case 6:
            case 9:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 2 : 1;
            case 1:
            case 4:
            case 7:
            case 10:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 3 : 2;
            case 2:
            case 5:
            case 8:
            case 11:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 4 : 3;
            default:
                return 0;
        }
    }

    /**
     * Creates the message and passes it to the msgHandler to be sent to the Server.
     * @param type The type of message to be created. Integer value between 0-5.
     */
    private void messageConfirmed(int type) {
        ArrayList<String> mex = new ArrayList<>();
        mex.add(nick);
        mex.addAll(inputStr);
        ArrayList<Integer> inter = new ArrayList<>(inputInt);
        msgHandler.send(new ActionMessage(inter, mex, null, type));
        inputInt.clear();
        inputStr.clear();
    }

    public void setKill() {
        this.kill = true;
    }

    /**
     * Handles input and checks if it's valid, then adds it to the global Integer array to be used later.
     * @return The correct input. Integer value.
     */
    private int getIntInput() {
        try {
            Scanner s = new Scanner(System.in);
            int in = Integer.parseInt(s.nextLine());
            if (in != -1) {
                inputInt.add(in);
            }
            return in;
        } catch (NumberFormatException e) {
            return getIntInput();
        }
    }

    /**
     * Handles input and checks if it's in a certain range.
     * @param b The integer right bound of the range.
     * @return The correct input. Integer value.
     */
    private int getSingleIntInput(int b) {
        try {
            Scanner s = new Scanner(System.in);
            int res = Integer.parseInt(s.nextLine());
            while (res < -1 || res > b) {
                System.out.println("The number entered is not allowed.");
                res = Integer.parseInt(s.nextLine());
            }
            return res;
        } catch (NumberFormatException e) {
            return getSingleIntInput(b);
        }
    }

    /**
     * Handles input and checks if it's valid, then adds it to the global String Arraylist to be used later.
     * @return The correct input. String value.
     */
    private String getStrInput() {
        Scanner s = new Scanner(System.in);
        String in = s.nextLine();
        if (!in.equals("-1")) {
            inputStr.add(in.toUpperCase());
            return in.toUpperCase();
        } else return in;
    }

    /**
     * Handles input and checks if it's in a certain range, then adds it to the global Arraylist to be used later.
     * @param a The left int bound.
     * @param b The right int bound.
     * @param input The input. Integer value.
     * @param string The request to be shown to the user. String value.
     */
    private void checkIntInput(int a, int b, int input, String string) {
        while (input < a || input > b) {
            inputInt.remove(inputInt.size() - 1);
            System.out.println("The number entered is not allowed.\n" + string);
            input = getIntInput();
        }
    }

    /**
     * Handles input and checks if it's a correct color, then adds it to the global Arraylist to be used later.
     * @param s The input. String value.
     * @param c The request to be shown to be user. String value.
     */
    private void checkStrInput(String s, String c) {
        while (!s.equals("RED") && !s.equals("BLUE") && !s.equals("GREEN") && !s.equals("YELLOW") && !s.equals("PINK")) {
            inputStr.remove(inputStr.size() - 1);
            System.out.println("The color entered is not allowed.\n" + c);
            s = getStrInput();
        }
    }

    /**
     * This method gets a non-empty reply String while asking the "request"
     *
     */
    private String getValidString(String request) {
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

    /**
     * This method gets a valid int while asking the "request"
     */
    private int getValidInt() {
        boolean inv = true; //Input Not Valid
        String input;
        int n = 0;
        Scanner s = new Scanner(System.in);
        while (inv) {
            System.out.println("What's the ID of the game you wanna join?");
            try {
                input = s.nextLine();
                n = Integer.parseInt(input);
                inv = false;
            } catch (Exception e) {
                System.out.println("Input is not valid");
            }
        }
        return n;
    }

    private int getCorrectInput(String request, int a, int b) {
        //This method gets correct input from the client of 2 possible integer values: a, b while asking the "request"
        boolean inv = true; //Input Not Valid
        String input = null;
        Scanner s = new Scanner(System.in);
        while (inv) {
            System.out.println(request);
            try {
                input = s.nextLine();
                if (Integer.parseInt(input) == a || Integer.parseInt(input) == b) {
                    inv = false;
                } else {
                    System.out.println("Input is not valid");
                }
            } catch (Exception e) {
                System.out.println("Input is not valid");
            }
        }
        return Integer.parseInt(input);
    }
}