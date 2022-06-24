package it.polimi.ingsw.MESSAGES;

/**
 * The ResponseMessage contains responses to send to the client after he requested a specific action
 * @author GC56
 */
public class ResponseMessage extends MessageType{
    public String response;
    public boolean allGood; //If true the requested action has been performed, if false it hasn't
    public Integer gameid;
    public boolean gameEnded;

    /**
     *
     * @param s The text of the response to send to the client
     * @param allGood true: the requested action was performed, false: the requested action wasn't performed
     * @param gameid The ID of the game. null if not yet in a game
     * @param gameEnded true only if the game just ended
     * @author GC56
     */
    public ResponseMessage(String s, boolean allGood, Integer gameid, boolean gameEnded){
        super(2);
        this.response=s;
        this.allGood=allGood;
        this.gameid=gameid;
        this.gameEnded=gameEnded;
    }
}
