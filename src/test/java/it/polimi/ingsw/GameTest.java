package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

import java.util.ArrayList;

public class GameTest {
    public Game game;

    @Test
    public void GameSimulation () {
        try {
            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 0);

            game.playCard("PIER", 2);
            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());

            game.playCard("PAOLO", 7);

            Assert.assertEquals("Azione", game.roundMaster.round.getCurrentPhase());

            //game.activateCharacter(player1.nickname, 1);
            game.gateToHall("PIER", "BLUE");
            game.gateToIsland("PIER", 3, 7, "YELLOW");
            game.gateToIsland("PIER", 2, 7, "YELLOW");

            //game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            //Assert.assertEquals(game.motherNature.getIsola().getId(), game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement() + 1);

            game.CloudToGate("PIER", "RED", 0, 1);
            game.CloudToGate("PIER", "YELLOW", 2, 1);
            game.CloudToGate("PIER", "BLUE", 1, 1);

            Assert.assertTrue(game.getB().clouds.get(0).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(0).studentsMoved, 0);

            game.gateToHall("PAOLO", "RED");
            game.gateToIsland("PAOLO", 1, 11, "YELLOW");
            game.gateToIsland("PAOLO", 4, 4, "YELLOW");

            //game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

        }catch (ImpossibleActionException | BoundException e){
            System.out.println(e.getMessage());
        }

    }


    @Before
    public void setUp() throws Exception {
        try{
            game = new Game(2, 1, "PIER", null, "PAOLO", null, null, null);
        }catch (GameException e){
            System.out.println(e.getMessage());}

    }

    @After
    public void tearDown() {
        try{
            game = new Game(2, 1, "PIER", null, "PAOLO", null, null, null);
        }catch (GameException e){
            System.out.println(e.getMessage());}
    }
}