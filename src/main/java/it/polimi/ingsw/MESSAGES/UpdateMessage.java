package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The UpdateMessage contains all the information about the current state of the game.
 * @author GC56
 */
public class UpdateMessage extends MessageType{

    public ArrayList<String> update; // List of written messages to send to all clients when an action is correctly performed.
    public boolean gameEnded; // says if the game is over or not.
    public int game_Type; // says the type of rules chosen in this game: 0 -> normal rules; 1 -> expert rules.
    public int nPlayers; // says the number of players in this game.
    public final ArrayList<String> order; // the order in which the players are going to play.
    public String phase; // the current phase of this turn in the game.
    public int turnNumber; // number of turns already finished.
    public boolean cloudtaken=false; // says if the player who is playing right now has already selected a cloud.
    public final ArrayList<Integer> valueCardsPlayed; // values of the last card played from each player. //[positions] 0:player.get(0).getLastCardPlayed().getValue(); 1:player.get(1).getLastCardPlayed().getValue(); 2: player.get(2).getLastCardPlayed().getValue();

    public final LinkedHashMap<Integer, ArrayList<Integer>> handPlayer; // contains all the cards in the hand of every player.
    // so the Map is so built: Integer represents the position in the list game.getPlayers() of a player. In ArrayList<Integer> there are the two information needed of a card (movement and value)
    // for example: handPlayer.get(0) will return [1,1; 1,2; 2,3; 2,4; 3,5; 3,6; 4,7; 4,8; 5,9; 5,10] so it's the hand of the player who is in position 0 in the list Players.
    //In order: <player, <movement, value>>

    public final ArrayList<Integer> coinsOnPlayer; // contains every player's coins number.
    // [positions] 0:players.get(0).getCoins(); 1: players.get(1).getCoins(); 2: players.get(2).getCoins();
    public final ArrayList<Integer> towersOnPlayer; // contains every player's towers number.
    //[positions] 0:players.get(0); 1: players.get(1); 2: players.get(2);


    public final LinkedHashMap<Integer, ArrayList<String>> gatePlayer;// contains all the students in the Gate of every player.
    // so the Map is so built: Integer represents the position in the list game.getPlayers() of a player. In ArrayList<String> there are the two information needed of a Student (ColorInCLI and color)
    // for example: gatePlayer.get(0) will return ["\u001B[31m","RED"; "\u001B[32m","GREEN"; ...] so it's the gate of the player who is in position 0 in the list Players.
    //In order: <player, <colorInCLI, color>>


    //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public final LinkedHashMap<Integer, ArrayList<Integer>> hallPlayer;// contains all the students in the Hall of every player.
    // so the Map is so built: Integer represents the position in the list game.getPlayers() of a player. In ArrayList<Integer> there is the number of Students for each color.
    // for example: hallPlayer.get(0) will return [3; 0; 0; 2; 5;] so it's the hall of the player who is in position 0 in the list Players.
    // [positions] 0: red (in the example: 3) ; 1: blue (in the example: 0); 2: green (in the example: 0); 3: yellow (in the example: 2); 4:pink(in the example: 5);


    //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public final LinkedHashMap<Integer, ArrayList<Boolean>> professors;// contains all the professors of every player.
    // so the Map is so built: Integer represents the position in the list game.getPlayers() of a player. In ArrayList<Boolean> a position is true if and only if the specified Player owns the specified professor.
    // for example: professors.get(0) will return [true, false, true, false, false] so it's the list of professors of the player who is in position 0 in the list Players.
    //[positions] 0: red (in the example: true); 1: blue (in the example: false); 2: green (in the example: true); 3: yellow (in the example: false); 4:pink (in the example: false);


    public final ArrayList<Integer> lastCardsPlayed;
    //[positions] of lastCardsPlayed:
    // 0:Movement of the last card played by game.getOrder(0); 1: Value of the last card played by game.getOrder(0);
    // 2: Movement of the last card played by game.getOrder(1); 3: Value of the last card played by game.getOrder(1);
    // 4:Movement of the last card played by game.getOrder(2); 5:Value of the last card played by game.getOrder(2);

    public int numIslands; // says the number of islands remained.

    public final LinkedHashMap<Integer, ArrayList<String>> studentsOnIsland; // contains all the students on every island.
    // so the Map is so built: Integer represents the position of the island in game.getBoard().getIslands() . In ArrayList<String> there are the two information needed of a Student (ColorInCLI and color)
    // for example: studentsOnIsland.get(0) will return ["\u001B[31m","RED"; "\u001B[32m","GREEN"; ...] so it's the list of students on island number 1.
    //In order: <Island, <colorInCLI, color>>


    public final ArrayList<Boolean> motherNatureOnIsland; //[positions] 0: island1; 1: island2 ... 11: island12. True if and only if MotherNature is on the specified island;

    public final ArrayList<Integer> towersOnIsland; //[positions] 0: island1; 1: island2 ... number of tower on every island;
    public final ArrayList<String> whoOwnTowers; //[positions] 0: the name of the Player who owns island1, 1: the name of the Player who owns island2...

    public final LinkedHashMap<Integer, ArrayList<String>> studentsOnCloud;// contains all the students on every Cloud.
    // so the Map is so built: Integer represents the position of the Cloud in game.getBoard().getClouds() . In ArrayList<String> there are the two information needed of a Student (ColorInCLI and color)
    // for example: studentsOnCloud.get(0) will return ["\u001B[31m","RED"; "\u001B[32m","GREEN"; ...] so it's the list of students on Cloud number 0.
    //In order: <Cloud, <colorInCLI, color>>

    public final ArrayList<Integer> idCharacter; //if GameType=1, then here there will be the three id of the three characters in game.
    public final ArrayList<Boolean> activated;   //[positions] 0: true if and only if the character in position 0 has been already activated. 1: true if and only if character in position 1 has been already activated...
    public final ArrayList<Integer> cardCost; // the cost of every character. 0: the cost of the character in position 0; 1: the cost of the character in position 1; ...
    public int numTD; // number of prohibition cards.

    public final ArrayList<String> players; // final list of players in the game.
    public final ArrayList<Boolean> numTDOnIsland; // number of prohibition cards on every island. [positions] 0: the number of prohibition cards on island 1; 1: number of prohibition cards on island 2; ...

    public final LinkedHashMap<Integer, ArrayList<String>> studentsOnCard; // contains all the students on every Character card (if there is a card with students on).
    // so the Map is so built: Integer represents the position of the character in  game.characterSelector.getCharacters() . In ArrayList<String> there are the two information needed of a Student (ColorInCLI and color)
    // for example: studentsOnCard.get(0) will return ["\u001B[31m","RED"; "\u001B[32m","GREEN"; ...] so it's the list of students on character in position 0.
    //In order: <ConcreteCharacter, <colorInCLI, color>>

    public final ArrayList<Integer> playersMoves; //the number of moves remained of every player. [positions]: 0: number of moves of game.getPlayers().get(0); 1: number of moves of game.getPlayers().get(1); ...


    /**
     * UpdateMessage constructor. It constructs all the maps and lists.
     */
    public UpdateMessage() {
        super(4);
        this.order = new ArrayList<>();
        this.coinsOnPlayer = new ArrayList<>();
        this.towersOnPlayer = new ArrayList<>();
        this.players=new ArrayList<>();
        this.lastCardsPlayed = new ArrayList<>();
        this.motherNatureOnIsland = new ArrayList<>();
        this.towersOnIsland = new ArrayList<>();
        this.idCharacter = new ArrayList<>();
        this.activated = new ArrayList<>();
        this.cardCost = new ArrayList<>();
        this.numTDOnIsland = new ArrayList<>();
        this.update=new ArrayList<>();

        this.playersMoves = new ArrayList<>();
        this.whoOwnTowers = new ArrayList<>();
        this.studentsOnIsland= new LinkedHashMap<>();
        this.gatePlayer= new LinkedHashMap<>();
        this.handPlayer= new LinkedHashMap<>();
        this.hallPlayer= new LinkedHashMap<>();
        this.professors= new LinkedHashMap<>();
        this.studentsOnCloud= new LinkedHashMap<>();
        this.studentsOnCard= new LinkedHashMap<>();
        this.gameEnded=false;
        this.valueCardsPlayed=new ArrayList<>();
    }
}
