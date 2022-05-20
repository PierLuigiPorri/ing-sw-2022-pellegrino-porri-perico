package it.polimi.ingsw.MESSAGES;

import it.polimi.ingsw.EXCEPTIONS.MessageCreationError;

public class CreationMessage extends MessageType{
    public int creationid;
    //0: newGame(nick, gt, np)
    //1: joinRandomGame(nick)
    //2: joinGameWithId(nick, id)
    //3: seeAvailableGames()
    public String nick;
    public int players;
    public int gameType;
    public int gameid;

    public CreationMessage(int id, String nick, int gameType, int players) throws MessageCreationError{
        //creationid: 0
        super(1);
        if(id==0) {
            this.creationid=id;
            this.nick=nick;
            this.gameType=gameType;
            this.players=players;
        }
        else throw new MessageCreationError("Impossible to create this message");
    }

    public CreationMessage(int id, String nick) throws MessageCreationError{
        //creationid: 1
        super(1);
        if(id==1) {
            this.creationid=id;
            this.nick=nick;
        }
        else throw new MessageCreationError("Impossible to create this message");
    }

    public CreationMessage(int id, String nick, int gameid) throws MessageCreationError{
        //creationid: 2
        super(1);
        if(id==2) {
            this.creationid=id;
            this.nick=nick;
            this.gameid=gameid;
        }
        else throw new MessageCreationError("Impossible to create this message");
    }

    //TODO: gli altri costruttori
}
