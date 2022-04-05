package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class HallTest {

    private Game game;
    private Player player;
    private Hall hall;
    private int r;
    private int b;
    private int g;
    private int y;
    private int p;


    @Test
    public void setColor() {
        ColorTracker color1= game.red;
        ColorTracker color2= game.blue;
        ColorTracker color3= game.green;
        ColorTracker color4= game.yellow;
        ColorTracker color5= game.pink;
        hall.setColor(color1);
        hall.setColor(color1);
        hall.setColor(color2);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color1);

        r= game.getColor(player, game.red);
        b= game.getColor(player, game.blue);
        g= game.getColor(player, game.green);
        y= game.getColor(player, game.yellow);
        p= game.getColor(player, game.pink);

        Assert.assertEquals(0, y);
        Assert.assertEquals(3, r);
        Assert.assertEquals(1, b);
        Assert.assertEquals(3, g);
        Assert.assertEquals(0, y);
        Assert.assertEquals(0, p);
    }

    @Test
    public void desetColor() {
        ColorTracker color1= game.red;
        ColorTracker color2= game.blue;
        ColorTracker color3= game.green;
        ColorTracker color4= game.yellow;
        ColorTracker color5= game.pink;

        hall.setColor(color5);
        hall.setColor(color1);
        hall.setColor(color1);
        hall.setColor(color1);
        hall.setColor(color1);
        hall.setColor(color2);
        hall.setColor(color2);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color1);
        hall.setColor(color4);
        hall.setColor(color4);
        hall.setColor(color4);
        hall.setColor(color4);
        hall.setColor(color4);

        game.removeFromHall(player, color1);
        game.removeFromHall(player, color1);
        game.removeFromHall(player, color2);
        game.removeFromHall(player, color3);
        game.removeFromHall(player, color3);
        game.removeFromHall(player, color3);
        game.removeFromHall(player, color1);
        game.removeFromHall(player, color5);

        r= game.getColor(player, game.red);
        b= game.getColor(player, game.blue);
        g= game.getColor(player, game.green);
        y= game.getColor(player, game.yellow);
        p= game.getColor(player, game.pink);

        Assert.assertEquals(5, y);
        Assert.assertEquals(2, r);
        Assert.assertEquals(1, b);
        Assert.assertEquals(6, g);
        Assert.assertEquals(5, y);
        Assert.assertEquals(0, p);
    }

    @Before
    public void setUp() throws Exception {
        game=new Game(2, 0, "Pier", null, "Paolo", null, null, null);
        player=new Player(2, "Pier", game);
        hall=new Hall();
    }

    @After
    public void tearDown() throws Exception {
        r=0;
        b=0;
        g=0;
        y=0;
        p=0;
    }
}