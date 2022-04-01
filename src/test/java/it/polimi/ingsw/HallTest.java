package it.polimi.ingsw;

import it.polimi.ingsw.MODEL.Color;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.Hall;
import it.polimi.ingsw.MODEL.Player;

import junit.framework.TestCase;
import org.junit.*;

public class HallTest {

    @Test
    public void setColor() {
        Hall hall=new Hall(new Player("Pier", new Game(2, "Pier")));
        Color color= Color.RED;
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