package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.BagEmptyException;
import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.SERVER.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 * Represents the Controller in the MVC architecture. It acts as a bridge between the model and the controller.
 * Here are all the methods the user, by the View, can invoke to perform actions and all the logic game's ones. It has information about the game status.
 * Thanks to this class, the ModelView is notified and ready to send update messages to the clients.
 * @author GC56
 */
public class Game extends Observable {
    private ArrayList<Integer> valueCardPlayed; //value of the cards played this round, in the same order as list players is.
    private final int playerCount; // number of players in the game.
    private final int gameType; //0: normal game, 1: expert game.
    private final ArrayList<Player> players; //array of all players.
    public ArrayList<Player> order; // says the order of each turn in which the players are going to play.
    private ArrayList<Card> cardsPlayed;  //Cards played in this round.
    private final Bag bag; // where all the students are at the beginning.
    private final Board board; // main board in which there are all the common things between players (as islands, clouds...)
    public final ColorTracker red, blue, green, yellow, pink; // professors.
    public final RoundMaster roundMaster; //rounds manager.
    public CharacterSelector characterSelector = null; // the object who choose the 3 characters in the game
    public final MotherNature motherNature; //main pawn of the game.
    private int MNbonus = 0; // additional movement to Mother Nature; is called by a Character.
    private int InfluenceBonus = 0; // bonus of influence given by some characters
    private Player PwBonus; // bonus given by some characters
    public boolean cloudEmptied=false;
    private final ModelView modelView; //notified by this.
    private boolean lastRound; // Says if we are in the last round.
    private boolean gameOver; // Says if the game is ended.

    private final ArrayList<String> update; //list of strings to send to the clients

    /**
     * Game constructor. It constructs every object of the game. It is called only when the correct number of players has joined the game.
     * requires (pcount==2 || pcount==3) && nick1!=null && nick2!=null && !nick1.equals(nick2) && !nick2.equals(nick3) && !nick1.equals(nick3) && gm!=null
     * @param pcount number of players playing.
     * @param gt type of the game (1: expert rules, 0: normal rules).
     * @param nick1 nickname of the first player logged.
     * @param nick2 nickname of the second player logged.
     * @param nick3 nickname of the third player logged.
     * @param gm the GameManager.
     */
    public Game(int pcount, int gt, String nick1, String nick2, String nick3, GameManager gm)  {
        this.playerCount = pcount;
        this.modelView = new ModelView(this, gm);
        this.addObserver(modelView);
        this.gameType = gt;
        this.players = new ArrayList<>();
        this.cardsPlayed = new ArrayList<>();
        //building professors
        this.red = new ColorTracker("RED");
        this.blue = new ColorTracker("BLUE");
        this.green = new ColorTracker("GREEN");
        this.yellow = new ColorTracker("YELLOW");
        this.pink = new ColorTracker("PINK");

        this.bag = new Bag();
        this.board = new Board(playerCount);
        this.order = new ArrayList<>();
        this.lastRound=false;
        this.gameOver=false;
        this.valueCardPlayed=new ArrayList<>();
        //default value for the list which contains the value of the last card played by every player
        for(int i=0; i<3; i++){
            valueCardPlayed.add(100);
        }
        //building players
        this.players.add(new Player(playerCount, nick1, this));
        this.players.add(new Player(playerCount, nick2, this));
        if (playerCount == 3) {
            this.players.add(new Player(playerCount, nick3, this));
        }

        // building gate for every player.
        for (Player p : players) {
            for (int i = 0; i < p.getGate().getMAX(); i++) {
                try {
                    p.getGate().addStudent(this.getBag().extractStudent().getColor());
                }catch (BagEmptyException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        this.motherNature = new MotherNature(board.islands.getIsland(1));
        roundMaster = new RoundMaster(players);

        order.addAll(players);

        if (gameType == 1)
            this.characterSelector = new CharacterSelector(this);
        this.update=new ArrayList<>();

        //notifying the game creation.
        update.add("Game created! LET'S GO!");
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * Returns true if and only if the game is over.
     * @return the boolean attribute gameOver.
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     * Returns the ModelView object that is notified by this.
     * @return the ModelView object that is notified by this.
     */
    public ModelView getModelView() {
        return modelView;
    }

    /**
     * This STATIC method generates a shuffled array of Students, equally distributed between colors.
     * @param numStud number of students to be generated.
     * @return a not sorted list of students.
     */
    public static ArrayList<Student> randomStudGenerator(int numStud) {
        int numColors = 5;
        ArrayList<Student> students = new ArrayList<>();
        //building (numStud/numColors) RED students. So on for every color.
        for (int i = 0; i < numStud / numColors; i++) {
            students.add(new Student("RED"));
        }
        for (int i = 0; i < numStud / numColors; i++) {
            students.add(new Student("BLUE"));
        }
        for (int i = 0; i < numStud / numColors; i++) {
            students.add(new Student("YELLOW"));
        }
        for (int i = 0; i < numStud / numColors; i++) {
            students.add(new Student("GREEN"));
        }
        for (int i = 0; i < numStud / numColors; i++) {
            students.add(new Student("PINK"));
        }
        //shuffles the list.
        Collections.shuffle(students);
        return students;
    }

    /**
     * It changes the phase of the current Round and fills all the clouds, if we are in Planning phase. Otherwise, it creates a new round, restore the characters effects and creates a new list of cards played by players.
     * In both cases sets the order in which the players are going to play in the new phase.
     * @throws BoundException when the bag is empty.
     */
    public void changePhase() throws BoundException {
        if(lastRound && roundMaster.round.getCurrentPhase().equals("Action")) {
            gameEnd();
        }
        else {
            // If the current phase is Planning, then the clouds need to be restored.
            if (roundMaster.round.getCurrentPhase().equals("Planning")) {
                for (int i = 0; i < playerCount + 1; i++) {
                    bagToCloud(0);
                    bagToCloud(1);
                    if (playerCount == 3) {
                        bagToCloud(2);
                    }
                }
            }

            if (roundMaster.round.getCurrentPhase().equals("Action")) {
                cardsPlayed = new ArrayList<>();
                this.valueCardPlayed=new ArrayList<>();
                for(int i=0; i<3; i++){
                    valueCardPlayed.add(100);
                }
            }
            // changes the phase in the model.
            this.order = roundMaster.changePhase();
            if (gameType == 1)
                //restore characters effects, since they stand for a turn.
                characterSelector.effects.restore();

            //reset the maxmoves of all players.
            for (Player p : players) {
                p.maxMoves = playerCount + 1;
            }
            // Notifies observers (ModelView)
            if(this.getBag().getStudentsSize()<=0){
                gameEnd();
            }
            update.add("\nNext phase!");
            setChanged();
            notifyObservers(update);
            update.clear();
        }
    }

    /**
     * It's called whenever in the game has passed 10 turns or a player has finished his tower.
     * It says who is the winner of the Game setting the attribute winner.
     */
    private void gameEnd() {
        // the winner of the game
        String winner = calculateWinner();
        update.add("GAME OVER! Winner: "+ winner);
        gameOver=true;
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * It's called once a game, when gameEnd is called. It calculates the winner of the game based on who has the minor number of towers.
     * @return the name of the player who won the Game. If there is not, it returns the String "It's a draw!".
     */
    private String calculateWinner(){
        int min=players.get(0).getTower_count();
        Player minPlayer=players.get(0);
        boolean first=true;
        boolean draw=false;
        for(Player p : players){
            if(!first){
                if(p.getTower_count()<min){
                    min=p.getTower_count();
                    minPlayer=p;
                    draw=false;
                }
                else if(p.getTower_count()==min){
                    draw=true;
                }
            }
            else{
                first=false;
            }
        }
        if(!draw) {
            return minPlayer.nickname;
        }
        else{
            return "It's a draw!";
        }
    }

    /**
     * Performs the action of moving a student from the Gate to the Player's Hall.
     * ensures (player1.getNickname().equals(name) && player1.hall.getColor(color)==\old(player1.hall.getColor(color))+1)
     * requires name!=null && color!=null
     * @param name the nickname of the user who wants to move a students to the Hall.
     * @param color the color of the students the user wants to move.
     * @throws ImpossibleActionException if the phase is not Action, or if the Player has no colors as specified in the parameters in his Gate. It can be called even if the player has finished his moves or if is not the turn of the player who called the method.
     */
    public void gateToHall(String name, String color) throws ImpossibleActionException {
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1 = playerTranslator(name);
            if (order.get(0).equals(player1)) {
                if (player1.getGate().getColorsInGate().contains(color)) {
                    if(player1.maxMoves>0) {
                        //adds a student to the Hall and removes the same player from the Gate.
                        addStudentToHall(color, player1);
                        int i = 0;
                        while (!(player1.getGate().students.get(i).getColor().equals(color))) {
                            i++;
                        }
                        removeFromGate(player1, i);
                        //reduces the number of moves remained to the player who moved the student.
                        player1.maxMoves--;
                        update.add("\nHeads up! " + player1.nickname + " moved a " + color + " student to their hall!");
                    }else throw new ImpossibleActionException("You can't move any more students! Get your new bad boys from the cloud");
                } else throw new ImpossibleActionException("No such color in " + player1.nickname + "'s gate.");
            } else throw new ImpossibleActionException("Is not your turn.");
        } else throw new ImpossibleActionException("\nNot the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * Performs the action of moving a student from the Bag to the specified Cloud.
     * ensures (!bag.isEmpty())
     * @param index the index of the cloud in which is needed to place students.
     * @throws BoundException when the bag is empty and is impossible to take students from it or when the index specified is not allowed.
     */
    public void bagToCloud(int index) throws BoundException {
        if (index >= 0 && index <= 3 && board.clouds.get(index).students.size() < playerCount + 1) {
            try {
                board.clouds.get(index).addStudent(bag.extractStudent().getColor());
            }catch (BagEmptyException e){
                System.out.println(e.getMessage());
            }
        } else throw new BoundException("\nINDEX OUT OF BOUND!\n");
    }

    /**
     * Performs the action of moving a student from the Gate to a specified Island.
     * requires name!=null
     * ensures (player.getNickname().equals(name) && !\old(player.getGate().getStudents.get(index)).equals(player.getGate().getStudents.get(index)) && player.getHall.getColor(\old(player.getGate().getStudents.get(index)).getColor)==player.getHall.getColor(\old(player.getGate().getStudents.get(index)).getColor)+1)
     * @param name the nickname of the user who wants to move a students to an Island.
     * @param indexIsland the index of the island the user wants to move students to.
     * @throws ImpossibleActionException if the phase is not Action, or if the Player has no colors as specified in the parameters in his Gate. It can be called even if the player has finished his moves or if is not the turn of the player who called the method.
     */
    public void gateToIsland(String name, int index, int indexIsland) throws BoundException, ImpossibleActionException {
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1 = playerTranslator(name);
            if (order.get(0).equals(player1)) {
                if (player1.getGate().getColorsInGate().contains(player1.getGate().getStudents().get(index).getColor())) {
                    if (player1.getGate().students.size() >= player1.getGate().MAX - playerCount ) {
                        if(player1.maxMoves>0) {
                            // adds the specified student to the specified island and removes it from the Player's Gate.
                            addStudentToIsland(player1.getGate().getStudents().get(index).getColor(), indexIsland);
                            String color=player1.getGate().getStudents().get(index).getColor();
                            removeFromGate(player1, index);
                            // Reduces the number of moves the player can do in this turn.
                            player1.maxMoves--;
                            update.add("\nSomething's happened!\n" + player1.nickname + " moved a " + color + " student to Island " + indexIsland + "!");
                        }else throw new ImpossibleActionException("You can't move any more students! Get your new bad boys from the cloud");
                    } else throw new BoundException("\n" + player1.nickname + " can't place anymore students.\n");
                } else throw new ImpossibleActionException("\nNot such color in " + player1.nickname + "'s gate");
            } else throw new ImpossibleActionException("\nIs not your turn.");
        } else throw new ImpossibleActionException("\nNot the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * Performs the action of moving a student from a specified Cloud to the Player's Gate.
     * requires player!=null
     * ensures (player.getNickname().equals(player) && (\forAll int i=0; i<player.getGate.getStudents.contains(\old(this.getBoard().getClouds.get(cIndex).students.get(i))))
     * @param player the nickname of the user who wants to move students to the Gate.
     * @param cIndex the index of the Cloud the user wants to take students from.
     * @throws ImpossibleActionException if the phase is not Action, or if the Player has not enough space in his Gate. It can be called even if is not the turn of the player who called the Action.
     */
    public void CloudToGate(String player, int cIndex) throws BoundException, ImpossibleActionException {
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player p = playerTranslator(player);
            if (order.get(0).equals(p)) {
                    if (!board.clouds.get(cIndex).students.isEmpty() && p.getGate().students.size() < p.getGate().MAX-2) {
                        // adds every student from the specified cloud to the player's gate.
                        for(Student s:board.clouds.get(cIndex).students){
                            addToGate(p, s.getColor());
                        }
                        //removes every student from the specified cloud.
                        while(!getBoard().clouds.get(cIndex).emptyCloud()){
                            removeFromCloud(cIndex, 0);
                        }
                        update.add("\nStay sharp! " + p.nickname + " just snatched the students from Cloud number " + cIndex + "!");
                        cloudEmptied=true;
                    } else
                        throw new BoundException("\nNot enough space in " + p.nickname + "'s gate, or the cloud is empty.\n");
            } else throw new ImpossibleActionException("\nIt's not your turn.");
        } else throw new ImpossibleActionException("\nNot the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * Performs the action of moving MotherNature into an island of player choice.
     * @param name the nickname of the player who wants to move MotherNature.
     * @param movement the distance between the thi island in which MotherNature is at the moment and the one in which the user wants to put MotherNature.
     * @throws ImpossibleActionException if the user tries to move MotherNature in a place not allowed by his last card played or if is not his turn. Even if is not the correct phase of the game in which the user can move MotherNature.
     * @throws ConsecutiveIslandException if the two island the game's logic is trying to merge are not consecutive.
     * @throws BoundException if, when is needed to refill clouds, the bag is empty.
     */
    public void moveMotherNature(String name, int movement) throws ImpossibleActionException, ConsecutiveIslandException, BoundException {
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1 = playerTranslator(name);
            if(cloudEmptied) {
                if (player1.maxMoves == 0 && order.get(0).equals(player1)) {
                    if ((movement <= order.get(0).getLastCardPlayed().getMovement() + MNbonus) && (movement>0)) {
                        motherNature.getIsland().setMotherNature(false);
                        Island tmp = motherNature.getIsland();
                        for (int i = 0; i < movement; i++) {
                            tmp = tmp.next;
                        }
                        tmp.setMotherNature(true);
                        motherNature.setIsland(tmp);
                        cloudEmptied = false;

                        //At the end of Mother Nature's movement, it's time to calculate influence on the island in which She stopped.
                        determineInfluence(tmp.getId());

                        //Islands need to be merged if and only if, once the Influence is determined,
                        // the next or the previous island is controlled by the same player.
                        Island r = tmp;
                        while (!r.next.equals(tmp))
                            r = r.next;
                        if (!r.towers.isEmpty() && !tmp.towers.isEmpty()) {
                            if (r.getPlayer().equals(tmp.getPlayer())) {
                                if (board.islands.getIsland(tmp.getId()).next.equals(board.islands.getIsland(r.getId())) || board.islands.getIsland(r.getId()).next.equals(board.islands.getIsland(tmp.getId()))) {
                                    //time to merge the island in which MotherNature stopped and its next.
                                    int min = Math.min(tmp.getId(), r.getId());
                                    mergeIslands(tmp.getId(), r.getId());
                                    tmp = getBoard().islands.getIsland(min);
                                    motherNature.setIsland(tmp);
                                } else
                                    throw new ConsecutiveIslandException("\nThe islands are not consecutive, impossible to merge!");
                            }
                        }
                        if (!tmp.towers.isEmpty() && !tmp.next.towers.isEmpty()) {
                            if (tmp.getPlayer().equals(tmp.next.getPlayer())) {
                                if (board.islands.getIsland(tmp.getId()).next.equals(board.islands.getIsland(tmp.next.getId())) || board.islands.getIsland(tmp.next.getId()).next.equals(board.islands.getIsland(tmp.getId()))) {
                                    //time to merge the island in which MotherNature stopped the one which is before it.
                                    int min=tmp.getId();
                                    mergeIslands(tmp.getId(), tmp.next.getId());
                                    tmp = getBoard().islands.getIsland(min);
                                    motherNature.setIsland(tmp);
                                } else
                                    throw new ConsecutiveIslandException("\nThe islands are not consecutive, impossible to merge!");
                            }
                        }
                        //the player who called the method has finished his things to do. So he is removed from Order list.
                        order.remove(0);
                    } else throw new ImpossibleActionException("\nThe card you played doesn't allow you to move Mother Nature there!");

                } else throw new ImpossibleActionException("\nIs not your turn or you still have to place students.");
            }else throw new ImpossibleActionException("\nPick your cloud before moving mother nature!");
        } else throw new ImpossibleActionException("\nNot the correct phase in which you can move Mother nature! \n");

        if (order.isEmpty())
            //if every player has moved MotherNature than is time to change phase.
            changePhase();
        else {
            update.add("\nNow is "+order.get(0).nickname+"'s turn to place students!");
            setChanged();
            notifyObservers(update);
            update.clear();
        }
    }

    /**
     * It's called everytime MotherNature lands on an island. it determines the influence of every player on the specified island. If is there a player who dominates all the others, then a tower of that Player need to be placed.
     * requires index>=1 && index<=12
     * @param index the index of the island on which is needed to calculate the influence.
     * @throws ImpossibleActionException if there are no towers on the island in which the game's logic is trying to swap the towers between two players.
     */
    public void determineInfluence(int index) throws ImpossibleActionException {
        if (this.board.islands.getIsland(index).TD) {
            // if the is a prohibition card, nothing has to be done.
            this.board.islands.getIsland(index).removeTD();
            characterSelector.restoreTD();
            update.add("\nMother nature stopped on Island " + index + ", but a Prohibition Token denied her right to place a Tower! Too bad.");
        } else {
            ArrayList<Integer> p = new ArrayList<>();
            int x = 0;
            for (int i = 0; i < playerCount; i++) {
                p.add(x);
            }

            // counting the number of tower on the specified island
            for (Tower t : this.board.islands.getIsland(index).towers) {
                for (int z = 0; z < playerCount; z++) {
                    if (t.getPlayer().equals(this.players.get(z)))
                        p.set(z, p.get(z) + Tower.getInfluence());
                }
            }

            // counting the number of students for each player on the specified island
            for (Student s : this.board.islands.getIsland(index).getStudents()) {
                if (colorTranslator(s.getColor()).getPlayer() != null)
                    p.set(players.indexOf(colorTranslator(s.getColor()).getPlayer()), p.get(players.indexOf(colorTranslator(s.getColor()).getPlayer())) + colorTranslator(s.getColor()).getInfluence());
            }
            // if needed, adds the bonus on influence given by a character.
            for (int z = 0; z < playerCount; z++) {
                if (this.players.get(z).equals(this.PwBonus))
                    p.set(z, p.get(z) + InfluenceBonus);
            }
            ArrayList<Integer> q = new ArrayList<>(p);

            // ordering the list by influence.
            Collections.sort(p);

            if (!p.get(p.size() - 1).equals(p.get(p.size() - 2))) {
                if (this.board.islands.getIsland(index).towers.isEmpty()) {
                    //I'm sure it's not a superIsland, so I add a tower.
                    this.board.islands.getIsland(index).addTower(players.get(q.indexOf(p.get(p.size() - 1))));
                    players.get(q.indexOf(p.get(p.size() - 1))).removeTower();
                    update.add("\nYou better start worrying because " + players.get(q.indexOf(p.get(p.size() - 1))).nickname + " just placed a Tower on Island " + index + "!");
                    if(players.get(q.indexOf(p.get(p.size() - 1))).getTower_count()==0){
                        gameEnd();
                    }
                } else if (!players.get(q.indexOf(p.get(p.size() - 1))).equals(this.board.islands.getIsland(index).getPlayer())) {
                    //Could be a superIsland, so it could be needed to swap towers between players.
                    String oldOwner = this.board.islands.getIsland(index).getPlayer().nickname;
                    swapTowers(index, players.get(q.indexOf(p.get(p.size() - 1))));
                    for(int i=0; i<board.islands.getIsland(index).getTowers().size(); i++) {
                        //I remove a tower for any tower placed
                        if(!(players.get(q.indexOf(p.get(p.size() - 1))).getTower_count()==0)) {
                            players.get(q.indexOf(p.get(p.size() - 1))).removeTower();
                        }
                        else{
                            gameEnd();
                        }
                    }
                    update.add("\nPOWER PLAY! " + players.get(q.indexOf(p.get(p.size() - 1))).nickname + " just took all of " + oldOwner + "'s Towers on Island " + index + "!");
                    if(players.get(q.indexOf(p.get(p.size() - 1))).getTower_count()==0){
                        gameEnd();
                    }
                }
            } else
                update.add("\nMother Nature stopped on Island " + index + ", and nothing happened. She's disappointed.");
        }
    }

    /**
     * It's called if, on the specified SuperIsland, the influence of a player is higher than the influence of the owner of the tower one. So it swaps the towers from the player who has used to own the towers to the one who has higher influence on the island.
     * requires index>=0 && index<=12 && player1!=null.
     * @param index the index of the SuperIsland in which is needed to swap towers.
     * @param player1 the player who has higher influence on the island.
     * @throws ImpossibleActionException if the island has no towers.
     */
    public void swapTowers(int index, Player player1) throws ImpossibleActionException {
        if (board.islands.getIsland(index).towers != null) {
            Player playerLosingTowers=board.islands.getIsland(index).getPlayer();
            for (Tower t : board.islands.getIsland(index).towers) {
                // changing the tower's owner and adding again the remove tower to the player who lost it.
                t.setPlayer(player1);
                playerLosingTowers.addTower();
            }
        } else throw new ImpossibleActionException("\nNo towers in this island.\n");
    }

    /**
     * Performs the action called by game's logic to merge two islands. Two island will no more be part of CircularList, but a new SuperIsland is created and added to the CircularList.
     * requires (index1>=1 && index1<=12 && index2>=1 && index2<=12)
     * @param index1 the index of an island to be merged.
     * @param index2 the index of the other island to be merged with the one with index equals to index1
     */
    public void mergeIslands(int index1, int index2){
        Island i1, i2;
        i1 = board.islands.getIsland(index1);
        i2 = board.islands.getIsland(index2);
        if (!i1.towers.isEmpty() && !i2.towers.isEmpty() && i1.towers.get(0).getPlayer().equals(i2.towers.get(0).getPlayer())) {
            // merging islands
            board.islands.mergeIslands(i1, i2);
            update.add("\nIt's happening everybody!\n" + "Island " + index1 + " and Island " + index2 + " just merged into one! " +
                    "We have a new Island " + Math.min(index1, index2) + "!" +
                    "\nThere are only " + board.islands.size() + " left!");
        }
        else System.out.println("Errore nel merge");
        if(board.islands.size()<=3){
            gameEnd();
        }
    }

    /**
     * Method called when a player wants to play an assistant card.
     * @param player The player who plays the card
     * @param index The position of the Card in the list of cards in Hand; it does not always correspond to the attribute Value of a Card.
     * @throws ImpossibleActionException if the card can't be played
     * @throws BoundException from ChangePhase();
     */
    public void playCard(String player, int index) throws ImpossibleActionException, BoundException {
        if (roundMaster.round.getCurrentPhase().equals("Planning")) {
            if (index >= 0 && index < 10) {
                Player player1 = playerTranslator(player);
                int i = 0;
                while (!players.get(i).equals(player1)) {
                    i++;
                }
                if (order.get(0).equals(this.players.get(i))) {
                    int card1=100, card2=100;
                    if(!cardsPlayed.isEmpty()) {
                        card1 = cardsPlayed.get(0).getValue();
                        if(cardsPlayed.size()==2)
                            card2=cardsPlayed.get(1).getValue();
                    }
                    //time to play the card
                    int value=player1.getHand().getCards().get(index).getValue();
                    if(value!=card1 && value!=card2) {
                        actuallyPlayCard(i, index);
                    } else {
                        //The card has already been played
                        boolean onlyUsedCards=true;
                        for (Card handCard : players.get(i).getHand().getCards())
                        {
                            boolean found=false;
                            for(Card playedCard : cardsPlayed){
                                if (handCard.getValue() == playedCard.getValue()) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                onlyUsedCards = false;
                                break;
                            }
                        }
                        if(onlyUsedCards){
                            // the player has to play the card and be putted in order after the other one.
                            actuallyPlayCard(i, index);
                        }
                        else throw new ImpossibleActionException("\nYou can't play a card which has already been played by someone else!\n");
                    }
                } else throw new ImpossibleActionException("\nNot " + players.get(i).nickname + "'s turn!\n");
            } else throw new ImpossibleActionException("\nNo card with " + index + " as index\n");
        } else throw new ImpossibleActionException("\nIt's not planning phase!\n");
    }

    /**
     * Method called by playCard() after checking that the move is "legal".
     * @param playerIndex Index of the player in the ArrayList players
     * @param cardIndex The position of the Card in the list of cards in Hand; it does not always correspond to the attribute Value of a Card.
     * @throws BoundException from changePhase().
     */
    private void actuallyPlayCard(int playerIndex, int cardIndex) throws BoundException{
        cardsPlayed.add(this.players.get(playerIndex).playCard(cardIndex)); //Removes and returns the card
        valueCardPlayed.set(playerIndex, cardsPlayed.get(cardsPlayed.size()-1).getValue());
        order.remove(0);
        update.add("\n" + this.players.get(playerIndex).nickname + " played a card with value: "+cardsPlayed.get(cardsPlayed.size() - 1).getValue()+" and movement: "+cardsPlayed.get(cardsPlayed.size() - 1).getMovement());
        if(players.get(playerIndex).getHand().getCards().isEmpty()){
            lastRound=true;
        }
        //When order is empty, it means that every player has played. So it's time to change phase into "Action";
        if (order.isEmpty()) {
            changePhase();
        }
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     *  returns the list of players in this game.
     * @return the list of players in this game.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     *  returns the board of this game.
     * @return the board of this game.
     */
    public Board getBoard() {
        return board;
    }

    /**
     *  returns the bag of this game.
     * @return the bag of this game.
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * returns the game type
     * @return gameType.
     */
    public int getGameType() {
        return gameType;
    }

    /**
     * adds a student of the specified color to the Hall of the specified Player
     * requires color!=null && player!=null
     * @param color the color of the student to be added
     * @param player the player who recives the student
     */
    public void addStudentToHall(String color, Player player) {
        player.getHall().setColor(color);
        checkColorChanges(player.getHall().getCardState());
        if (player.getHall().getColor(color) % 3 == 0 && gameType == 1) {
            player.addCoin();
        }
    }

    /**
     * returns the number of students of a specified color contained in the Hall of a specified player.
     * @param player the player from who is needed to get the nu,ber of students of a certain color.
     * @param color the color of the students from which is needed to know the number of from the Hall.
     * @return the number of students of a specified color contained in the Hall of a specified player.
     */
    public int getColor(Player player, String color) {
        return player.getHall().getColor(color);
    }

    /**
     * Adds a student of the specified color to the specified players gate.
     * requires p1!=null && color!=null
     * @param p1 the player to whom the students will be added.
     * @param color the color of the student to be added.
     */
    public void addToGate(Player p1, String color) {
        p1.getGate().addStudent(color);
    }

    /**
     * adds a student of the specified color to the specified island.
     * requires index>=1 && index<=12 && color!=null
     * @param color the color of the student to be added
     * @param index the index of the island to which the student will be added.
     */
    public void addStudentToIsland(String color, int index) {
        board.islands.getIsland(index).addStudent(color);
    }
    ///

    /**
     * Removes a specified student from a specified island. It's mainly used in test.
     * requires index>=1 && index<=12 && sIndex>=0
     * @param sIndex the index of the student to be removed.
     * @param index the index of the island from which is needed to remove students.
     */
    public void removeFromIsland(int sIndex, int index) {
        board.islands.getIsland(index).removeStudent(sIndex);
    }
    /**
     * Removes a specified student from a Gate. It's mainly used by Characters.
     * requires index>=0 && p1!=null
     * @param p1 the player from whom a student will be removed from their gate.
     * @param index the index of the student to be removed.
     */
    public void removeFromGate(Player p1, int index) {
        p1.getGate().removeStudent(index);
    }

    /**
     * Removes a specified student from a Gate. It's mainly used by Characters.
     * requires indexCloud>=0 && indexCloud<=3 && indexStudent>=0 && indexStudent<=3
     * @param indexCloud the Cloud from which a student will be removed from.
     * @param indexStudent the index of the student to be removed.
     */
    public void removeFromCloud(int indexCloud, int indexStudent) {
        board.clouds.get(indexCloud).removeStudent(indexStudent);
    }

    /**
     * Mainly used in tests, removes a student from hall.
     * requires p!=null && color!=null
     * @param p the Player from whom is needed to remove a student.
     * @param color the color of the student to be removed.
     */
    public void removeFromHall(Player p, String color) { //Probabilmente con le modifiche a Student questo metodo diventa inutile
        p.getHall().desetColor(color);
    }
    ///

    /**
     * Method called by the Controller class when an effect activation request is received. Checks if the player has enough coins, then relays the
     * request to the characterSelector. When the effect is applied, stores the String received to be added to the updateMessage.
     * @param player The player who activated the effect.
     * @param id The id of the activated card.
     * @param intpar The first ArrayList of integer parameters needed for the effects.
     * @param strpar The Arraylist of String parameters needed for the effects.
     * @param intpar2 The second Arraylist of integer parameters, needed for some effects.
     * @throws ImpossibleActionException Thrown when the player who activated the card doesn't have enough coins to actually activate it.
     */
    public void activateCharacter(String player, int id, ArrayList<Integer> intpar, ArrayList<String> strpar, ArrayList<Integer> intpar2) throws ImpossibleActionException {
        //Keep the unused parameters null, and always use the first parameter in numeric order by type.
        Player p = playerTranslator(player);
        if (p.getCoins() >= characterSelector.getCost(id)) {
            // removes the coins from the player and activate the effect of the called character.
            p.removeCoin(characterSelector.getCost(id));
            update.add("\n Heads up! " + player + " just activated the Character card " + id + "!" + "\n" +
                    characterSelector.applyEffect(id, p, intpar, strpar, intpar2));
        } else throw new ImpossibleActionException("\nNot enough coins!\n");
        setChanged();
        notifyObservers(update);
        update.clear();
    }

    /**
     * This method is called every time the number of students in any hall is changed.
     * Checks the current ownership of each Professor following a particular rule depending on
     * if a specific Character Effect is activated or not.
     * @param rule Boolean indicating if the Character effect that lets a Professor change ownership even
     *             if the number of students of its color is the same in both hall is activated or not.
     */
    public void checkColorChanges(boolean rule) {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("RED");
        colors.add("BLUE");
        colors.add("YELLOW");
        colors.add("GREEN");
        colors.add("PINK");
        for (String ct : colors) {
            Player max = colorTranslator(ct).getPlayer();
            if (rule) {
                for (Player pl : players) {
                    if ((max == null && pl.getHall().getColor(ct) > 0) || ((max != null) && pl.getHall().getColor(ct) >= max.getHall().getColor(ct) && !pl.equals(max)))
                        max = pl;
                }
            } else {
                for (Player pl : players) {
                    if ((max == null && pl.getHall().getColor(ct) > 0) || ((max != null) && pl.getHall().getColor(ct) > max.getHall().getColor(ct) && !pl.equals(max)))
                        max = pl;
                }
            }
            colorTranslator(ct).setPlayer(max);
        }
    }

    /**
     * Returns an object Player from the specified name. The Player returned is the one with nickname equals to the specified one.
     * It is used to translate the specified nickname to the corresponding Player.
     * requires name!=null
     * @param name the nickname of a Player.
     * @return the Player whom nickname is name.
     * @throws IllegalArgumentException if no Player has 'name' as nickname
     */
    public Player playerTranslator(String name) throws IllegalArgumentException {
        if (name.equals(players.get(0).nickname) || name.equals(players.get(1).nickname) || name.equals(players.get(2).nickname)) {
            // one and only one Player has 'name' as nickname.
            if (players.get(0).nickname.equals(name))
                return players.get(0);
            else if (players.get(1).nickname.equals(name))
                return players.get(1);
            else
                return players.get(2);
        } else throw new IllegalArgumentException("\n" + name + " does not exists as a nickname.\n");
    }

    /**
     * Returns a ColorTracker from the specified color. The ColorTracker returned is the one with the same color as the specified one.
     * It is used to translate the specified color to the corresponding ColorTracker. Mainly used to calculate influence on an island.
     * requires color!=null
     * @param color the color to be "translated" into a ColorTracker
     * @return the corresponding ColorTracker.
     * @throws IllegalArgumentException if the specified color does not exist as a color in this game.
     */
    public ColorTracker colorTranslator(String color) throws IllegalArgumentException {
        ColorTracker color1;
        if (color.equals("RED") || color.equals("BLUE") || color.equals("YELLOW") || color.equals("GREEN") || color.equals("PINK")) {
            // one and only one ColorTracker has 'color' as its color.
            switch (color) {
                case "RED":
                    color1 = red;
                    break;
                case "BLUE":
                    color1 = blue;
                    break;
                case "GREEN":
                    color1 = green;
                    break;
                case "YELLOW":
                    color1 = yellow;
                    break;
                default:
                    color1 = pink;
                    break;
            }
            return color1;
        } else throw new IllegalArgumentException("\n" + color + " does not exist as a color in this game.\n");
    }

    /**
     * It sets the MotherNature movement bonus to 2, which is the only one value possible, since it can be set by only one character.
     * ensures this.MNbonus==2;
     */
    public void setMNbonus() {
        this.MNbonus = 2;
    }

    /**
     * It sets the MotherNature movement bonus to 0, which is the default value. It is called at the end of every turn in which a character as been activated.
     * ensures this.MNbonus==0;
     */
    public void disableMNbonus() {
        this.MNbonus = 0;
    }

    /**
     * It sets the bonus influence to a specified Player. The bonus influence is always equals to 2 and can be set by only one character.
     * ensures this.PwBonus.equals(p) && this.InfluenceBonus==2
     * @param p the Player who is going to receive the bonus on influence.
     */
    public void enableInfluenceBonus(Player p) {
        this.PwBonus = p;
        this.InfluenceBonus = 2;
    }

    /**
     * Returns the list of last card played by each player. The list's order follows the order in which the players have played their card.
     * @return the list of last card played by each player.
     */
    public ArrayList<Card> getCardsPlayed() {
        return cardsPlayed;
    }

    /**
     * It disables the bonus on influence, setting it to 0.
     * ensures this.InfluenceBonus == 0
     */
    public void disableInfluenceBonus() {
        this.InfluenceBonus = 0;
    }

    /**
     * Returns the number of players in this game.
     * @return the number of players in this game.
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Returns the list of values of the last card played by each player. The list's order follows the players list's order. So the order of the values is always the same: in the first position there will always be the value of the card played by the player who is in first position of the list players, and so on.
     * @return the list of values of the last card played by each player.
     */
    public ArrayList<Integer> getValueCardPlayed(){
        return valueCardPlayed;
    }

}
