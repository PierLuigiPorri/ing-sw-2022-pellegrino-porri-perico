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
        ColorTracker color1= new ColorTracker(Color.RED);
        ColorTracker color2= new ColorTracker(Color.BLUE);
        ColorTracker color3= new ColorTracker(Color.GREEN);
        ColorTracker color4= new ColorTracker(Color.YELLOW);
        ColorTracker color5= new ColorTracker(Color.PINK);
        hall.setColor(color1);
        hall.setColor(color1);
        hall.setColor(color2);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color3);
        hall.setColor(color1);

        r= hall.getColor(Color.RED);
        b= hall.getColor(Color.BLUE);
        g= hall.getColor(Color.GREEN);
        y= hall.getColor(Color.YELLOW);
        p= hall.getColor(Color.PINK);

        Assert.assertEquals(0, y);
        Assert.assertEquals(3, r);
        Assert.assertEquals(1, b);
        Assert.assertEquals(3, g);
        Assert.assertEquals(0, y);
        Assert.assertEquals(0, p);
    }

    @Test
    public void desetColor() {
        ColorTracker color1= new ColorTracker(Color.RED);
        ColorTracker color2= new ColorTracker(Color.BLUE);
        ColorTracker color3= new ColorTracker(Color.GREEN);
        ColorTracker color4= new ColorTracker(Color.YELLOW);
        ColorTracker color5= new ColorTracker(Color.PINK);
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
        try {
            hall.desetColor(color1);
            hall.desetColor(color1);
            hall.desetColor(color2);
            hall.desetColor(color3);
            hall.desetColor(color3);
            hall.desetColor(color3);
            hall.desetColor(color1);
            hall.desetColor(color5);
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
        r= hall.getColor(Color.RED);
        b= hall.getColor(Color.BLUE);
        g= hall.getColor(Color.GREEN);
        y= hall.getColor(Color.YELLOW);
        p= hall.getColor(Color.PINK);

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
        player=new Player("Pier", game);
        hall=new Hall(player);
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