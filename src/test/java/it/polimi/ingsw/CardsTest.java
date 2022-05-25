package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;
import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import static org.junit.Assert.*;

public class CardsTest
{
    public Game game;
    public Player p1, p2;
    private ServerSocket k1;

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
                    t=t.next;
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
            String temp = null;
            int quant=0;
            System.out.println("\n "+ game.characterSelector.getCharacters().get(0).getIndex());
            switch(game.characterSelector.getCharacters().get(0).getIndex()){ //not all the cards are tested due to not being necessary
                case 0:
                    ConcreteCharacter car= (ConcreteCharacter) game.characterSelector.getCharacters().get(0);
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
                    ConcreteCharacter car3= (ConcreteCharacter) game.characterSelector.getCharacters().get(0);
                    assertEquals(car3.getTD(), 4);
                    assertFalse(game.getB().islands.getIsland(1).TD);
                    break;
                case 5:
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("GREEN", game.playerTranslator("CARMINE"));
                    game.determineInfluence(1);
                    assertFalse(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "FRANCO");
                    break;
                case 6:
                    ConcreteCharacter car5=(ConcreteCharacter) game.characterSelector.getCharacters().get(0);
                    assertEquals(car5.getStudents().size(), 6);
                    assertEquals(game.getPlayers().get(0).getGate().getStudents().size(), 7);
                    break;
                case 8:
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("GREEN", game.playerTranslator("CARMINE"));
                    game.determineInfluence(1);
                    assertFalse(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer(), game.playerTranslator("FRANCO"));
                    game.addStudentToIsland("GREEN", 1);
                    game.addStudentToIsland("GREEN", 1);
                    break;
                case 9:
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    game.getPlayers().get(0).getGate().getStudents().set(0, new Student("GREEN"));
                    game.getPlayers().get(0).getGate().getStudents().set(1, new Student("GREEN"));
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToIsland("RED", 1);
                    game.determineInfluence(1);
                    assertFalse(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "FRANCO");
                    break;
                case 10:
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToIsland("BLUE", 1);
                    game.addStudentToIsland("YELLOW", 1);
                    game.addStudentToIsland("GREEN", 1);
                    game.addStudentToIsland("PINK", 1);
                    game.determineInfluence(1);
                    assertTrue(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    break;
                case 11:
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToHall("RED", game.playerTranslator("FRANCO"));
                    game.addStudentToIsland("RED", 1);
                    game.addStudentToIsland("RED", 1);
                    game.determineInfluence(1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "FRANCO");
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    game.addStudentToHall("RED", game.playerTranslator("CARMINE"));
                    break;
                default:break;
            }
            game.activateCharacter("FRANCO", 0, 1, "RED", a, c, 1, b);
            switch(game.characterSelector.getCharacters().get(0).getIndex()){
                case 0:
                    ConcreteCharacter car2= (ConcreteCharacter) game.characterSelector.getCharacters().get(0);
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
                    //assertTrue(pass);
                    game.characterSelector.effects.restore();
                    break;
                case 4:
                    ConcreteCharacter car4= (ConcreteCharacter) game.characterSelector.getCharacters().get(0);
                    assertEquals(car4.getTD(), 4);
                    //assertTrue(game.getB().islands.getIsland(1).TD);
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
                    //assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer(), game.playerTranslator("CARMINE"));
                    game.characterSelector.effects.restore();
                    break;
                case 6:
                    ConcreteCharacter car5=(ConcreteCharacter) game.characterSelector.getCharacters().get(0);
                    assertEquals(car5.getStudents().size(), 6);
                    assertEquals(game.getPlayers().get(0).getGate().getStudents().size(), 7);
                    game.characterSelector.effects.restore();
                    break;
                case 8:
                    game.determineInfluence(1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().size(), 1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "FRANCO");
                    game.characterSelector.effects.restore();
                    break;
                case 9:
                    game.determineInfluence(1);
                    assertFalse(game.getB().islands.getIsland(1).getTowers().isEmpty());
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "CARMINE");
                    game.characterSelector.effects.restore();
                    break;
                case 10:
                    game.determineInfluence(1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().size(), 1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "FRANCO");
                    game.characterSelector.effects.restore();
                    break;
                case 11:
                    assertEquals(game.getPlayers().get(0).getHall().getColor("RED"), 0);
                    assertEquals(game.getPlayers().get(1).getHall().getColor("RED"), 1);
                    game.determineInfluence(1);
                    assertEquals(game.getB().islands.getIsland(1).getTowers().get(0).getPlayer().nickname, "CARMINE");
                    break;
                default:
                    game.characterSelector.effects.restore();
                    break;
            }
    }




    @Before
    public void setUp() throws IOException {
        int i=4000 + (int)(Math.random() * ((10000 - 4000) + 1));
        k1=new ServerSocket(i);
        Socket s = new Socket("127.0.0.1", i);
        GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
        game = new Game(2, 1, "FRANCO", "CARMINE", null, gm);
        p1 = new Player(2, "FRANCO", game);
        p2 = new Player(2, "CARMINE", game);
    }
}
