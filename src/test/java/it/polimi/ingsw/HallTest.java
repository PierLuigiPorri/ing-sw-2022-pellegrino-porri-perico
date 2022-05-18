package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class HallTest {

    private Game game;
    private Player player;
    private int r;
    private int b;
    private int g;
    private int y;
    private int p;


    @Test
    public void setColor() {
        game.addStudentToHall("RED", player);
        game.addStudentToHall("RED", player);
        game.addStudentToHall("BLUE", player);
        game.addStudentToHall("GREEN", player);
        game.addStudentToHall("GREEN", player);
        game.addStudentToHall("GREEN", player);
        game.addStudentToHall("RED", player);


        r= game.getColor(player, "RED");
        b= game.getColor(player, "BLUE");
        g= game.getColor(player, "GREEN");
        y= game.getColor(player, "YELLOW");
        p= game.getColor(player, "PINK");

        Assert.assertEquals(0, y);
        Assert.assertEquals(3, r);
        Assert.assertEquals(1, b);
        Assert.assertEquals(3, g);
        Assert.assertEquals(0, y);
        Assert.assertEquals(0, p);
    }

    @Test
    public void desetColor() {

        player.getHall().setColor("PINK");
        player.getHall().setColor("RED");
        player.getHall().setColor("RED");
        player.getHall().setColor("RED");
        player.getHall().setColor("RED");
        player.getHall().setColor("BLUE");
        player.getHall().setColor("BLUE");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("GREEN");
        player.getHall().setColor("RED");
        player.getHall().setColor("YELLOW");
        player.getHall().setColor("YELLOW");
        player.getHall().setColor("YELLOW");
        player.getHall().setColor("YELLOW");
        player.getHall().setColor("YELLOW");

        game.removeFromHall(player, "RED");
        game.removeFromHall(player, "RED");
        game.removeFromHall(player, "BLUE");
        game.removeFromHall(player, "GREEN");
        game.removeFromHall(player, "GREEN");
        game.removeFromHall(player, "GREEN");
        game.removeFromHall(player, "RED");
        game.removeFromHall(player, "PINK");

        r= game.getColor(player, "RED");
        b= game.getColor(player, "BLUE");
        g= game.getColor(player, "GREEN");
        y= game.getColor(player, "YELLOW");
        p= game.getColor(player, "PINK");

        Assert.assertEquals(5, y);
        Assert.assertEquals(2, r);
        Assert.assertEquals(1, b);
        Assert.assertEquals(6, g);
        Assert.assertEquals(5, y);
        Assert.assertEquals(0, p);
    }

    @Before
    public void setUp() throws ImpossibleActionException {
        game=new Game(2, 0, "Pier", "Paolo", null);
        player=new Player(2, "Pier", game);
    }

    @After
    public void tearDown() {
        r=0;
        b=0;
        g=0;
        y=0;
        p=0;
    }
}