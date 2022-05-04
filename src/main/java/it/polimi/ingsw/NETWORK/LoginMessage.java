package it.polimi.ingsw.NETWORK;

public class LoginMessage implements MessageType{
    String nickname;

    public LoginMessage(String nickname){
        this.nickname=nickname;
    }
}
