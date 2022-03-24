package it.polimi.ingsw.MODEL;

public class Game2_3 {
    public Game2_3(int pcount){
        if(pcount==2){
            Player p1=new Player();
            Player p2=new Player();
            Board b=new Board();
            Bag bg=new Bag();
            Game game=new Game(p1,p2);
        }
    }
}
