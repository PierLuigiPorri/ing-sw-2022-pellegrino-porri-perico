package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.GameException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class GameTest {
    public Game game;
    public Player player;
    Student[] tmp1;
    Student[] tmp2;
    Student[] tm1;
    Student[] tm2;

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

            for (int i=0; i<3; i++){
                tmp1[i]=game.getPlayers().get(1).getGate().getStudents().get(i);
                tmp2[i]=game.getPlayers().get(0).getGate().getStudents().get(i);
            }

            game.gateToHall("PIER", tmp1[0].getColor() );
            game.gateToIsland("PIER", 3, 7, tmp1[1].getColor());
            game.gateToIsland("PIER", 2, 7, tmp1[2].getColor());

            game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(game.motherNature.getIsola().getId(), game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement() + 1);

            for (int i=0; i<3; i++){
                tm1[i]=game.getB().clouds.get(0).getStudents().get(i);
                tm2[i]=game.getB().clouds.get(1).getStudents().get(i);
            }

            game.CloudToGate("PIER", tm2[0].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[1].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[2].getColor(), 0, 1);

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

            game.gateToHall("PAOLO", tmp2[0].getColor());
            game.gateToIsland("PAOLO", 1, 11, tmp2[1].getColor());
            game.gateToIsland("PAOLO", 4, 4, tmp2[2].getColor());

            game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

            game.CloudToGate("PAOLO", tm1[0].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[1].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[2].getColor(), 0, 0);

            game.changePhase();


            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 1);
            game.playCard("PIER", 1);
            Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(0).getLastCardPlayed()));

            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());
            game.playCard("PAOLO", 5);
            Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(1).getLastCardPlayed()));

            Assert.assertEquals("Azione", game.roundMaster.round.getCurrentPhase());

            for (int i=0; i<3; i++){
                tmp1[i]=game.getPlayers().get(1).getGate().getStudents().get(i);
                tmp2[i]=game.getPlayers().get(0).getGate().getStudents().get(i);
            }
            game.gateToHall("PIER", tmp1[0].getColor() );
            game.gateToIsland("PIER", 3, 7, tmp1[1].getColor());
            game.gateToIsland("PIER", 2, 7, tmp1[2].getColor());
            game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(4, game.motherNature.getIsola().getId());
            for (int i=0; i<3; i++){
                tm1[i]=game.getB().clouds.get(0).getStudents().get(i);
                tm2[i]=game.getB().clouds.get(1).getStudents().get(i);
            }
            game.CloudToGate("PIER", tm2[0].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[1].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[2].getColor(), 0, 1);
            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);
            game.gateToHall("PAOLO", tmp2[0].getColor());
            game.gateToIsland("PAOLO", 1, 11, tmp2[1].getColor());
            game.gateToIsland("PAOLO", 4, 4, tmp2[2].getColor());
            game.moveMotherNature(game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);
            game.CloudToGate("PAOLO", tm1[0].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[1].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[2].getColor(), 0, 0);
            game.changePhase();
            Assert.assertEquals("Pianificazione", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 2);
            game.changePhase();

        }catch (ImpossibleActionException | BoundException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void mergeIslandsTest() throws ConsecutiveIslandException {
        Assert.assertEquals(12, game.getB().islands.size());
        //game.mergeIslands(1, 3); returns the correct exceptions. it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException:
        // The islands are not consecutive, impossible to merge!
        Assert.assertEquals(12, game.getB().islands.size());
        game.mergeIslands(1,2);
        Assert.assertEquals(12, game.getB().islands.size());
        game.getB().islands.getIsland(1).addTower(player);
        game.getB().islands.getIsland(2).addTower(player);
        game.mergeIslands(1, 2);
        //Assert.assertFalse(game.getB().islands.contains( game.getB().islands.getIsland(2)));
        //Assert.assertFalse(game.getB().islands.contains( game.getB().islands.getIsland(1)));
        Assert.assertEquals(11, game.getB().islands.size());

    }

    @Before
    public void setUp() throws Exception {
        try{
            game = new Game(2, 1, "PIER", null, "PAOLO", null, null, null);
        }catch (GameException e){
            System.out.println(e.getMessage());}
        player= new Player(2,"Pier", game);
        tmp1= new Student[3];
        tmp2= new Student[3];
        tm1 = new Student[3];
        tm2 = new Student[3];
    }

    @After
    public void tearDown() {
        try{
            game = new Game(2, 1, "PIER", null, "PAOLO", null, null, null);
        }catch (GameException e){
            System.out.println(e.getMessage());}
    }
}