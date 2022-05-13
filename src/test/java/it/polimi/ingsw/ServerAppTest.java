package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.MESSAGES.*;
import it.polimi.ingsw.SERVER.*;

import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

/**
 * Unit test for simple App.
 */
public class ServerAppTest
{
    ServerApp server;
    VirtualView mh;
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Before
    public void setUp(){
        mh=new VirtualView(new Socket());
    }
}
