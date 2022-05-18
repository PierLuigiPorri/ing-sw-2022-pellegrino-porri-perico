package it.polimi.ingsw.MESSAGES;

import java.io.Serializable;

public class MessageType implements Serializable {
    public int type;
    //0: AckMessage (Client -> Server)
    //1: CreationMessage (Client -> Server)
    //2: ResponseMessage (Server -> Client)
    //3: ActionMessage (Client -> Server)
    //4: UpdateMessage (Server -> Client)
    //5: KillMessage (Client -> Server)

    public MessageType(int type){
        this.type=type;
    }
}
