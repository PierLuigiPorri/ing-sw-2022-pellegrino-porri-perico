package it.polimi.ingsw;

import it.polimi.ingsw.MODEL.*;

import junit.framework.TestCase;
import org.junit.*;

public class HallTest {

    @Test
    public void setColor() {
        Hall hall=new Hall(new Player("Pier", new Game(2, "Pier")));
        ColorTracker color= new ColorTracker(Color.RED);
        hall.setColor(color);
        Assert.assertEquals(1, hall.getRed());
    }

    @Test
    public void name1() {
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
}