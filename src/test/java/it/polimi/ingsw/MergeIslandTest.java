package it.polimi.ingsw;

import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.GAME.Player;
import it.polimi.ingsw.GAME.Student;
import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class MergeIslandTest {

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
    public void mergeIslandsTest() {
        if (game != null) {
            Assert.assertEquals(12, game.getB().islands.size());
            //game.mergeIslands(1, 3); returns the correct exceptions. it.polimi.ingsw.EXCEPTIONS.ConsecutiveIslandException:
            // The islands are not consecutive, impossible to merge!
            Assert.assertEquals(12, game.getB().islands.size());
            game.mergeIslands(1, 2);
            Assert.assertEquals(12, game.getB().islands.size());
            game.getB().islands.getIsland(11).addTower(player);
            game.getB().islands.getIsland(12).addTower(player);
            game.mergeIslands(11, 12);
            Assert.assertFalse(game.getB().islands.contains(game.getB().islands.getIsland(12)));
            Assert.assertEquals(11, game.getB().islands.size());
            game.getB().islands.getIsland(1).addTower(player);
            game.mergeIslands(1, 11);
            Assert.assertEquals(10, game.getB().islands.size());
            Assert.assertEquals(game.getB().islands.getIsland(10).getIslandCount(), 3);

            System.out.println("TEST PASSATO!");
        }
    }


    @Before
    public void setUp() throws Exception {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            GameManager gm3 = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 3);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);

            player = new Player(2, "Pier", game);
            tmp1 = new Student[4];
            tmp2 = new Student[4];
            tm1 = new Student[4];
            tm2 = new Student[4];
            tmp3 = new Student[4];
            tm3 = new Student[4];
        } catch (BindException e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
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
        } catch (BindException e) {
            System.out.println(e.getMessage());
        }
    }
}
