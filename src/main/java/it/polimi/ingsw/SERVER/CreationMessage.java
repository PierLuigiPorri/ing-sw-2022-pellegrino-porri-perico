package it.polimi.ingsw.SERVER;

public class CreationMessage extends LoginMessage implements MessageType{
    int players;
    int gameType;

    public CreationMessage(String nickname, int pl, int gt) {
        super(nickname);
        this.players=pl;
        this.gameType=gt;
    }
}
