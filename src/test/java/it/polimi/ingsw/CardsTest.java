package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.Assert.*;

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

    @Test
    public void concreteSetup(){
        for(CharacterType c:game.characterSelector.getCharacters()){
            if(c.getIndex()==0 || c.getIndex()==10 || c.getIndex()==6){
                ConcreteCharacter w=(ConcreteCharacter) c;
                assertNotNull(w.getStudents());
                assertFalse(w.getStudents().contains(null));
                assertEquals(w.getStudents().size(), w.getMAX());
            }
            else if(c.getIndex()==4){
                ConcreteCharacter w=(ConcreteCharacter) c;
                assertEquals(w.getTD(), 4);
                Island t=game.getB().islands.head;
                while(t!=game.getB().islands.tail){
                    assertFalse(t.TD);
                }
            }
        }

    }

    @Test
    public void EffectsTest() throws ImpossibleActionException {
        for (int x = 0; x < 30; x++) {
                game.getPlayers().get(0).addCoin();
            }
        ArrayList<Integer> a=new ArrayList<>(), b=new ArrayList<>();
        ArrayList<String> c=new ArrayList<>();
        for(int t=0; t<2; t++){
            a.add(t);
            b.add(t);
            c.add("RED");
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("\n "+ game.characterSelector.getCharacters().get(i).getIndex());
            game.activateCharacter("FRANCO", i, 1, "RED", a, c, 1, b);
            /*switch(c.getIndex()){
                case 0:

            }*/
        }
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
