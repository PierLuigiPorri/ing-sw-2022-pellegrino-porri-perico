/*package it.polimi.ingsw;

import it.polimi.ingsw.GAME.Game;
import it.polimi.ingsw.SERVER.ConnectionManager;
import it.polimi.ingsw.SERVER.Controller;
import it.polimi.ingsw.SERVER.GameManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

public class ControllerTest {
    Controller controller;
    Game game;
    ServerSocket k1;
    Socket s;


    @Test
    public void controllerTest() {
        int island = 1 + (int) (Math.random() * ((12 - 1) + 1));
        int student = 1 + (int) (Math.random() * ((8 - 1) + 1));
        int movement =  1 + (int) (Math.random() * ((7 - 1) + 1));
        int cloud = (int) (Math.random() * (2));

        controller.gateToIsland("PIER", student, island);
        controller.moveMotherNature("PIER", movement);
        controller.CloudToGate("PIER", cloud);
        controller.gateToHall("PIER", "RED");
        System.out.println("TEST PASSATO!");
    }


    @Before
    public void setUp() {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);
            controller = new Controller(game, new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            int i = 4000 + (int) (Math.random() * ((10000 - 4000) + 1));
            k1 = new ServerSocket(i);
            s = new Socket("127.0.0.1", i);
            GameManager gm = new GameManager(new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s), 2);
            game = new Game(2, 1, "PIER", "PAOLO", null, gm);
            controller = new Controller(game, new ConnectionManager(s), new ConnectionManager(s), new ConnectionManager(s));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
*/