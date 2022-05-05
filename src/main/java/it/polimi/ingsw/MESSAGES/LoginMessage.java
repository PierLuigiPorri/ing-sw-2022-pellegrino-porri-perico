package it.polimi.ingsw.MESSAGES;

import it.polimi.ingsw.MESSAGES.MessageType;

public class LoginMessage implements MessageType {
    String nickname;

    public LoginMessage(String nickname){
        this.nickname=nickname;
    }
}
