package it.polimi.ingsw;

import it.polimi.ingsw.GAME.*;

import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.*;

import java.net.ServerSocket;
import java.net.Socket;

public class HallTest {

    private Game game;
    private Player player;
    private int r;
    private int b;
    private int g;
    private int y;
    private int p;
    ServerSocket k1;

    @Test
    public void setColor() {
        if(game!=null) {
            game.addStudentToHall("RED", player);
            game.addStudentToHall("RED", player);
            game.addStudentToHall("BLUE", player);
            game.addStudentToHall("GREEN", player);
            game.addStudentToHall("GREEN", player);
            game.addStudentToHall("GREEN", player);
            game.addStudentToHall("RED", player);


            r = game.getColor(player, "RED");
            b = game.getColor(player, "BLUE");
            g = game.getColor(player, "GREEN");
            y = game.getColor(player, "YELLOW");
            p = game.getColor(player, "PINK");

            Assert.assertEquals(0, y);
            Assert.assertEquals(3, r);
            Assert.assertEquals(1, b);
            Assert.assertEquals(3, g);
            Assert.assertEquals(0, y);
            Assert.assertEquals(0, p);
        }
    }

    @Test
    public void desetColor() {
        if (game != null) {
            player.getHall().setColor("PINK");
            player.getHall().setColor("RED");
            player.getHall().setColor("RED");
            player.getHall().setColor("RED");
            player.getHall().setColor("RED");
            player.getHall().setColor("BLUE");
            player.getHall().setColor("BLUE");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("GREEN");
            player.getHall().setColor("RED");
            player.getHall().setColor("YELLOW");
            player.getHall().setColor("YELLOW");
            player.getHall().setColor("YELLOW");
            player.getHall().setColor("YELLOW");
            player.getHall().setColor("YELLOW");

            game.removeFromHall(player, "RED");
            game.removeFromHall(player, "RED");
            game.removeFromHall(player, "BLUE");
            game.removeFromHall(player, "GREEN");
            game.removeFromHall(player, "GREEN");
            game.removeFromHall(player, "GREEN");
            game.removeFromHall(player, "RED");
            game.removeFromHall(player, "PINK");

            r = game.getColor(player, "RED");
            b = game.getColor(player, "BLUE");
            g = game.getColor(player, "GREEN");
            y = game.getColor(player, "YELLOW");
            p = game.getColor(player, "PINK");

            Assert.assertEquals(5, y);
            Assert.assertEquals(2, r);
            Assert.assertEquals(1, b);
            Assert.assertEquals(6, g);
            Assert.assertEquals(5, y);
            Assert.assertEquals(0, p);
        }
    }

    @Before
    public void setUp()  {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            Socket s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);
            player = new Player(2, "Pier", game);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() {
        r=0;
        b=0;
        g=0;
        y=0;
        p=0;
    }
}