package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CLI implements View, Runnable{

    private final ClientMsgHandler msgHandler;
    private final ArrayList<Integer> inputInt;
    private final ArrayList<String> inputStr;
    private final ArrayList<MessageType> messages;

    private UpdateMessage update;

    public String nick;
    private boolean responseNeeded;
    private final Object lock;

    public CLI(ClientMsgHandler clientMsgHandler, Object lock) {
        this.msgHandler = clientMsgHandler;
        this.inputInt = new ArrayList<>();
        this.inputStr = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.lock=lock;
    }

    @Override
    public void run() {
        msgHandler.setView(this);
        System.out.println(
                "    __________  _______    _   __________  _______\n" +
                        "   / ____/ __ \\/  _/   |  / | / /_  __/\\ \\/ / ___/\n" +
                        "  / __/ / /_/ // // /| | /  |/ / / /    \\  /\\__ \\\n" +
                        " / /___/ _, _// // ___ |/ /|  / / /     / /___/ /\n" +
                        "/_____/_/ |_/___/_/  |_/_/ |_/ /_/     /_//____/\n"
        );
        this.nick = getValidString("What's your name?");
        System.out.println("What would you like to do?" +
                "\n0:Create a new Game" +
                "\n1:Join a game" +
                "\n2: See all available games" +
                "\nDigit the appropriate number:");
        int g = getSingleIntInput(2); //0=New Game, 1=Join Game, 2=See Available Games
        if (g == 0) {
            newGame();
        } else if (g == 1) {
            joinGame();
        } else if (g == 2) {
            seeAvailableGames();
        }
    }

    private void newGame() {
        System.out.println("New Game");
        //new Thread(() -> {
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
        //});
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Prova");
        if (!messages.isEmpty()) {
            ResponseMessage lastMessage = (ResponseMessage) messages.remove(messages.size() - 1);
            if (lastMessage.allGood) {
                System.out.println(lastMessage.response);
                startGame();
            } else {
                System.out.println(lastMessage.response);
                newGame();
            }
        }
    }

    private void joinGame() {
        //new Thread(() -> {
            int choice;
            choice = getCorrectInput("Digit 0 to join a random game or 1 to join a specific game with its ID", 0, 1);
            if (choice == 0) {
                try {
                    msgHandler.send(new CreationMessage(1, nick));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (choice == 1) {
                int id;
                id = getValidInt("What's the ID of the game you wanna join?");
                try {
                    msgHandler.send(new CreationMessage(2, nick, id));
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
            if (!messages.isEmpty()) {
                ResponseMessage lastMessage = (ResponseMessage) messages.remove(messages.size() - 1);
                if (lastMessage.allGood) {
                    System.out.println(lastMessage.response);
                    startGame();
                } else {
                    System.out.println(lastMessage.response);
                    joinGame();
                }
            }
        //});
    }

    private void startGame(){
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!messages.isEmpty()) {
            UpdateMessage firstUpd = (UpdateMessage) messages.remove(messages.size() - 1);
            System.out.println(firstUpd.update);
            update(firstUpd);
            initCLI();
        }
    }

    private void seeAvailableGames() {
        //TODO
        System.out.println("Questa funzione non esiste per ora");
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
                if (lastmessage.type == 4) {
                    UpdateMessage up = (UpdateMessage) lastmessage;
                    System.out.println(up.update);
                    messages.remove(messages.size() - 1);
                    update((up));
                } else {
                    System.out.println(((ResponseMessage) lastmessage).response);
                    initCLI();
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
                    "\nThe clouds have been refilled." +
                    "\nNow's your chance to, you know, plan." +
                    "\nYou should all play a card. The best stuff happens later.");
            seeOwnBoard();
            System.out.println("\nTurn " + update.turnNumber + "!");
            System.out.println("\nPlayers that have to play, in order:" + update.order);
            //TODO:fixare l'ordine di stampa delle carte
            if (!update.lastCardPlayed.isEmpty()) {
                ArrayList<String> played = new ArrayList<>(update.players);
                played.removeAll(update.order);
                System.out.println("\nHere's the cards that have already been played (Movement,Value):");
                System.out.println(played);
                System.out.println(update.lastCardPlayed);
            }
            if (update.order.get(0).equals(nick)) {
                System.out.println("\nNow! Fire your card! Shape you destiny with a few single digit numbers!" +
                        "\nRemember, you can't play a card that has already been played this round. Just don't.");
                seeHand();
                actions(0).forEach((el) -> System.out.println(actions(0).indexOf(el) + ":" + el));
                choice = getSingleIntInput(actions(0).size());
                perform(actions(0).get(choice));
            } else {
                System.out.println("\nIt's not your time to play a card yet. Hold..." +
                        "\nWanna do something in the mean time? Digit the appropriate number and we'll do that for you:");
                actions(1).forEach((el) -> System.out.println(actions(1).indexOf(el) + ":" + el));
                choice = getSingleIntInput(actions(1).size());
                perform(actions(1).get(choice));
            }
        } else {
            System.out.println("\nAction time!" +
                    "\nThis is the big league. Now is when the game is decided. Every round. Let's go!" +
                    "\nMove students. Activate special effects. Move digital imaginary tokens. Your call.");
            System.out.println("\nPlayers that have to play, in order:" + update.order);
            if (update.order.get(0).equals(nick) && update.playersMoves.get(0) != 0) {
                System.out.println("\nYour turn!" +
                        "\nGo" +
                        "\nDo stuff!" +
                        "\nYou know what to do. If you don't, here's a reminder." +
                        "\nDigit the appropriate number and we'll do that for you:");
                actions(3).forEach((el) -> System.out.println(actions(3).indexOf(el) + ":" + el));
                choice = getSingleIntInput(actions(3).size());
                perform(actions(3).get(choice));
            } else if (update.order.get(0).equals(nick)) {
                System.out.println("\nOK! Good student managing. Now let's end this round. " +
                        "\nTime to politely ask Lady Mother Nature to relocate on an Island of your choosing." +
                        "\nAnd don't forget to choose a cloud to take students from!");
                actions(4).forEach((el) -> System.out.println(actions(4).indexOf(el) + ":" + el));
                choice = getSingleIntInput(actions(4).size());
                perform(actions(3).get(choice));
            } else {
                System.out.println("\nNot your time to shine yet, somebody else is playing." +
                        "\nBe ready for when your turn comes." +
                        "\nIn the mean time, wanna do something? Digit the appropriate number and we'll do that for you:");
                actions(2).forEach((el) -> System.out.println(actions(2).indexOf(el) + ":" + el));
                choice = getSingleIntInput(actions(2).size());
                perform(actions(2).get(choice));
            }
        }
    }

    private void perform(String request) throws IOException {
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
            default:
                break;
        }
    }

    private ArrayList<String> actions(int spot) {
        ArrayList<String> list = new ArrayList<>();
        LocalTime time = LocalTime.now();
        switch (spot) {
            case 0://Player's turn, Planning phase
                list.add("Play a card");
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                if (time.getSecond() % 3 == 0)
                    list.add("Dissimulate all pancakes in a 3 km radius");
                if (update.game_Type == 1)
                    list.add("See Characters");
                list.add("Refresh");
                break;
            case 1://Planning phase, not player's turn
            case 2://Action phase, not player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                if (update.game_Type == 1)
                    list.add("See Characters");
                list.add("Refresh");
                break;
            case 3://Action phase, player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                if (time.getSecond() % 3 == 0)
                    list.add("See other players' hands");
                if (time.getSecond() % 7 == 0)
                    list.add("Win instantly");
                list.add("Move a student from the gate to an Island");
                list.add("Move a student from the gate to your Hall");
                if (update.game_Type == 1) {
                    list.add("Activate a Character");
                    list.add("See Characters");
                }
                list.add("Refresh");
                break;
            case 4://End of action phase, player's turn
                list.add("See other players' boards");
                list.add("See board (islands and clouds)");
                list.add("See hand");
                if (update.game_Type == 1)
                    list.add("See Characters");
                if (time.getSecond() % 3 == 0)
                    list.add("Ask support for all these hidden options that keep appearing");
                if (time.getSecond() % 7 == 0)
                    list.add("Bribe a professor of your choice");
                list.add("Get students from a Cloud");
                list.add("Move Mother Nature");
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
                    checkIntInput(0, 3, i, "What's the position of the student on the card you want to move?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                    System.out.println("What's the index of the island on which you want to move the student?");
                    x = getIntInput();
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
                    checkIntInput(1, 12, i, "What's the index of the island you want to determine the influence of?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 4:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What's the index of the island you want to put the counter on?");
                    i = getIntInput();
                    checkIntInput(1, 12, i, "What's the index of the island you want to put the counter on?\n");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 6:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
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
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 8:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What color would you like to disable?");
                    String in = getStrInput();
                    checkStrInput(in, "What color would you like to disable?");
                    b.add(inputStr.get(inputStr.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 9:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("How many students would you like to swap?");
                    i = getIntInput();
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
                    checkIntInput(0, 3, i, "What is the index on the card of the student you want to add?");
                    a.add(inputInt.get(inputInt.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            case 11:
                if (update.coinsOnPlayer.get(update.players.indexOf(nick)) >= characterCost(index)) {
                    System.out.println("What color would you like to affect?");
                    String in = getStrInput();
                    checkStrInput(in, "Which color?");
                    b.add(inputStr.get(inputStr.size() - 1));
                } else System.out.println("Well...seems like you're too poor for this. Sorry.");
                break;
            default:
                break;
        }
        msgHandler.send(new ActionMessage(a, b, c, 5));
        inputInt.clear();
        inputStr.clear();
    }

    private void seePlayerBoards(int choice) {
        System.out.println("\nGATE:" + update.gatePlayer.get(choice));
        System.out.println("\n********************************************************");
        System.out.println("\n      STUDENTS          PROFESSORS");
        System.out.println("\nRED:" + update.hallPlayer.get(choice).get(0) + "        " + (update.professors.get(choice).get(0) ? "YES" : "NO"));
        System.out.println("\nBLUE:" + update.hallPlayer.get(choice).get(1) + "       " + (update.professors.get(choice).get(1) ? "YES" : "NO"));
        System.out.println("\nGREEN:" + update.hallPlayer.get(choice).get(2) + "      " + (update.professors.get(choice).get(2) ? "YES" : "NO"));
        System.out.println("\nYELLOW:" + update.hallPlayer.get(choice).get(3) + "     " + (update.professors.get(choice).get(3) ? "YES" : "NO"));
        System.out.println("\nPINK:" + update.hallPlayer.get(choice).get(4) + "       " + (update.professors.get(choice).get(4) ? "YES" : "NO"));
        System.out.println("\nTowers left:" + update.towersOnPlayer.get(choice) + ((update.game_Type == 1) ? "   Coins left:" + update.coinsOnPlayer.get(choice) : ""));
    }

    private void seeOtherBoards() {
        System.out.println("\nSure! Which board would you like to see? As always, digit the appropriate number:");
        for (String n : update.players) {
            if (!Objects.equals(n, nick)) {
                System.out.println(update.players.indexOf(n) + ":" + n);
            }
        }
        int choice = getSingleIntInput(update.nPlayers - 1);
        System.out.println("\nOK! Here's what " + update.players.get(choice) + " has:");
        seePlayerBoards(choice);
    }

    private void seeOwnBoard() {
        System.out.println("\nHere's what you have:");
        seePlayerBoards(update.players.indexOf(nick));
    }

    private void seeBoard() {
        System.out.println("\nSure! Here's what we're at:");
        System.out.println("\n                ISLANDS"+(update.game_Type==1 ? "(if you see a [X] it means there's a Prohibition counter there!)":""));
        for (int index : update.studentsOnIsland.keySet()) {
            System.out.println("\nIsland " + index + (update.numTDOnIsland.get(index) ? "[X]" : "") + ":" + update.studentsOnIsland.get(index) + "Towers:" + update.towersOnIsland.get(index - 1) + (update.whoOwnTowers.get(index - 1) != null ? (", owned by " + update.whoOwnTowers.get(index - 1)) : "") + (update.motherNatureOnIsland.get(index - 1) ? "  <----Mother Nature is here! Say hello!" : ""));
        }
        System.out.println("\n                CLOUDS           ");
        for (int index : update.studentsOnCloud.keySet()) {
            System.out.println("\nCloud " + index + ":" + update.studentsOnCloud.get(index));
        }
    }

    private void seeHand() {
        System.out.println("\nRight away! Here's your hand(Index:Movement,Value):");
        for (int i = 0; i < update.handPlayer.get(update.players.indexOf(nick)).size(); i=i+2) {
            System.out.println("\n"+update.handPlayer.get(update.players.indexOf(nick)).indexOf(update.handPlayer.get(update.players.indexOf(nick)).get(i))+":"+update.handPlayer.get(update.players.indexOf(nick)).get(i)+","+update.handPlayer.get(update.players.indexOf(nick)).get(i+1)+"  ");
        }
    }

    private void seeCharacters() {
        System.out.println("\nThese guys can give you the boost you need to win! Here's what we've got today:");
        System.out.println("\nIf the cost is between '**', it means that Character has already been used!");
        System.out.println("\nINDEX     COST        EFFECT");
        for (int i : update.idCharacter) {
            System.out.println("\n" + i + "     " + (update.activated.get(update.idCharacter.indexOf(i)) ? "*" : "") + characterCost(i) + (update.activated.get(update.idCharacter.indexOf(i)) ? "*" : "") + "        " + characterEffect(i));
            if (i == 0 || i == 6 || i == 10) {
                System.out.println("\nStudents currently on card:" + update.studentsOnCard.get(update.idCharacter.indexOf(i)));
            } else if (i == 4) {
                System.out.println("\nProhibition counters currently on this card:" + update.numTD);
            }
        }
    }

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

    private void checkIntInput(int a, int b, int input, String string) {
        while (input < a || input > b) {
            inputInt.remove(inputInt.size() - 1);
            System.out.println("The number entered is not allowed.\n" + string + "\n");
            input = getIntInput();
        }
    }

    private void checkStrInput(String s, String c) {
        while (!s.equals("RED") && !s.equals("BLUE") && !s.equals("GREEN") && !s.equals("YELLOW") && !s.equals("PINK")) {
            inputStr.remove(inputStr.size() - 1);
            System.out.println("The color entered is not allowed.\n" + c + "\n");
            s = getStrInput();
        }
    }

    private String getValidString(String request) {
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

    private int getValidInt(String request) {
        //This method gets a valid int while asking the "request"
        boolean inv = true; //Input Not Valid
        String input;
        int n = 0;
        Scanner s = new Scanner(System.in);
        while (inv) {
            System.out.println(request);
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