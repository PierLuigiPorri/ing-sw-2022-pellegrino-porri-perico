package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.SERVER.Controller;
import it.polimi.ingsw.SERVER.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Game extends Observable {
    private final int playerCount;
    private final int gameType; //0: regole semplificate, 1: regole esperto.
    private final ArrayList<Player> players; //array of all players.
    public ArrayList<Player> order; // says the order of each turn in which the players are going to play.
    //private Controller controller; //
    // private ArrayList<VirtualView> messageHandlers; //Potranno essere usati per notificare le view remote delle modifiche
    private ArrayList<Card> cardsPlayed;  //Cards played in this round
    private final Bag bag;
    private final Board board;
    public final ColorTracker red, blue, green, yellow, pink; // professors.
    public RoundMaster roundMaster; //rounds manager.
    private Player winner;
    public CharacterSelector characterSelector= null;
    public final MotherNature motherNature;
    private int MNbonus=0; // additional movement to Mother Nature; is called by a Character.
    private int InfluenceBonus=0;
    private Player PwBonus;
    private final ModelView modelView;

    public Game(int pcount, int gt, String nick1, String nick2, String nick3) throws ImpossibleActionException {
        //Parameters: num of players, gametype, nickname and MsgHandler for every player
        this.playerCount=pcount;
        this.modelView=new ModelView(this);
        this.addObserver(modelView);
        this.gameType=gt;
        this.players=new ArrayList<>();
        this.cardsPlayed=new ArrayList<>();
        this.red=new ColorTracker("RED");
        this.blue=new ColorTracker("BLUE");
        this.green=new ColorTracker("GREEN");
        this.yellow=new ColorTracker("YELLOW");
        this.pink=new ColorTracker("PINK");
        this.bag=new Bag();
        this.board=new Board(playerCount);
        this.order = new ArrayList<>();

        this.players.add(new Player(playerCount, nick1, this));
        this.players.add(new Player(playerCount, nick2, this));
        if(playerCount==3){
            this.players.add(new Player(playerCount, nick3, this));
        }

        for (Player p: players) {
            for(int i=0; i< p.getGate().getMAX(); i++){
                if(!this.getBg().getStudents().isEmpty()) {
                    p.getGate().addInitialStud(this.getBg().extractStudent());
                }else throw new ImpossibleActionException("The bag is empty!");
            }
        }
        this.motherNature=new MotherNature(board.islands.getIsland(1));
        if(this.gameType==1){
            for(Player p:this.players){
                p.addCoin();
            }
        }
        roundMaster = new RoundMaster(players);

        /*mh1.setPlayerName(nick1);
        mh2.setPlayerName(nick2);
        if(playerCount==3){
            mh3.setPlayerName(nick3);
        }
        messageHandlers=new ArrayList<>();
        messageHandlers.add(mh1);
        messageHandlers.add(mh2);
        if(playerCount==3){
            messageHandlers.add(mh3);
        }
        controller=new Controller(this, mh1, mh2, mh3);
        for (VirtualView mh :
                messageHandlers) {
            mh.setController(controller);
            mh.setGameCreated();
        }*/
        order.addAll(players);

        if(gameType==1)
            this.characterSelector=new CharacterSelector(this);
        setChanged();
        notifyObservers();
    }

    public ModelView getModelView() {
        return modelView;
    }

    public static ArrayList<Student> randomStudGenerator(int numStud){
        //This STATIC method generates a shuffled array of Students, equally distributed between colors
        int numColors=5;
        ArrayList<Student> students=new ArrayList<>();
        for (int i = 0; i < numStud/numColors; i++) {
            students.add(new Student("RED"));
        }
        for (int i = 0; i < numStud/numColors; i++) {
            students.add(new Student("BLUE"));
        }
        for (int i = 0; i < numStud/numColors; i++) {
            students.add(new Student("YELLOW"));
        }
        for (int i = 0; i < numStud/numColors; i++) {
            students.add(new Student("GREEN"));
        }
        for (int i = 0; i < numStud/numColors; i++) {
            students.add(new Student("PINK"));
        }
        Collections.shuffle(students);
        return students;
    }

    public void changePhase() throws BoundException, ImpossibleActionException {
        // If the current phase is Planning, then the clouds need to be restored.
        if (roundMaster.round.getCurrentPhase().equals("Planning")) {
            for (int i = 0; i < playerCount + 1; i++) {
                if (bag.getSize() == 0)
                    throw new BoundException("The bag is empty!\n");
                else {
                    bagToCloud(0);
                    bagToCloud(1);
                    if (playerCount == 3) {
                        bagToCloud(2);
                    }
                }
            }
        }
        int[] tmp = new int[3];
        for (int i = 0; i < cardsPlayed.size(); i++) {
            tmp[i] = cardsPlayed.get(i).getValue();
        }
        if(roundMaster.round.getCurrentPhase().equals("Action"))
            cardsPlayed=new ArrayList<>();

        this.order = roundMaster.changePhase(tmp);


//reset the maxmoves of all players.
        for (Player p : players) {
            p.maxMoves = playerCount + 1;
        }
        setChanged();
        notifyObservers();
    }

    public Player gameEnd(){
        int[] x;
        x=new int[3];
        int min;
        x[0]=this.players.get(0).getTower_count();
        x[1]=this.players.get(1).getTower_count();
        x[2]=this.players.get(2).getTower_count();
        min=Math.min(Math.min(x[0], x[1]),x[2]);
        if(min==x[0])
            return this.players.get(0);
        else if(min==x[1])
            return this.players.get(1);
        else
            return this.players.get(2);
    }

    public void gateToHall(String name, String color) throws ImpossibleActionException {
        if(roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1 = playerTranslator(name);
            if(order.get(0).equals(player1)) {
                if (player1.getGate().getColorsInGate().contains(color)) {
                    addStudentToHall(color, player1);
                    int i = 0;
                    while (!(player1.getGate().students.get(i).getColor().equals(color))) {
                        i++;
                    }
                    removeFromGate(player1, i);
                    player1.maxMoves--;
                } else throw new ImpossibleActionException("Not such color in " + player1.nickname + "'s gate.");
            }else throw new ImpossibleActionException("Is not your turn.");
        }else throw new ImpossibleActionException("Not the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers();
    }

    public void bagToCloud(int index) throws BoundException, ImpossibleActionException {
        if (index >= 0 && index <= 3 && board.clouds.get(index).students.size() < playerCount + 1) {
            if (!this.getBg().getStudents().isEmpty()) {
                board.clouds.get(index).addStudent(bag.extractStudent().getColor());
            } else throw new ImpossibleActionException("The bag is empty!");

        } else throw new BoundException("INDEX OUT OF BOUND!\n");
        setChanged();
        notifyObservers();
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) throws BoundException, ImpossibleActionException {
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1 = playerTranslator(name);
            if (order.get(0).equals(player1)) {
                if (player1.getGate().getColorsInGate().contains(color)) {
                    if (player1.getGate().students.size() >= player1.getGate().MAX - 2) {
                        addStudentToIsland(color, indexIsland);
                        removeFromGate(player1, index);
                        player1.maxMoves--;
                    } else throw new BoundException(player1.nickname + " can't place anymore students.\n");
                } else throw new ImpossibleActionException("Not such color in " + player1.nickname + "'s gate");
            } else throw new ImpossibleActionException("Is not your turn.");
        } else throw new ImpossibleActionException("Not the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers();
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) throws BoundException, ImpossibleActionException { //TODO
        if (roundMaster.round.getCurrentPhase().equals("Action")) {
            Player p = playerTranslator(player);
            if (order.get(0).equals(p)) {
                if (board.clouds.get(cIndex).getColorsInCloud().contains(color)) {
                    if (!board.clouds.get(cIndex).students.isEmpty() && p.getGate().students.size() < p.getGate().MAX) {
                        addToGate(p, color);
                        removeFromCloud(cIndex, sIndex);
                    } else
                        throw new BoundException("Not enough space in " + p.nickname + "'s gate, or the cloud is empty.\n");
                } else throw new ImpossibleActionException("Not such color in this cloud.");
            } else throw new ImpossibleActionException("Is not your turn.");
        } else throw new ImpossibleActionException("Not the correct phase in which you can move Students! \n");
        setChanged();
        notifyObservers();
    }



    public void moveMotherNature(String name, int movement) throws ImpossibleActionException, ConsecutiveIslandException, BoundException {
        //TODO:determinare chi chiede al player quanto deve muovere, perché MNbonus (anche se 0)dovrà essere aggiunto al massimo movimento
        if(roundMaster.round.getCurrentPhase().equals("Action")) {
            Player player1=playerTranslator(name);
            if(player1.maxMoves==0 && order.get(0).equals(player1)) {
                if (movement < 7 + MNbonus) {
                    motherNature.getIsola().setMotherNature(false);
                    Island tmp = motherNature.getIsola();
                    for (int i = 0; i < movement; i++) {
                        tmp = tmp.next;
                    }
                    tmp.setMotherNature(true);
                    motherNature.setIsland(tmp);

//At the end of Mother Nature's movement, it's time to calculate influence on the island in which She stopped.
                    determineInfluence(tmp.getId());

//Islands need to be merged if and only if, once the Influence is determined,
//the next or the previous island is controlled by the same player.
                    Island r = tmp;
                    while (!r.next.equals(tmp))
                        r = r.next;
                    if (!r.towers.isEmpty() && !tmp.towers.isEmpty()) {
                        if (r.getPlayer().equals(tmp.getPlayer())) {
                            if(board.islands.getIsland(tmp.getId()).next.equals(board.islands.getIsland(r.getId()))||board.islands.getIsland(r.getId()).next.equals(board.islands.getIsland(tmp.getId()))) {
                                int min = Math.min(tmp.getId(), r.getId());
                                mergeIslands(tmp.getId(), r.getId());
                                tmp = getB().islands.getIsland(min);
                            }else throw new ConsecutiveIslandException("The islands are not consecutive, impossible to merge!");
                        }
                        if (tmp.getPlayer().equals(tmp.next.getPlayer())) {
                            if(board.islands.getIsland(tmp.getId()).next.equals(board.islands.getIsland(tmp.next.getId()))||board.islands.getIsland(tmp.next.getId()).next.equals(board.islands.getIsland(tmp.getId()))) {
                                mergeIslands(tmp.getId(), tmp.next.getId());
                            }else throw new ConsecutiveIslandException("The islands are not consecutive, impossible to merge!");
                        }
                    }
                } else throw new ImpossibleActionException("No card has this movement value.");
                order.remove(0);
            }else throw new ImpossibleActionException("Is not your turn or you still have to place students.");
        }else throw new ImpossibleActionException("Not the correct phase in which you can move Students! \n");

        if(order.isEmpty())
            changePhase();
        setChanged();
        notifyObservers();
    }

    public void determineInfluence(int index) throws ImpossibleActionException {
        /*TODO: scrivere le eccezioni.
           Prima va definito bene il calcolo dell'influenza.*/
        if(this.board.islands.getIsland(index).TD){
            this.board.islands.getIsland(index).removeTD();
            characterSelector.restoreTD();
        }
        else {
            ArrayList<Integer> p = new ArrayList<>();
            int x = 0;
            for (int i = 0; i < playerCount; i++) {
                p.add(x);
            }
            for (Tower t : this.board.islands.getIsland(index).towers) {
                for (int z = 0; z < playerCount; z++) {
                    if (t.getPlayer().equals(this.players.get(z)))
                        p.set(z, p.get(z) + Tower.getInfluence());
                    if(this.players.get(z).equals(this.PwBonus))
                        p.set(z, p.get(z) + InfluenceBonus);
                }
            }
            for (Student s : this.board.islands.getIsland(index).getStudents()) {
                if(colorTranslator(s.getColor()).getPlayer() != null)
                    p.set(players.indexOf(colorTranslator(s.getColor()).getPlayer()), p.get(players.indexOf(colorTranslator(s.getColor()).getPlayer())) + colorTranslator(s.getColor()).getInfluence());
            }
            ArrayList<Integer> q = new ArrayList<>(p);
            Collections.sort(p);
            if (!p.get(p.size()-1).equals(p.get(p.size()-2))) {
                if (this.board.islands.getIsland(index).towers.isEmpty()) {
                    this.board.islands.getIsland(index).addTower(players.get(q.indexOf(p.get(p.size()-1))));
                    players.get(q.indexOf(p.get(p.size()-1))).removeTower();
                } else if (!players.get(q.indexOf(p.get(p.size()-1))).equals(this.board.islands.getIsland(index).getPlayer())) {
                    swapTowers(index, players.get(q.indexOf(p.get(p.size()-1))));
                    players.get(q.indexOf(p.get(p.size()-1))).removeTower();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    public void swapTowers(int index, Player player1) throws ImpossibleActionException {
        if (board.islands.getIsland(index).towers != null) {
            for (Tower t : board.islands.getIsland(index).towers)
                t.setPlayer(player1);
        } else throw new ImpossibleActionException("No towers in this island.\n");
    }

    public void mergeIslands(int index1, int index2) {
        //TODO: restituire la nuova isola creata. Controllare MoveMotherNature.
        Island i1, i2;
        i1 = board.islands.getIsland(index1);
        i2 = board.islands.getIsland(index2);
        if (!i1.towers.isEmpty() && !i2.towers.isEmpty() && i1.towers.get(0).getPlayer().equals(i2.towers.get(0).getPlayer()))
            board.islands.mergeIslands(i1, i2);
    }

    public void playCard(String player, int index) throws ImpossibleActionException, BoundException {
        if (roundMaster.round.getCurrentPhase().equals("Planning")) {
            if (index > 0 && index <= 10) {
                Player player1 = playerTranslator(player);
                int i = 0;
                while (!players.get(i).equals(player1)) {
                    i++;
                }
                if (order.get(0).equals(this.players.get(i))) {
                    cardsPlayed.add(this.players.get(i).playCard(index));
                    order.remove(0);
                } else throw new ImpossibleActionException("Not " + players.get(i).nickname + "'s turn!\n");
//When Order.get(0) is equal to NULL, means every player has played. So is time to change phase into "Action";
                if (order.isEmpty()) {
                    changePhase();
                }
            } else throw new ImpossibleActionException("No card with " + index + " as value\n");
        }
        setChanged();
        notifyObservers();
    }

    public void activateCharacter(String player, int id, int parAC1, String parA2, ArrayList<Integer> parAC3, ArrayList<String> parA4, int parC2, ArrayList<Integer> parC4) throws ImpossibleActionException {
        //Keep the unused parameters null, and always use the first parameter in numeric order by type.
        //Parameters will be filled client-side. Parameters marked with "A" are used by AbstractCharacters, with "C" by ConcreteCharacters, and with "AC" by both.
        Player p=playerTranslator(player);
        if(p.getCoins()>= characterSelector.getCost(id)){
            p.removeCoin(characterSelector.getCost(id));
            characterSelector.applyEffect(id, p, parAC1, parA2, parAC3, parA4, parC2, parC4);
        }else throw new ImpossibleActionException("Not enough coins!\n");
        setChanged();
        notifyObservers();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Board getB() {
        return board;
    }

    public Bag getBg() {
        return bag;
    }

    public int getGameType() {
        return gameType;
    }

    ///
    public void addStudentToCloud(String color, int index) {
        board.clouds.get(index).addStudent(color);
    }

    public void addStudentToHall(String color, Player player) {
        player.getHall().setColor(color);
        checkColorChanges(player.getHall().getCardState());
        if(player.getHall().getColor(color)%3==0 && gameType==1){
            player.addCoin();
        }
    }

    public int getColor(Player player, String color){
        return player.getHall().getColor(color);
    }

    public void addToGate(Player p1, String color) {
        p1.getGate().addStudent(color);
    }

    public void addStudentToIsland(String color, int index) {
        board.islands.getIsland(index).addStudent(color);
    }
    ///

    public void removeFromIsland(int sIndex, int index) {
        board.islands.getIsland(index).removeStudent(sIndex);
    }

    public void removeFromGate(Player p1, int index) {
        p1.getGate().removeStudent(index);
    }

    public void removeFromCloud(int indexCloud, int indexStudent) {
        board.clouds.get(indexCloud).removeStudent(indexStudent);
    }

    public void removeFromHall(Player p, String color){ //Probabilmente con le modifiche a Student questo metodo diventa inutile
        p.getHall().desetColor(color);
    }
    ///


    public void checkColorChanges(boolean rule){
        ArrayList<String> colors=new ArrayList<>();
        colors.add("RED");
        colors.add("BLUE");
        colors.add("YELLOW");
        colors.add("GREEN");
        colors.add("PINK");
        for(String ct:colors){
            Player max=colorTranslator(ct).getPlayer();
            if(rule){
                for(Player pl:players){
                    if((max==null&&pl.getHall().getColor(ct)>0)||((max!=null)&&pl.getHall().getColor(ct)>=max.getHall().getColor(ct) && !pl.equals(max)))
                        max=pl;
                }
            }
            else{
                for(Player pl:players){
                    if((max==null&&pl.getHall().getColor(ct)>0)||((max!=null)&&pl.getHall().getColor(ct)>max.getHall().getColor(ct) && !pl.equals(max)))
                        max=pl;
                }
            }
            colorTranslator(ct).setPlayer(max);
        }
    }

    public Player playerTranslator(String name) throws IllegalArgumentException{
        if (name.equals(players.get(0).nickname) || name.equals(players.get(1).nickname) || name.equals(players.get(2).nickname)) {
            if (players.get(0).nickname.equals(name))
                return players.get(0);
            else if (players.get(1).nickname.equals(name))
                return players.get(1);
            else
                return players.get(2);
        } else throw new IllegalArgumentException(name +" does not exists as a nickname.\n");
    }

    public ColorTracker colorTranslator(String color) throws IllegalArgumentException {
        ColorTracker color1;
        if (color.equals("RED") || color.equals("BLUE") || color.equals("YELLOW") || color.equals("GREEN") || color.equals("PINK")) {
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
        }else throw new IllegalArgumentException(color + " does not exist as a color in this game.\n");
    }

    public int getMNbonus() {
        return MNbonus;
    }

    public void setMNbonus(){
        this.MNbonus=2;
    }

    public void disableMNbonus(){
        this.MNbonus=0;
    }

    public void enableInfluenceBonus(Player p){
        this.PwBonus=p;
        this.InfluenceBonus=2;
    }

    public ArrayList<Card> getCardsPlayed(){
        return cardsPlayed;
    }

    public void disableInfluenceBonus(){
        this.InfluenceBonus=0;
    }

    public int getPlayerCount(){
        return playerCount;
    }

}
