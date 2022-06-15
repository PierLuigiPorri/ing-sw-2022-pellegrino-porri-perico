package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.BoundException;
import it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.GAME.Player;
import it.polimi.ingsw.GAME.Student;
import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

public class Game3Test {
    public Game game3;
    public Player player;

    Student[] tmp1;
    Student[] tmp2;
    Student[] tmp3;
    Student[] tm1;
    Student[] tm2;
    Student[] tm3;
    ServerSocket k1;

    @Test
    public void game3Simulation() {
        if (game3 != null) {
            Assert.assertEquals(3, game3.getPlayers().size());
            Assert.assertEquals(12, game3.getBoard().islands.size());
            Assert.assertEquals(3, game3.getBoard().clouds.size());
            Assert.assertEquals(0, game3.getBoard().islands.getIsland(1).getStudents().size());
            Assert.assertEquals(0, game3.getBoard().islands.getIsland(6).getStudents().size());
            Assert.assertEquals(0, game3.roundMaster.getRoundCount());
            try {
                Assert.assertEquals("Planning", game3.roundMaster.round.getCurrentPhase());
                game3.playCard("PIER", 2);
                Assert.assertEquals("Planning", game3.roundMaster.round.getCurrentPhase());
                game3.playCard("PAOLO", 7);
                Assert.assertEquals("Planning", game3.roundMaster.round.getCurrentPhase());
                game3.playCard("Gandalf", 5);
                Assert.assertEquals("Action", game3.roundMaster.round.getCurrentPhase());

                for (int i = 0; i < 4; i++) {
                    tmp1[i] = game3.getPlayers().get(0).getGate().getStudents().get(i);
                    tmp2[i] = game3.getPlayers().get(1).getGate().getStudents().get(i);
                    tmp3[i] = game3.getPlayers().get(2).getGate().getStudents().get(i);
                }
                game3.gateToHall("PIER", tmp1[0].getColor());
                game3.gateToIsland("PIER", 0, 7);
                game3.gateToIsland("PIER", 0, 7);
                game3.gateToHall("PIER", tmp1[3].getColor());

                for (int i = 0; i < 4; i++) {
                    tm1[i] = game3.getBoard().clouds.get(0).getStudents().get(i);
                    tm2[i] = game3.getBoard().clouds.get(1).getStudents().get(i);
                    tm3[i] = game3.getBoard().clouds.get(2).getStudents().get(i);
                }

                game3.CloudToGate("PIER", 0);

                game3.moveMotherNature("PIER", game3.order.get(0).getLastCardPlayed().getMovement());
                Assert.assertEquals(game3.motherNature.getIsola().getId(), 3);


                game3.gateToHall("Gandalf", tmp2[0].getColor());
                game3.gateToIsland("Gandalf", 0, 7);
                game3.gateToIsland("Gandalf", 0, 7);
                game3.gateToHall("Gandalf", tmp2[3].getColor());


                game3.CloudToGate("Gandalf", 2);


                game3.moveMotherNature("Gandalf", game3.order.get(0).getLastCardPlayed().getMovement());
                Assert.assertEquals(game3.motherNature.getIsola().getId(), 6);

                game3.gateToHall("PAOLO", tmp2[0].getColor());
                game3.gateToIsland("PAOLO", 0, 7);
                game3.gateToIsland("PAOLO", 0, 7);
                game3.gateToHall("PAOLO", tmp2[3].getColor());


                game3.CloudToGate("PAOLO", 1);


                game3.moveMotherNature("PAOLO", game3.order.get(0).getLastCardPlayed().getMovement());
                Assert.assertEquals(game3.motherNature.getIsola().getId(), 10);

                System.out.println("TEST PASSATO!");
            } catch (ImpossibleActionException | BoundException | ConsecutiveIslandException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @Before
    public void setUp()  {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm3 = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 3);
            game3 = new Game(3, 1, "PIER", "PAOLO", "Gandalf", gm3);

            player = new Player(2, "Pier", game3);
            tmp1 = new Student[4];
            tmp2 = new Student[4];
            tm1 = new Student[4];
            tm2 = new Student[4];
            tmp3 = new Student[4];
            tm3 = new Student[4];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown()  {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm3 = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 3);
            game3 = new Game(3, 1, "PIER", "PAOLO", "Gandalf", gm3);

            tmp1 = new Student[4];
            tmp2 = new Student[4];
            tm1 = new Student[4];
            tm2 = new Student[4];
            tmp3 = new Student[4];
            tm3 = new Student[4];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
