package it.polimi.ingsw.MODEL;

public class Controller {
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

    private MotherNature motherNature;
    private Character[] charaCards; //Se viene più comodo si può anche fare come ArrayList o 3 variabili singole eventualmente
    //L'inizializzazione chiama Character(random tra 1 e 12)

    public Controller(int pcount, String string){ //Qua dovrà essere considerato anche il GameType per sapere se inizializzare il Deck di carte personaggio o no
        if(pcount==2){
            this.p1=new Player(pcount, string);
            this.p2=new Player(pcount, string);
            this.board=new Board();
            this.bag=new Bag(this);
            this.handP1=new Hand(p1);
            this.handP2=new Hand(p2);
            this.handP3=new Hand(p3);
            this.cardsplayed=new Card[3];

            this.red=new ColorTracker(Color.RED);
            this.blue=new ColorTracker(Color.BLUE);
            this.green=new ColorTracker(Color.GREEN);
            this.yellow=new ColorTracker(Color.YELLOW);
            this.pink=new ColorTracker(Color.PINK);
        }
    }

    public void Start(){
        Player[] players = new Player[3];
        players[0]=p1;
        players[1]=p2;
        if (p3!=null)
            players[2]=p3;

        roundMaster=new RoundMaster(players);
    }

    public Player[] changePhase(){
        int[] tmp=new int[3];
        Player[] players;

        for(int i=0; i<3; i++){
            tmp[i]=cardsplayed[i].getValue();
        }
        players=roundMaster.changePhase(tmp);
        if (roundMaster.getRoundCount()>10 ||
                p1.getTower_count()==0 ||
                p2.getTower_count()==0 ||
                p3.getTower_count()==0 ){
            winner=this.gameEnd();
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
    ///
    public void addStudentToCloud(Color color, int index){
        board.clouds.get(index).addStudent(color);
    }

    public void addStudentToHall(Color color, Player player){
        player.getHall().setColor(color);
        checkColorChanges(player);
    }

    public void addToGate(Player p1, Color color){
        p1.getGate().addStudent(color);
    }

    public void addStudentToIsland(Color color, int index){
        board.islands.getIsland(index).addStudent(color);
    }
    ///

    public void removeFromIsland(int sIndex, int index) {
        board.islands.getIsland(index).removeStudent(sIndex);
    }

    public void removeFromGate(Player p1, int index){
        p1.getGate().removeStudent(index);
    }

    public void removeFromCloud(int indexCloud, int indexStudent){
        board.clouds.get(indexCloud).removeStudent(indexStudent);
    }

    public void removeFromHall(Color color, Player player){
        player.getHall().desetColor(color);
    }
    ///

    public void bagToCloud(int index){
        board.clouds.get(index).addStudent(bag.extractStudent().getColor());
    }

    public void gateToIsland(String name, int index, int indexIsland, String color){
        Player player1=playerTranslator(name);
        Color color1=colorTranslator(color);

        addStudentToIsland(color1, indexIsland);
        removeFromGate(player1, index);
    }

    public void CloudToGate(String player, String color, int sIndex, int cIndex){ //TODO
        Player p= playerTranslator(player);
        Color color1= colorTranslator(color);
        addToGate(p, color1);
        removeFromCloud(cIndex, sIndex);
        //TODO: assert p.getGate().students.size()<p.getGate().MAX-2, ma con un try/catch
    }

    ///

    public void moveMotherNature(int movement){
        motherNature.getIsola().setMotherNature(false);
        Island tmp=motherNature.getIsola();
        for (int i=0; i<movement; i++){
            tmp=tmp.next;
        }
        tmp.setMotherNature(true);
        motherNature.setIsland(tmp);
    }

    public int determineInfluence(String player, int index){
        Player player1=playerTranslator(player);
        Color[] colors=new Color[5];

        if(red.getPlayer().equals(player1))
            colors[0]=Color.RED;
        else colors[0]=null;

        if(blue.getPlayer().equals(player1))
            colors[1]=Color.BLUE;
        else colors[1]=null;

        if(green.getPlayer().equals(player1))
            colors[2]=Color.GREEN;
        else colors[2]=null;

        if(yellow.getPlayer().equals(player1))
            colors[3]=Color.YELLOW;
        else colors[3]=null;

        if(pink.getPlayer().equals(player1))
            colors[4]=Color.PINK;
        else colors[4]=null;

        int influenceTowers=0;
        int i=0;
        while (board.islands.getIsland(index).towers[i]!=null) {
            influenceTowers = influenceTowers + board.islands.getIsland(index).towers[i].getInfluence();
            i++;
        }
        return influenceTowers + board.islands.getIsland(index).getStudent_Influence(colors);
    }

    public void swapTowers(int index, String playerTO){
        Player player1=playerTranslator(playerTO);

        int i=0;
        while (board.islands.getIsland(index).towers[i]!=null) {
            board.islands.getIsland(index).towers[i].setPlayer(player1);
            i++;
        }

    }

    public void mergeIslands(int index1, int index2){
        Island i1, i2;
        i1=board.islands.getIsland(index1);
        i2=board.islands.getIsland(index2);
        board.islands.mergeIslands(i1, i2);
    }

    public void playCard(String player, int index){
        Player player1=playerTranslator(player);

        if(handP1.player.equals(player1)){
            cardsplayed[0]=handP1.cards[index];
            handP1.cards[index]=null;
        }
        if(handP2.player.equals(player1)){
            cardsplayed[1]=handP2.cards[index];
            handP2.cards[index]=null;
        }
        if(handP3.player.equals(player1)){
            cardsplayed[2]=handP3.cards[index];
            handP3.cards[index]=null;
        }
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

    public Controller getGame() {
        return this;
    }

    private Player playerTranslator(String name){
        if(p1.nickname.equals(name))
            return p1;
        else if(p2.nickname.equals(name))
            return p2;
        else
            return p3;
    }

    private Color colorTranslator(String color){
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
}
