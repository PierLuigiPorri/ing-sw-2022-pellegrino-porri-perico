package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.*;

import java.net.ServerSocket;
import java.net.Socket;

public class GameTest {
    public Game game;
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

    @Before
    public void setUp() throws Exception {
        ServerSocket k1=new ServerSocket(4000);
        Socket s= new Socket("127.0.0.1", 4000);
        GameManager gm=new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
        GameManager gm3=new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 3);
        game = new Game(2, 1, "PIER", "PAOLO", null, gm);

        player = new Player(2, "Pier", game);
        tmp1 = new Student[4];
        tmp2 = new Student[4];
        tm1 = new Student[4];
        tm2 = new Student[4];
        tmp3 = new Student[4];
        tm3 = new Student[4];
    }

    @After
    public void tearDown() throws Exception{
        Socket s= new Socket("127.0.0.1", 4000);
        GameManager gm=new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
        GameManager gm3=new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 3);
        game = new Game(2, 1, "PIER", "PAOLO", null, gm);

        tmp1= new Student[4];
        tmp2= new Student[4];
        tm1 = new Student[4];
        tm2 = new Student[4];
        tmp3= new Student[4];
        tm3 = new Student[4];
    }
}