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
    ServerSocket k1;

    @Test
    public void GameSimulation () {
        if(game!=null) {
            try {
                Assert.assertEquals(2, game.getPlayers().size()); // number of players == 2
                Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase()); // the phase is planning phase
                Assert.assertEquals(game.roundMaster.getRoundCount(), 0); // the round is 0
                Assert.assertEquals(10, game.getPlayers().get(0).getHand().getCards().size()); // number of cards in the hand of each player
                Assert.assertEquals("PIER", game.getPlayers().get(0).getNickname()); // verifying the name is Pier
                Assert.assertEquals(8, game.getPlayers().get(0).getTower_count()); // the towers owned by a player is 8
                game.playCard("PIER", 2); // first player plays a card
                Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase()); // still in planning phase
                Assert.assertEquals(9, game.getPlayers().get(0).getHand().getCards().size()); // number of cards in PIER's hand == 9

                game.playCard("PAOLO", 7); // the other player plays a card

                Assert.assertEquals(9, game.getPlayers().get(0).getHand().getCards().size()); // he now has 9 cards

                Assert.assertEquals("Action", game.roundMaster.round.getCurrentPhase()); // we go in action phase since every player has played his card.

                for (int i = 0; i < 3; i++) {
                    tmp1[i] = game.getPlayers().get(1).getGate().getStudents().get(i);
                    tmp2[i] = game.getPlayers().get(0).getGate().getStudents().get(i);
                }

                // the first player starts placing students
                game.gateToHall("PIER", tmp2[0].getColor());
                game.gateToIsland("PIER", 0, 11);
                game.gateToIsland("PIER", 0, 9);


                for (int i = 0; i < 3; i++) {
                    tm1[i] = game.getBoard().clouds.get(0).getStudents().get(i);
                    tm2[i] = game.getBoard().clouds.get(1).getStudents().get(i);
                }
                //takes students from a cloud
                game.CloudToGate("PIER", 0);

                Assert.assertTrue(game.getBoard().clouds.get(0).emptyCloud()); //the cloud is now empty
                Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0); // the player has no more moves to do

                // PIER moves MotherNature
                game.moveMotherNature("PIER", game.order.get(0).getLastCardPlayed().getMovement());

                // the other player starts placing students.
                game.gateToHall("PAOLO", tmp1[0].getColor());
                game.gateToIsland("PAOLO", 0, 1);
                game.gateToIsland("PAOLO", 0, 1);


                game.CloudToGate("PAOLO", 1);

                Assert.assertTrue(game.getBoard().clouds.get(1).emptyCloud()); // the chosen cloud is empty
                Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0); // the player has no more students to move

                game.moveMotherNature("PAOLO", game.order.get(0).getLastCardPlayed().getMovement());

                Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase()); // now the phase is again Planning and a new round is started
                Assert.assertEquals(game.roundMaster.getRoundCount(), 1); // the round is now 1, since we have finished the first one
                Assert.assertEquals(9,  game.getPlayers().get(0).getHand().getCards().size());// players have still 9 cards playable
                game.playCard("PIER", 1);
                Assert.assertEquals(8,  game.getPlayers().get(0).getHand().getCards().size()); // after playing another card, the player has 8 cards

                Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(0).getLastCardPlayed())); // the last card played is contained in the attribute LastCardPlayed

                Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase()); // still in planning phase
                game.playCard("PAOLO", 5);
                Assert.assertTrue(game.getCardsPlayed().contains(game.getPlayers().get(1).getLastCardPlayed())); // the last card played is contained in the attribute LastCardPlayed


                Assert.assertEquals("Action", game.roundMaster.round.getCurrentPhase()); // again in action phase, since everybody has played their card.

                for (int i = 0; i < 3; i++) {
                    tmp1[i] = game.getPlayers().get(1).getGate().getStudents().get(i);
                    tmp2[i] = game.getPlayers().get(0).getGate().getStudents().get(i);
                }
                // placing again students
                game.gateToHall("PIER", tmp2[0].getColor());
                game.gateToIsland("PIER", 0, 2);
                game.gateToIsland("PIER", 0, 2);

                for (int i = 0; i < 3; i++) {
                    tm1[i] = game.getBoard().clouds.get(0).getStudents().get(i);
                    tm2[i] = game.getBoard().clouds.get(1).getStudents().get(i);
                }
                //taking students from cloud
                game.CloudToGate("PIER", 0);

                //the cloud is now empty and the player can move no students
                Assert.assertTrue(game.getBoard().clouds.get(0).emptyCloud());
                Assert.assertEquals(game.getPlayers().get(1).studentsMoved, 0);
                //moving MotherNature
                game.moveMotherNature("PIER", game.order.get(0).getLastCardPlayed().getMovement());

                if(game.getBoard().islands.size()==12)//if no merge has happened.
                    Assert.assertEquals(8, game.motherNature.getIsland().getId()); //mother nature new position
                else if(game.getBoard().islands.size()==11) // if there was a merge between two islands
                    Assert.assertEquals(7, game.motherNature.getIsland().getId());

                // Paolo's time to place students
                game.gateToHall("PAOLO", tmp1[0].getColor());
                game.gateToIsland("PAOLO", 0, 9);
                game.gateToIsland("PAOLO", 0, 4);
                game.CloudToGate("PAOLO", 1);
                Assert.assertTrue(game.getBoard().clouds.get(1).emptyCloud()); // the chosen cloud is empty

                game.moveMotherNature("PAOLO", game.order.get(0).getLastCardPlayed().getMovement());
                Assert.assertEquals("Planning", game.roundMaster.round.getCurrentPhase()); // again in planning phase
                Assert.assertEquals(game.roundMaster.getRoundCount(), 2); // the round number is now 2, since the first two rounds has finished.

                System.out.println("TEST PASSATO!");

            } catch (ImpossibleActionException | BoundException | ConsecutiveIslandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Before
    public void setUp() {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);

            player = new Player(2, "Pier", game);
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
    public void tearDown() {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);

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