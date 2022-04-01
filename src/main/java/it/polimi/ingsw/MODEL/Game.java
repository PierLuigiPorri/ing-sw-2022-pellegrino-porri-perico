package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public class Game {
    private int gameType; //0: regole semplificate, 1: regole esperto
    private Player p1, p2, p3;
    private Card[] cardsplayed;
    private Bag bag;
    private Board board;
    private ColorTracker red, blue, green, yellow, pink;
    private RoundMaster roundMaster;
    public int playerCount;
    private Player winner;
    private Hand handP1, handP2, handP3;
    private CharacterSelector characterSelector;


    private MotherNature motherNature;

    public Game(int pcount, String string){ //Qua dovrà essere considerato anche il GameType per sapere se inizializzare il Deck di carte personaggio o no
        if(pcount==2){
            this.p1=new Player(pcount, string);
            this.p2=new Player(pcount, string);

            this.board=new Board();
            this.bag=new Bag();
            this.handP1=new Hand(p1);
            this.handP2=new Hand(p2);
            this.handP3=new Hand(p3);
            this.cardsplayed=new Card[3];
            this.motherNature=new MotherNature(board.islands.getIsland(1));
            this.characterSelector=new CharacterSelector(this);

            this.red=new ColorTracker(Color.RED);
            this.blue=new ColorTracker(Color.BLUE);
            this.green=new ColorTracker(Color.GREEN);
            this.yellow=new ColorTracker(Color.YELLOW);
            this.pink=new ColorTracker(Color.PINK);
        }
    }

    public void start() throws GameException {
        if(playerCount>1 && playerCount<4) {
            if (playerCount == 2) {
                if (roundMaster.getRoundCount() == 0) {
                    Player[] players = new Player[2];
                    players[0] = p1;
                    players[1] = p2;
                    roundMaster = new RoundMaster(players);
                } else throw new GameException("Game already started!\n");
            }
            if (playerCount == 3) {
                if (roundMaster.getRoundCount() == 0) {
                    Player[] players = new Player[3];
                    players[0] = p1;
                    players[1] = p2;
                    players[2] = p3;
                    roundMaster = new RoundMaster(players);
                } else throw new GameException("Game already started!\n");
            }
        } else throw new GameException("Number of players not allowed.\n");
    }

    public Player[] changePhase(){
            int[] tmp = new int[3];
            Player[] players;

            for (int i = 0; i < 3; i++) {
                if(cardsplayed[i]!=null)
                    tmp[i] = cardsplayed[i].getValue();
                else tmp[i]=11;
            }
            players = roundMaster.changePhase(tmp);
            if (roundMaster.getRoundCount() > 10 ||
                    p1.getTower_count() == 0 ||
                    p2.getTower_count() == 0 ||
                    p3.getTower_count() == 0) {
                winner = this.gameEnd();
            }
            return players;
    }

    public Player gameEnd(){
        int[] x;
        x=new int[3];
        int min;
        x[0]=p1.getTower_count();
        x[1]=p2.getTower_count();
        x[2]=p3.getTower_count();
        min=Math.min(Math.min(x[0], x[1]),x[2]);
        if(min==x[0])
            return p1;
        else if(min==x[1])
            return p2;
        else
            return p3;
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
            Color color1 = colorTranslator(color);

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
            Color color1 = colorTranslator(color);

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

    public int determineInfluence(String player, int index){
        /*TODO: scrivere le eccezioni.
           Prima va definito bene il calcolo dell'influenza.*/

        Player player1=playerTranslator(player);
        ArrayList<Color> colors = new ArrayList<>();

        if(red.getPlayer().equals(player1))
            colors.add(Color.RED);
        if(blue.getPlayer().equals(player1))
            colors.add(Color.BLUE);
        if(green.getPlayer().equals(player1))
            colors.add(Color.GREEN);
        if(yellow.getPlayer().equals(player1))
            colors.add(Color.YELLOW);
        if(pink.getPlayer().equals(player1))
            colors.add(Color.PINK);

        int influenceTowers=0;
        int i=0;
        while (board.islands.getIsland(index).towers[i]!=null) {
            influenceTowers = influenceTowers + board.islands.getIsland(index).towers[i].getInfluence();
            i++;
        }
        return influenceTowers + board.islands.getIsland(index).getStudent_Influence(colors);
    }

    public void swapTowers(int index, String playerTO) throws ImpossibleActionException{
        try {
            Player player1 = playerTranslator(playerTO);
            if(board.islands.getIsland(index).towers[0]!=null) {
                int i = 0;
                while (board.islands.getIsland(index).towers[i] != null) {
                    board.islands.getIsland(index).towers[i].setPlayer(player1);
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

                if (handP1.player.equals(player1)) {
                    cardsplayed[0] = handP1.cards[index];
                    handP1.cards[index] = null;
                }
                if (handP2.player.equals(player1)) {
                    cardsplayed[1] = handP2.cards[index];
                    handP2.cards[index] = null;
                }
                if (handP3.player.equals(player1)) {
                    cardsplayed[2] = handP3.cards[index];
                    handP3.cards[index] = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else throw new ImpossibleActionException("No card with "+index+" as value\n");
    }

    public void activateCharacter(String player, int id){

    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
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
    public void addStudentToCloud(Color color, int index) {
        board.clouds.get(index).addStudent(color);
    }

    public void addStudentToHall(Color color, Player player) {
        player.getHall().setColor(color);
        checkColorChanges(player);
    }

    public void addToGate(Player p1, Color color) {
        p1.getGate().addStudent(color);
    }

    public void addStudentToIsland(Color color, int index) {
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

    public void removeFromHall(Color color, Player player) {
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

    private Color colorTranslator(String color) throws IllegalArgumentException {
        Color color1;
        if (color.equals("RED") || color.equals("BLUE") || color.equals("YELLOW") || color.equals("GREEN") || color.equals("PINK")) {
            switch (color) {
                case "RED":
                    color1 = Color.RED;
                    break;
                case "BLUE":
                    color1 = Color.BLUE;
                    break;
                case "GREEN":
                    color1 = Color.GREEN;
                    break;
                case "YELLOW":
                    color1 = Color.YELLOW;
                    break;
                default:
                    color1 = Color.PINK;
                    break;
            }
            return color1;
        }else throw new IllegalArgumentException(color + " does not exist as a color in this game.\n");
    }

}
