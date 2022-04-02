package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private int gameType; //0: regole semplificate, 1: regole esperto
    private final ArrayList<Player> players;
    private ArrayList<Controller> controllers;
    private Card[] cardsPlayed;
    private final Bag bag;
    private final Board board;
    public final ColorTracker red, blue, green, yellow, pink;
    public RoundMaster roundMaster;
    public int playerCount;
    private Player winner;
    private final ArrayList<Hand> hands;
    private final CharacterSelector characterSelector;
    private final MotherNature motherNature;

    public Game(int pcount, int gt, String nick1, Socket sock1, String nick2, Socket sock2, String nick3, Socket sock3){
        //Parameters: num of players, gametype, nickname and socket for every player
        controllers.add(new Controller(this, sock1));
        controllers.add(new Controller(this, sock2));
        if(pcount==3){
            controllers.add(new Controller(this, sock3));
        }
        for(Controller c: controllers){
            new Thread(c).start();
        }

        this.players=new ArrayList<>();
        this.hands=new ArrayList<>();
        this.bag=new Bag(this);
        this.red=new ColorTracker(Color.RED);
        this.blue=new ColorTracker(Color.BLUE);
        this.green=new ColorTracker(Color.GREEN);
        this.yellow=new ColorTracker(Color.YELLOW);
        this.pink=new ColorTracker(Color.PINK);
        this.characterSelector=new CharacterSelector(this);
        if(pcount==2){
            this.board=new Board(this);
            this.players.add(new Player(string, this));
            this.players.add(new Player(string, this));
            this.hands.add(new Hand(players.get(0)));
            this.hands.add(new Hand(players.get(1)));
            this.cardsPlayed =new Card[3];
        }
        else{
            this.board=new Board(pcount, this);
            this.players.add(new Player(pcount, string, this));
            this.players.add(new Player(pcount, string, this));
            this.players.add(new Player(pcount, string, this));
            this.hands.add(new Hand(players.get(0)));
            this.hands.add(new Hand(players.get(1)));
            this.hands.add(new Hand(players.get(2)));
        }
        this.motherNature=new MotherNature(board.islands.getIsland(1));
        if(this.gameType==1){
            for(Player p:this.players){
                p.addCoin();
            }
        }
    }

    public void start() throws GameException {
        if(playerCount>1 && playerCount<4) {
            if (playerCount == 2) {
                if (roundMaster.getRoundCount() == 0) {
                    Player[] players = new Player[2];
                    players[0] = this.players.get(0);
                    players[1] = this.players.get(1);
                    roundMaster = new RoundMaster(players);
                } else throw new GameException("Game already started!\n");
            }
            if (playerCount == 3) {
                if (roundMaster.getRoundCount() == 0) {
                    Player[] players = new Player[3];
                    players[0] = this.players.get(0);
                    players[1] = this.players.get(1);
                    players[2] = this.players.get(2);
                    roundMaster = new RoundMaster(players);
                } else throw new GameException("Game already started!\n");
            }
        } else throw new GameException("Number of players not allowed.\n");
    }

    public Player[] changePhase(){
            int[] tmp = new int[3];
            Player[] players;

            for (int i = 0; i < 3; i++) {
                if(cardsPlayed[i]!=null)
                    tmp[i] = cardsPlayed[i].getValue();
                else tmp[i]=11;
            }
            players = roundMaster.changePhase(tmp);
            if (roundMaster.getRoundCount() > 10 ||
                    this.players.get(0).getTower_count() == 0 ||
                    this.players.get(1).getTower_count() == 0 ||
                    this.players.get(2).getTower_count() == 0) {
                winner = this.gameEnd();
            }
            return players;
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

    public void bagToCloud(int index) throws BoundException{
        if(bag.getSize()==0)
            throw new BoundException("The bag is empty!\n");
        if(index>0 && index <= 2 && board.clouds.get(index).students.size()<3)
            board.clouds.get(index).addStudent(bag.extractStudent().getColor());

        else throw new BoundException("INDEX OUT OF BOUND!\n");
    }

    public void gateToIsland(String name, int index, int indexIsland, String color) throws BoundException{
        try {
            Player player1 = playerTranslator(name);
            ColorTracker color1 = colorTranslator(color);

            if(player1.getGate().students.size() >= player1.getGate().MAX - 2) {
                addStudentToIsland(color1, indexIsland);
                removeFromGate(player1, index);
            } else  throw new BoundException(player1 + " can't place anymore students.\n");

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex) throws BoundException { //TODO

        try {
            Player p = playerTranslator(player);
            ColorTracker color1 = colorTranslator(color);

            if (p.getGate().students.size() < p.getGate().MAX - 2 && !board.clouds.get(sIndex).students.isEmpty()) {
                addToGate(p, color1);
                removeFromCloud(cIndex, sIndex);
            } else throw new BoundException("Not enough space in" +p+ "gate, or the cloud is empty.\n");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    ///

    public void moveMotherNature(int movement) throws ImpossibleActionException {
        if (movement < 7) {
            motherNature.getIsola().setMotherNature(false);
            Island tmp = motherNature.getIsola();
            for (int i = 0; i < movement; i++) {
                tmp = tmp.next;
            }
            tmp.setMotherNature(true);
            motherNature.setIsland(tmp);
        }else throw new ImpossibleActionException("No card has this movement value.");
    }

    public int determineInfluence(int index) throws ImpossibleActionException {
        /*TODO: scrivere le eccezioni.
           Prima va definito bene il calcolo dell'influenza.*/
        if(this.board.islands.getIsland(index).TD){
            this.board.islands.getIsland(index).removeTD();
            characterSelector.restoreTD();
        }
        else {
            ArrayList<Integer> p = new ArrayList<>();
            for (int i = 0; i < playerCount; i++) {
                int x = 0;
                p.add(x);
            }
            for (Tower t : this.board.islands.getIsland(index).towers) {
                for (int z = 0; z < playerCount; z++) {
                    if (t.getPlayer().equals(this.players.get(z)))
                        p.set(z, p.get(z) + Tower.getInfluence());
                }
            }
            for (Student s : this.board.islands.getIsland(index).students) {
                p.set(players.indexOf(s.getColor().getPlayer()), p.get(players.indexOf(s.getColor().getPlayer())) + s.getInfluence());
            }
            ArrayList<Integer> tmp = p;
            Collections.sort(tmp);
            if (!tmp.get(tmp.size()-1).equals(tmp.get(tmp.size()-2))) {
                if (this.board.islands.getIsland(index).towers.isEmpty()) {
                    this.board.islands.getIsland(index).addTower(players.get(p.indexOf(tmp.get(tmp.size()-1))));
                    players.get(p.indexOf(tmp.get(tmp.size()-1))).removeTower();
                } else if (!players.get(p.indexOf(tmp.get(tmp.size()-1))).equals(this.board.islands.getIsland(index).getPlayer())) {
                    swapTowers(index, players.get(p.indexOf(tmp.get(tmp.size()-1))));
                    players.get(p.indexOf(tmp.get(tmp.size()-1))).removeTower();
                }
            }
        }
    }

    public void swapTowers(int index, Player player1) throws ImpossibleActionException{
        try {
            if(board.islands.getIsland(index).towers!=null) {
                int i = 0;
                while (board.islands.getIsland(index).towers.get(i) != null) {
                    board.islands.getIsland(index).towers.get(i).setPlayer(player1);
                    i++;
                }
            } else throw new ImpossibleActionException("No towers in this island.\n");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    public void mergeIslands(int index1, int index2) throws ConsecutiveIslandException {
        if(board.islands.getIsland(index1).next.equals(board.islands.getIsland(index2))) {
            Island i1, i2;
            i1 = board.islands.getIsland(index1);
            i2 = board.islands.getIsland(index2);
            board.islands.mergeIslands(i1, i2);
        }else throw new ConsecutiveIslandException("The islands are not consecutive, impossible to merge!");
    }

    public void playCard(String player, int index) throws ImpossibleActionException {
        if(index>0 && index<=10) {
            try {
                Player player1 = playerTranslator(player);

                if (this.hands.get(0).player.equals(player1)) {
                    cardsPlayed[0] = this.hands.get(0).cards[index];
                    this.hands.get(0).cards[index] = null;
                }
                if (this.hands.get(1).player.equals(player1)) {
                    cardsPlayed[1] = this.hands.get(1).cards[index];
                    this.hands.get(1).cards[index] = null;
                }
                if (this.hands.get(2).player.equals(player1)) {
                    cardsPlayed[2] = this.hands.get(2).cards[index];
                    this.hands.get(2).cards[index] = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else throw new ImpossibleActionException("No card with "+index+" as value\n");
    }

    public void activateCharacter(String player, int id){

    }

    public Player getP1() {
        return this.players.get(0);
    }

    public Player getP2() {
        return this.players.get(1);
    }

    public Board getB() {
        return board;
    }

    public Bag getBg() {
        return bag;
    }

    public Game getGame() {
        return this;
    }

    public int getGameType() {
        return gameType;
    }

    ///
    public void addStudentToCloud(ColorTracker color, int index) {
        board.clouds.get(index).addStudent(color);
    }

    public void addStudentToHall(ColorTracker color, Player player) {
        player.getHall().setColor(color);
        checkColorChanges(player);
    }

    public void addToGate(Player p1, ColorTracker color) {
        p1.getGate().addStudent(color);
    }

    public void addStudentToIsland(ColorTracker color, int index) {
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

    public void removeFromHall(ColorTracker color, Player player) {
        player.getHall().desetColor(color);
    }
    ///
    private void checkColorChanges(Player player1){
        if( p1.getHall().getRed()>p2.getHall().getRed() &&  p1.getHall().getRed()>p3.getHall().getRed())
            red.setPlayer(player1);
        if( p1.getHall().getBlue()>p2.getHall().getBlue() &&  p1.getHall().getBlue()>p3.getHall().getBlue())
            blue.setPlayer(player1);
        if( p1.getHall().getGreen()>p2.getHall().getGreen() &&  p1.getHall().getGreen()>p3.getHall().getGreen())
            green.setPlayer(player1);
        if( p1.getHall().getYellow()>p2.getHall().getYellow() &&  p1.getHall().getYellow()>p3.getHall().getYellow())
            yellow.setPlayer(player1);
        if( p1.getHall().getPink()>p2.getHall().getPink() &&  p1.getHall().getPink()>p3.getHall().getPink())
            pink.setPlayer(player1);
    }

    private Player playerTranslator(String name) throws IllegalArgumentException{
        if (name.equals(p1.nickname) || name.equals(p2.nickname) || name.equals(p3.nickname)) {
            if (p1.nickname.equals(name))
                return p1;
            else if (p2.nickname.equals(name))
                return p2;
            else
                return p3;
        } else throw new IllegalArgumentException(name +" does not exists as a nickname.\n");
    }

    private ColorTracker colorTranslator(String color) throws IllegalArgumentException {
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

}
