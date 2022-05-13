package it.polimi.ingsw.MESSAGES;

import it.polimi.ingsw.EXCEPTIONS.MessageCreationError;

public class CreationMessage extends MessageType{
    public int id;
    //0: newGame(nick, gt, np)
    //1: joinRandomGame()
    //2: joinGameWithId(id)
    //3: sendNick(nick)
    //4: seeAvailableGames()
    public String nick;
    public int players;
    public int gameType;
    public int gameid;

    public CreationMessage(int id) throws MessageCreationError{
        //id: 1, 4
        super(1);
        if(id==1 || id==4)
            this.id=id;
        else throw new MessageCreationError("Impossible to create this message");
    }

    //TODO: gli altri costruttori
}
