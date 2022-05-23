package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class GameTest {
    public Game game;
    public Game game3;
    public Player player;

    Student[] tmp1;
    Student[] tmp2;
    Student[] tmp3;
    Student[] tm1;
    Student[] tm2;
    Student[] tm3;

    @Test
    public void GameSimulation () {
        try {
            Assert.assertEquals(2, game.getPlayers().size());
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 0);
            Assert.assertEquals(10, game.getPlayers().get(0).getHand().cards.size());
            Assert.assertEquals("PIER", game.getPlayers().get(0).getNickname());
            Assert.assertEquals(8, game.getPlayers().get(0).getTower_count());
            game.playCard("PIER", 2);
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());

            game.playCard("PAOLO", 7);

            Assert.assertEquals("Action", game.roundMaster.round.getCurrentPhase());

            //game.activateCharacter(player1.nickname, 1);

            for (int i=0; i<3; i++){
                tmp1[i]=game.getPlayers().get(1).getGate().getStudents().get(i);
                tmp2[i]=game.getPlayers().get(0).getGate().getStudents().get(i);
            }

            game.gateToHall("PIER", tmp2[0].getColor());
            game.gateToIsland("PIER", 0, 11, tmp2[1].getColor());
            game.gateToIsland("PIER", 0, 4, tmp2[2].getColor());


            for (int i=0; i<3; i++){
                tm1[i]=game.getB().clouds.get(0).getStudents().get(i);
                tm2[i]=game.getB().clouds.get(1).getStudents().get(i);
            }

            game.CloudToGate("PIER", tm1[0].getColor(), 0, 0);
            game.CloudToGate("PIER", tm1[1].getColor(), 0, 0);
            game.CloudToGate("PIER", tm1[2].getColor(), 0, 0);

            Assert.assertTrue(game.getB().clouds.get(0).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

            game.moveMotherNature("PIER", game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());


            game.gateToHall("PAOLO", tmp1[0].getColor() );
            game.gateToIsland("PAOLO", 0, 7, tmp1[1].getColor());
            game.gateToIsland("PAOLO", 0, 7, tmp1[2].getColor());


            game.CloudToGate("PAOLO", tm2[0].getColor(), 0, 1);
            game.CloudToGate("PAOLO", tm2[1].getColor(), 0, 1);
            game.CloudToGate("PAOLO", tm2[2].getColor(), 0, 1);

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

            game.moveMotherNature("PAOLO", game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            //Assert.assertEquals(game.motherNature.getIsola().getId(), game.getCardsPlayed().remove(game.getCardsPlayed().size()-1).getMovement() + 1);


            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 1);
            game.playCard("PIER", 1);
            Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(0).getLastCardPlayed()));

            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            game.playCard("PAOLO", 5);
            Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(1).getLastCardPlayed()));

            Assert.assertEquals("Action", game.roundMaster.round.getCurrentPhase());

            for (int i=0; i<3; i++){
                tmp1[i]=game.getPlayers().get(1).getGate().getStudents().get(i);
                tmp2[i]=game.getPlayers().get(0).getGate().getStudents().get(i);
            }

            game.gateToHall("PIER", tmp2[0].getColor() );
            game.gateToIsland("PIER", 0, 7, tmp2[1].getColor());
            game.gateToIsland("PIER", 0, 7, tmp2[2].getColor());

            for (int i=0; i<3; i++){
                tm1[i]=game.getB().clouds.get(0).getStudents().get(i);
                tm2[i]=game.getB().clouds.get(1).getStudents().get(i);
            }
            game.CloudToGate("PIER", tm2[0].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[1].getColor(), 0, 1);
            game.CloudToGate("PIER", tm2[2].getColor(), 0, 1);

            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());
            Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);

            game.moveMotherNature("PIER", game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(12, game.motherNature.getIsola().getId());

            game.gateToHall("PAOLO", tmp1[0].getColor());
            game.gateToIsland("PAOLO", 0, 9, tmp1[1].getColor());
            game.gateToIsland("PAOLO", 0, 4, tmp1[2].getColor());
            game.CloudToGate("PAOLO", tm1[0].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[1].getColor(), 0, 0);
            game.CloudToGate("PAOLO", tm1[2].getColor(), 0, 0);
            Assert.assertTrue(game.getB().clouds.get(1).emptyCloud());

            game.moveMotherNature("PAOLO", game.getCardsPlayed().get(game.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            Assert.assertEquals(game.roundMaster.getRoundCount(), 2);

            System.out.println("TEST PASSATO!");

        }catch (ImpossibleActionException | BoundException | ConsecutiveIslandException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void game3Simulation() {
        Assert.assertEquals(3, game3.getPlayers().size());
        Assert.assertEquals(12, game3.getB().islands.size());
        Assert.assertEquals(3, game3.getB().clouds.size());
        Assert.assertEquals(0, game3.getB().islands.getIsland(1).getStudents().size());
        Assert.assertEquals(0, game3.getB().islands.getIsland(6).getStudents().size());
        Assert.assertEquals(0, game3.roundMaster.getRoundCount());
        try {
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            game3.playCard("PIER", 2);
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            game3.playCard("PAOLO", 7);
            Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase());
            game3.playCard("Gandalf", 5);
            Assert.assertEquals("Action", game3.roundMaster.round.getCurrentPhase());

            for (int i=0; i<4; i++){
                tmp1[i]=game3.getPlayers().get(0).getGate().getStudents().get(i);
                tmp2[i]=game3.getPlayers().get(1).getGate().getStudents().get(i);
                tmp3[i]=game3.getPlayers().get(2).getGate().getStudents().get(i);
            }
            game3.gateToHall("PIER", tmp1[0].getColor() );
            game3.gateToIsland("PIER", 0, 7, tmp1[1].getColor());
            game3.gateToIsland("PIER", 0, 7, tmp1[2].getColor());
            game3.gateToHall("PIER", tmp1[3].getColor() );

            for (int i=0; i<4; i++){
                tm1[i]=game3.getB().clouds.get(0).getStudents().get(i);
                tm2[i]=game3.getB().clouds.get(1).getStudents().get(i);
                tm3[i]=game3.getB().clouds.get(2).getStudents().get(i);
            }

            game3.CloudToGate("PIER", tm2[0].getColor(), 0, 1);
            game3.CloudToGate("PIER", tm2[1].getColor(), 0, 1);
            game3.CloudToGate("PIER", tm2[2].getColor(), 0, 1);
            game3.CloudToGate("PIER", tm2[3].getColor(), 0, 1);

            game3.moveMotherNature("PIER", game3.getCardsPlayed().get(game3.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(game3.motherNature.getIsola().getId(), game3.getCardsPlayed().remove(game3.getCardsPlayed().size()-1).getMovement() + 1);


            game3.gateToHall("Gandalf", tmp2[0].getColor() );
            game3.gateToIsland("Gandalf", 0, 7, tmp2[1].getColor());
            game3.gateToIsland("Gandalf", 0, 7, tmp2[2].getColor());
            game3.gateToHall("Gandalf", tmp2[3].getColor() );


            game3.CloudToGate("Gandalf", tm3[0].getColor(), 0, 2);
            game3.CloudToGate("Gandalf", tm3[1].getColor(), 0, 2);
            game3.CloudToGate("Gandalf", tm3[2].getColor(), 0, 2);
            game3.CloudToGate("Gandalf", tm3[3].getColor(), 0, 2);


            game3.moveMotherNature("Gandalf", game3.getCardsPlayed().get(game3.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(game3.motherNature.getIsola().getId(), 8);

            game3.gateToHall("PAOLO", tmp2[0].getColor() );
            game3.gateToIsland("PAOLO", 0, 7, tmp2[1].getColor());
            game3.gateToIsland("PAOLO", 0, 7, tmp2[2].getColor());
            game3.gateToHall("PAOLO", tmp2[3].getColor() );


            game3.CloudToGate("PAOLO", tm1[0].getColor(), 0, 0);
            game3.CloudToGate("PAOLO", tm1[1].getColor(), 0, 0);
            game3.CloudToGate("PAOLO", tm1[2].getColor(), 0, 0);
            game3.CloudToGate("PAOLO", tm1[3].getColor(), 0, 0);


            game3.moveMotherNature("PAOLO", game3.getCardsPlayed().get(game3.getCardsPlayed().size()-1).getMovement());
            Assert.assertEquals(game3.motherNature.getIsola().getId(), 12);

            System.out.println("TEST PASSATO!");
        }catch (ImpossibleActionException | BoundException | ConsecutiveIslandException e){
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
        game.getB().islands.getIsland(11).addTower(player);
        game.getB().islands.getIsland(12).addTower(player);
        game.mergeIslands(11, 12);
        Assert.assertFalse(game.getB().islands.contains( game.getB().islands.getIsland(12)));
        //Assert.assertFalse(game.getB().islands.contains( game.getB().islands.getIsland(11)));
        Assert.assertEquals(11, game.getB().islands.size());
        game.getB().islands.getIsland(1).addTower(player);
        game.mergeIslands(1,11);
        Assert.assertEquals(10, game.getB().islands.size());
        Assert.assertEquals(game.getB().islands.getIsland(10).getIslandCount(), 3);

        System.out.println("TEST PASSATO!");
    }

    @Before
    public void setUp() throws Exception {

            game = new Game(2, 1, "PIER", "PAOLO", null, null);
            game3 = new Game(3, 1, "PIER", "PAOLO", "Gandalf", null);

        player= new Player(2,"Pier", game);
        tmp1= new Student[4];
        tmp2= new Student[4];
        tm1 = new Student[4];
        tm2 = new Student[4];
        tmp3= new Student[4];
        tm3 = new Student[4];
    }

    @After
    public void tearDown() {

        game = new Game(2, 1, "PIER", "PAOLO", null, null);
        game3 = new Game(3, 1, "PIER", "PAOLO", "Gandalf", null);

        tmp1= new Student[4];
        tmp2= new Student[4];
        tm1 = new Student[4];
        tm2 = new Student[4];
        tmp3= new Student[4];
        tm3 = new Student[4];
    }
}