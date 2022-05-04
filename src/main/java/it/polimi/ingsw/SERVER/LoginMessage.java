package it.polimi.ingsw.SERVER;

public class LoginMessage implements MessageType{
    String nickname;

    public LoginMessage(String nickname){
        this.nickname=nickname;
    }
}
