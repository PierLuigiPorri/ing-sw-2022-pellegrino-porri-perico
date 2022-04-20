package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

import java.util.ArrayList;

public class GameTest {
    public Game game;
    public Player player1;
    public Player player2;

    @Test
    public void GameSimulation () {
        try {
            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 0);

            game.playCard(player1.nickname, 2);
            game.playCard(player2.nickname, 7);

            game.gateToHall(player1.nickname, "BLUE");
            game.gateToIsland(player1.nickname, 3, 7, "YELLOW");
            game.gateToIsland(player1.nickname, 2, 7, "YELLOW");

            //game.moveMotherNature(game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement());
            //Assert.assertEquals(game.motherNature.getIsola().getId(), game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement() + 1);
            for (int i=0; i<3; i++)
                game.CloudToGate(player1.nickname, "RED", i, 1);

            Assert.assertTrue(game.getB().clouds.get(0).emptyCloud());
            Assert.assertEquals(player1.studentsMoved, 0);

            game.gateToHall(player2.nickname, "RED");
            game.gateToIsland(player2.nickname, 1, 11, "YELLOW");
            game.gateToIsland(player2.nickname, 4, 4, "YELLOW");

            //game.moveMotherNature(game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement());

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(player2.studentsMoved, 0);

        }catch (ImpossibleActionException | BoundException e){
            System.out.println(e.getMessage());
        }

    }


    @Before
    public void setUp() throws Exception {
        try{
            game = new Game(2, 0, "PIER", null, "PAOLO", null, null, null);
            player1=new Player(2,"PIER", game);
            player2=new Player(2, "PAOLO", game);
        }catch (GameException e){
            System.out.println(e.getMessage());}

    }

    @After
    public void tearDown() {

    }
}