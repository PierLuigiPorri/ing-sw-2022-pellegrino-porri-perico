package it.polimi.ingsw.MESSAGES;

import it.polimi.ingsw.MESSAGES.MessageType;

public class LoginMessage extends MessageType {
    String nickname;

    public LoginMessage(String nickname){
        this.nickname=nickname;
    }
}
