package it.polimi.ingsw.MESSAGES;

import java.io.Serializable;

/**
 * Abstract class that represents a generic message exchanged between client and server.
 *  @author GC56
 */
public abstract class MessageType implements Serializable {
    public int type;
    //0: AckMessage (Client -> Server)
    //1: CreationMessage (Client -> Server)
    //2: ResponseMessage (Server -> Client)
    //3: ActionMessage (Client -> Server)
    //4: UpdateMessage (Server -> Client)
    //5: KillMessage (Client -> Server)

    /**
     * Constructor.
     * @param type The type of the message. Types of messages are:
     *       0: AckMessage (Client -> Server)
     *       1: CreationMessage (Client -> Server)
     *       2: ResponseMessage (Server -> Client)
     *       3: ActionMessage (Client -> Server)
     *       4: UpdateMessage (Server -> Client)
     *       5: KillMessage (Client -> Server)
     * @requires type>=0 && type<=5
     * @author GC56
     */
    public MessageType(int type){
        this.type=type;
    }
}
