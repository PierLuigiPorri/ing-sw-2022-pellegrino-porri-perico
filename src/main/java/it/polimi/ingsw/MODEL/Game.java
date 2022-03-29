package it.polimi.ingsw.MODEL;

public class Game {
    private int gameType; //0: regole semplificate, 1: regole esperto
    private Player p1, p2, p3, p4;
    private Card[] cardsplayed;
    private Bag bag;
    private Board board;
    private RoundMaster roundMaster;
    public int playerCount;
    private Player winner;
    private MotherNature motherNature;
    private Character[] charaCards; //Se viene più comodo si può anche fare come ArrayList o 3 variabili singole eventualmente
    //L'inizializzazione chiama Character(random tra 1 e 12)

    public Game(int pcount, String string){ //Qua dovrà essere considerato anche il GameType per sapere se inizializzare il Deck di carte personaggio o no
        if(pcount==2){
            this.p1=new Player(pcount, string);
            this.p2=new Player(pcount, string);
            this.board=new Board();
            this.bag=new Bag(this);
        }
    }

    public void Start(){
        Player[] players = new Player[4];
        players[0]=p1;
        players[1]=p2;
        if (p3!=null)
            players[2]=p3;
        if (p4!=null)
            players[3]=p4;

        roundMaster=new RoundMaster(players);
    }

    public void changePhase(){
        int[] tmp=new int[4];
        for(int i=0; i<4; i++){
            tmp[i]=cardsplayed[i].getValue();
        }
        roundMaster.changePhase(tmp);
        if (roundMaster.getRoundCount()>10 ||
                p1.getTower_count()==0 ||
                p2.getTower_count()==0 ||
                p3.getTower_count()==0 ||
                p4.getTower_count()==0){
            winner=this.gameEnd();
        }
    }

    public Player gameEnd(){
        int[] x;
        x=new int[4];
        int min;
        x[0]=p1.getTower_count();
        x[1]=p2.getTower_count();
        x[2]=p3.getTower_count();
        x[3]=p4.getTower_count();
        min=Math.min(Math.min(x[0], x[1]), Math.min(x[2], x[3]));
        if(min==x[0])
            return p1;
        else if(min==x[1])
            return p2;
        else if(min==x[2])
            return p3;
        else
            return p4;
    }

    public void addStudentToHall(String color, String player){
        Color color1=colorTrnslator(color);
        Player player1=playerTranslator(player);

        player1.getGate().removeStudent(color1);
        player1.getHall().setColor(color1);
    }

    public void addStudentToGate(String color, String player, int index, int i ){
        Color color1=colorTrnslator(color);
        Player player1=playerTranslator(player);

        board.getClouds()[index].removeStudent(i);
        player1.getGate().addStudent(board.getClouds()[index].removeStudent(i).getColor());
    }

    public void addStudentToIsland(String color, int index, String player){
        Color color1=colorTrnslator(color);
        Player player1=playerTranslator(player);

        player1.getGate().removeStudent(color1);
        board.getIslands().getIsland(index).addStudent(color1);

    }

    public void addStudentToCloud(int index){
        board.getClouds()[index].addStudent(bag.extractStudent().getColor());
    }


    public void removeStudentFromIsland(String color, int index){
        Color color1=colorTrnslator(color);
        board.getIslands().getIsland(index).removeStudent(color1);
    }

    public void moveMotherNature(int movement){
        board.getIslands().getIsland(motherNature.getIsola()).setMotherNature(false);
        board.getIslands().getIsland(motherNature.getIsola()+movement).setMotherNature(true);
        motherNature.moveIntoIsland(movement);
    }

    public int determineInfluence(String player, int index){
        Player player1=playerTranslator(player);
        Color[] colors;

        colors=player1.getColorDominated(p2, p3, p4);
        return board.getIslands().getIsland(index).towers[0].getInfluence() +
                board.getIslands().getIsland(index).getStudent_Influence(colors);
    }


    //TODO: non so come fare lo swap. -Pier
    public void swapTowers(int index, String playerTO){
        Player p1=playerTranslator(playerTO);

        board.getIslands().getIsland(index).towers[0].setPlayer(p1);
    }

    public void mergeIslands(int index1, int index2){}

    public void playCard(String player, int index){}

    public void activateCharacter(String player, int id){}

    public void turnPass(String player){}

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

    private Player playerTranslator(String name){
        if(p1.nickname.equals(name))
            return p1;
        else if(p2.nickname.equals(name))
            return p2;
        else if(p3.nickname.equals(name))
            return p3;
        else
            return p4;
    }

    private Color colorTrnslator(String color){
        Color color1;
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
    }

    public int getGameType() {
        return gameType;
    }
}
