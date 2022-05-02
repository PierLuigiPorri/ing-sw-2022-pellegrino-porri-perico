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
            String temp = null;
            int quant=0;
            System.out.println("\n "+ game.characterSelector.getCharacters().get(i).getIndex());
            switch(game.characterSelector.getCharacters().get(i).getIndex()){ //not all the cards are tested due to not being necessary
                case 0:
                    ConcreteCharacter car= (ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    temp=car.getStudents().get(1).getColor();
                    for(Student st: game.getB().islands.getIsland(1).getStudents()){
                        if(Objects.equals(st.getColor(), temp))
                            quant++;
                    }
                    break;
                case 1:
                    for(Player ps:game.getPlayers())
                        assertFalse(ps.getHall().cardActivated);
                    break;
                case 4:
                    ConcreteCharacter car3= (ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    assertEquals(car3.getTD(), 4);
                    assertFalse(game.getB().islands.getIsland(1).TD);
                    break;
                case 5:
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("GREEN", game.playerTranslator("CARMINE"));
                    game.determineInfluence(1);
                    assertFalse(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer(), game.playerTranslator("FRANCO"));
                    break;
                case 6:
                    ConcreteCharacter car5=(ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    assertEquals(car5.getStudents().size(), 6);
                    assertEquals(game.getPlayers().get(0).getGate().getStudents().size(), 7);
                    break;
            }
            game.activateCharacter("FRANCO", i, 1, "RED", a, c, 1, b);
            switch(game.characterSelector.getCharacters().get(i).getIndex()){
                case 0:
                    ConcreteCharacter car2= (ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    assertEquals(car2.getStudents().size(), 4);
                    for(Student st:game.getB().islands.getIsland(1).getStudents()){
                        if(Objects.equals(st.getColor(), temp))
                            quant--;
                    }
                    assertEquals(quant, -1);
                    game.characterSelector.effects.restore();
                    break;
                case 1:
                    boolean pass=false;
                    for(Player ps:game.getPlayers()) {
                        if (ps.getHall().cardActivated) {
                            pass = true;
                            break;
                        }
                    }
                    assertTrue(pass);
                    game.characterSelector.effects.restore();
                    break;
                case 4:
                    ConcreteCharacter car4= (ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    assertEquals(car4.getTD(), 3);
                    assertTrue(game.getB().islands.getIsland(1).TD);
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.determineInfluence(1);
                    assertTrue(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(car4.getTD(), 4);
                    game.characterSelector.effects.restore();
                    break;
                case 5:
                    game.removeFromIsland(0, 1);
                    game.addStudentToIsland("GREEN", 1);
                    game.determineInfluence(1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().size(), 1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer(), game.playerTranslator("CARMINE"));
                    game.characterSelector.effects.restore();
                    break;
                case 6:
                    ConcreteCharacter car5=(ConcreteCharacter) game.characterSelector.getCharacters().get(i);
                    assertEquals(car5.getStudents().size(), 6);
                    assertEquals(game.getPlayers().get(0).getGate().getStudents().size(), 7);
                    game.characterSelector.effects.restore();;
                default:
                    game.characterSelector.effects.restore();
            }
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
