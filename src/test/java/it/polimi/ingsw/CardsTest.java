package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class CardsTest
{
    public Game game;
    public Player p1, p2;

    @Test
    public void cardsCreation()
    {
        assertNotNull(game.characterSelector);
        assertNotNull(game.characterSelector.getCharacters());
        assertEquals(3, game.characterSelector.getCharacters().size());
        assertFalse(game.characterSelector.getCharacters().contains(null));
        assertTrue(p1.getCoins()==1&&p2.getCoins()==1);
    }




    @Before
    public void setUp() throws Exception {
        try{
            game = new Game(2, 1, "FRANCO", null, "CARMINE", null, null, null);
            p1=new Player(2,"FRANCO", game);
            p2=new Player(2, "CARMINE", game);
        }catch (GameException e){
            System.out.println(e.getMessage());
        }

    }
}
