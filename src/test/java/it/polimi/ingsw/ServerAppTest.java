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
    MsgHandler mh;
    MessageReader reader;
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void messagesTest(){
        AckMessage msg1=new AckMessage();
        System.out.println(reader.handle(msg1));
    }



    @Before
    public void setUp(){
        mh=new MsgHandler(new Socket());
        reader=new MessageReader();
    }
}
